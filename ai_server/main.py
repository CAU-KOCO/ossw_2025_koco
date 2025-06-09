from fastapi import FastAPI, UploadFile, File, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from models import ResumeInput, ResumeAnalysisResult
from analyzer import analyze_resume, analyze_file
import os
import tempfile
import traceback

app = FastAPI(
    title="Resume Analysis API",
    description="이력서 분석을 위한 API 서버",
    version="1.0.0"
)

# CORS 설정 - Spring Boot 서버와 통신을 위해 필요
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 프로덕션에서는 Spring Boot 서버 URL로 제한
    allow_credentials=True,
    allow_methods=["GET", "POST"],
    allow_headers=["*"],
)

@app.get("/")
async def root():
    return {"message": "Resume Analysis Server is running", "status": "healthy"}

@app.get("/health")
async def health_check():
    """Spring Boot에서 서버 상태 확인용"""
    return {"status": "healthy", "service": "resume-analysis"}

@app.post("/analyze_resume", response_model=ResumeAnalysisResult)
async def analyze_resume_endpoint(resume_input: ResumeInput):
    """텍스트 직접 분석"""
    try:
        if not resume_input.content.strip():
            raise HTTPException(status_code=400, detail="내용이 비어있습니다.")
        
        result = analyze_resume(resume_input.content)
        return result
    except Exception as e:
        traceback.print_exc()
        raise HTTPException(status_code=500, detail=f"분석 실패: {str(e)}")

@app.post("/analyze_resume_file", response_model=ResumeAnalysisResult)
async def analyze_resume_file_endpoint(file: UploadFile = File(...)):
    """파일 업로드 분석"""
    # 파일 크기 제한 (10MB)
    if file.size and file.size > 10 * 1024 * 1024:
        raise HTTPException(status_code=413, detail="파일 크기는 10MB 이하여야 합니다.")
    
    if not file.filename.lower().endswith((".txt", ".docx")):
        raise HTTPException(status_code=400, detail="txt 또는 docx 파일만 업로드 가능합니다.")
    
    suffix = os.path.splitext(file.filename)[1]
    contents = await file.read()
    
    # 빈 파일 체크
    if len(contents) == 0:
        raise HTTPException(status_code=400, detail="빈 파일입니다.")
    
    with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
        tmp.write(contents)
        tmp_path = tmp.name

    try:
        result = analyze_file(tmp_path)
        return result
    except Exception as e:
        traceback.print_exc()
        raise HTTPException(status_code=500, detail=f"파일 분석 실패: {str(e)}")
    finally:
        # 임시 파일 정리
        if os.path.exists(tmp_path):
            os.remove(tmp_path)

# 서버 시작 시 모델 로딩
@app.on_event("startup")
async def startup_event():
    """서버 시작 시 모델 미리 로딩"""
    print("=== Resume Analysis Server Starting ===")
    try:
        # 모델 미리 로딩하여 첫 요청 지연시간 단축
        from analyzer import get_readability_model
        get_readability_model()
        print("✅ 모델 로딩 완료")
    except Exception as e:
        print(f"⚠️ 모델 로딩 중 오류: {e}")
    print("=== Server Ready ===")

@app.on_event("shutdown")
async def shutdown_event():
    """서버 종료 시 정리 작업"""
    print("=== Resume Analysis Server Shutting Down ===")

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        app, 
        host="0.0.0.0", 
        port=8000,
        log_level="info"
    )