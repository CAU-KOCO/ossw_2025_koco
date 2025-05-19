from pydantic import BaseModel
from typing import List

class ResumeInput(BaseModel):
    content: str

class FeedbackItem(BaseModel):
    sentence: str
    comment: str

class SentenceAnalysisItem(BaseModel):
    sentence: str
    feedback: List[str]
    keywords: List[str]

class ResumeAnalysisResult(BaseModel):
    sentences: List[SentenceAnalysisItem]
    grammar_issues: List[str]
    suggested_sentences: List[str]
