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
    readability_score: float    

class ResumeAnalysisResult(BaseModel):
    original_text: str
    corrected_text: str
    sentences: List[SentenceAnalysisItem]
    grammar_issues: List[str]
    suggested_sentences: List[str]
