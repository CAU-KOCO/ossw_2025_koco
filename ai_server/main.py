from fastapi import FastAPI
from models import ResumeInput, ResumeAnalysisResult
from analyzer import analyze_resume

app = FastAPI()

@app.post("/analyze_resume", response_model=ResumeAnalysisResult)
def analyze_resume_endpoint(resume_input: ResumeInput):
  result = analyze_resume(resume_input.content)
  return result

