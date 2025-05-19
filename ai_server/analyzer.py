from models import ResumeAnalysisResult, FeedbackItem, SentenceAnalysisItem
from hanspell import spell_checker
from keybert import KeyBERT
from sentence_transformers import SentenceTransformer
import traceback
import kss


sbert_model = SentenceTransformer("snunlp/KR-SBERT-V40K-klueNLI-augSTS")
kw_model = KeyBERT(sbert_model)

def analyze_resume(content: str) -> ResumeAnalysisResult:
    # TODO: 맞춤법 검사 로직 추가 완료, 키워드 추출, 피드백 로직 추가예정
    sentences = split_sentence(content)
    grammar_issues = []
    sentence_results = []

    for sentence in sentences:
        issues = check_grammar(sentence)
        grammar_issues.extend(issues)

        # 키워드 추출
        keywords = extract_keyword(sentence)
        
        # 피드백 생성
        feedback = []

        sentence_results.append(SentenceAnalysisItem(
            sentence=sentence,
            feedback=feedback,
            keywords=keywords
        ))
            
    return ResumeAnalysisResult(
        sentences=sentence_results,
        grammar_issues=grammar_issues,
        suggested_sentences=[]
    )

def split_sentence(text: str) -> list[str]:
    return list(kss.split_sentences(text))

def check_grammar(text: str) -> list[str]:
    try:
        checked = spell_checker.check(text)
        original, result = checked.original, checked.checked

        if not hasattr(checked, "original") or not hasattr(checked, "checked"):
            raise AttributeError("Missing 'original' or 'checked' in result")
        
        return [f"{original} -> {result}"] if original != result else []
    
    except Exception as e:
        traceback.print_exc()  # 예외 발생 시 스택 트레이스 출력
        return [f"Error : 맞춤법 검사 중 오류가 발생했습니다. 오류 : {e}"]
    
def extract_keyword(text: str) -> list[str]:
    if not text.strip():
        return []
    try:
        keywords = kw_model.extract_keywords(text, 
                                             keyphrase_ngram_range=(1, 2),
                                             stop_words=None,
                                             top_n=3,
                                             use_mmr=True,
                                             diversity=0.7,
                                             nr_candidates=30)
        return [kw for kw, _ in keywords]
    except Exception as e:
        traceback.print_exc()
        return []
