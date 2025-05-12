from pydantic import BaseModel
from typing import List

class ResumeInput(BaseModel):
    content: str

class FeedbackItem(BaseModel):
    sentence: str
    comment: str

class ResumeAnalysisResult(BaseModel):
    keywords: List[str]
    feedback: List[FeedbackItem]
    grammar_issues: List[str]
    suggested_sentences: List[str]
