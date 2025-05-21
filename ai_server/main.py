from fastapi import FastAPI
from models import ResumeInput, ResumeAnalysisResult
from analyzer import analyze_resume

app = FastAPI()

@app.post("/analyze_resume", response_model=ResumeAnalysisResult)
def analyze_resume_endpoint(resume_input: ResumeInput):
  result = analyze_resume(resume_input.content)
  return result


# 1. 맞춤법 검사 추가 완료
# 2. 문장 분리 추가 완료
# 3. 키워드 추출 추가 완료
# 키워드 추출 불용어 stopwords-ko 사용