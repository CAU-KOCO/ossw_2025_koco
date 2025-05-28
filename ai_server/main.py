from fastapi import FastAPI, UploadFile, File, HTTPException
from models import ResumeInput, ResumeAnalysisResult
from analyzer import analyze_resume, analyze_file
import os
import tempfile
import traceback

app = FastAPI()

@app.post("/analyze_resume", response_model=ResumeAnalysisResult)
def analyze_resume_endpoint(resume_input: ResumeInput):
  result = analyze_resume(resume_input.content)
  return result


@app.post("/analyze_resume_file", response_model=ResumeAnalysisResult)
async def analyze_resume_file_endpoint(file: UploadFile = File(...)):
    if not file.filename.lower().endswith((".txt", ".docx")):
        raise HTTPException(400, "txt 또는 docx 파일만 업로드 가능합니다.")
    
    suffix = os.path.splitext(file.filename)[1]
    contents = await file.read()
    
    with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
        tmp.write(contents)
        tmp_path = tmp.name

    try:
        result = analyze_file(tmp_path)
    except Exception as e:
        traceback.print_exc()
        raise HTTPException(500, f"분석 실패: {e}")
    finally:
        os.remove(tmp_path)

    return result
# 1. 맞춤법 검사 추가 완료
# 2. 문장 분리 추가 완료
# 3. 키워드 추출 추가 완료
# 4. 피드백 생성 추가 완료
# 키워드 추출 불용어 stopwords-ko 사용 -> 사용 안 하는게 품질 좋아서 사용 x
# 5. 텍스트 파일, word 파일일 업로드 추가