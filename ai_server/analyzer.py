from models import ResumeAnalysisResult, FeedbackItem
from hanspell import spell_checker
import traceback
import kss

def analyze_resume(content: str) -> ResumeAnalysisResult:
    # TODO: 맞춤법 검사 로직 추가 완료, 키워드 추출, 피드백 로직 추가예정
    # 문장 분리 진행중
    sentences = split_sentences(content)
    grammar_issues = []
    feedback_items = []

    for sentence in sentences:
        issues = check_grammar(sentence)
        grammar_issues.extend(issues)

        if "다양한 활동" in sentence:
            feedback_items.append(FeedbackItem(
                category="활동",
                description="다양한 활동을 통해 팀워크를 강조하세요.",
                suggestion="팀 프로젝트 경험을 구체적으로 서술하세요."
            ))
            
    return ResumeAnalysisResult(
        keywords=["팀워크", "프로젝트"],
        feedback=feedback_items,
        grammar_issues=grammar_issues,
        suggested_sentences=[]
    )

def split_sentences(text: str) -> list[str]:
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
