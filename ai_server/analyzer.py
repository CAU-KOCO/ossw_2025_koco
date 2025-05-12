from models import ResumeAnalysisResult, FeedbackItem
from hanspell import spell_checker
import traceback

def analyze_resume(content: str) -> ResumeAnalysisResult:
    # TODO: 맞춤법 검사 로직 추가 완료, 이후 키워드 추출 및 피드백 로직 추가
    grammar_issues = check_grammar(content)

    return ResumeAnalysisResult(
        keywords=["팀워크", "프로젝트"],
        feedback=[
            FeedbackItem(
                sentence="저는 다양한 활동을 했습니다.",
                comment="구체적인 예시를 추가해보세요."
            )
        ],
        grammar_issues=grammar_issues,
        suggested_sentences=[]
    )

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
