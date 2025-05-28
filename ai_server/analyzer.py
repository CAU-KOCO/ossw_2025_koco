from models import ResumeAnalysisResult, FeedbackItem, SentenceAnalysisItem
from hanspell import spell_checker
from keybert import KeyBERT
from sentence_transformers import SentenceTransformer
from dotenv import load_dotenv, find_dotenv
from docx import Document
import openai
import traceback
import kss
import json
import os


dotenv_path = find_dotenv()
load_dotenv(dotenv_path)

openai.api_key = os.getenv("OPENAI_API_KEY")
gpt_model = "gpt-3.5-turbo"

sbert_model = SentenceTransformer("snunlp/KR-SBERT-V40K-klueNLI-augSTS")
kw_model = KeyBERT(sbert_model)

def analyze_resume(content: str) -> ResumeAnalysisResult:
    # TODO: 맞춤법 검사 로직 추가 완료, 키워드 추출, 피드백 로직 추가 완료
    grammar_issues = []
    
    # 맞춤법 검사
    try:
        check_content = spell_checker.check(content)
        corrected_text = check_content.checked

        sentences = split_sentence(content)

        for sentence in sentences:
            issues = check_grammar(sentence)
            grammar_issues.extend(issues)

    except Exception as e:
        traceback.print_exc()
        corrected_text = content
        grammar_issues.append(f"Error : 맞춤법 검사 중 오류가 발생했습니다. 오류 : {e}")

    sentence_results = []

    for sent in split_sentence(corrected_text):
        # 키워드 추출
        keywords = extract_keyword(sent)
        
        # 피드백 생성
        comment = generate_feedback(sent)

        sentence_results.append(SentenceAnalysisItem(
            sentence=sent,
            feedback=[comment],
            keywords=keywords
        ))
            
    return ResumeAnalysisResult(
        original_text=content,
        corrected_text=corrected_text,
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
        #stop_words_ko = load_stopwords_from_json()
        keywords = kw_model.extract_keywords(text, 
                                             keyphrase_ngram_range=(2, 3),
                                             stop_words=None,
                                             top_n=4,
                                             use_mmr=True,
                                             diversity=0.5,
                                             nr_candidates=30)
        return [kw for kw, _ in keywords]
    except Exception as e:
        traceback.print_exc()
        return []
    
    
def load_stopwords_from_json() -> list[str]:
    base_dir = os.path.dirname(__file__)
    filepath = os.path.join(base_dir, "resources", "stop_words_ko.json")
    with open(filepath, encoding='utf-8') as f:
        return json.load(f)
    
def generate_feedback(sentence: str) -> str:
    try:
        prompt = (
            "아래 문장의 장점과 개선할 점을 간단히 알려줘:\n"  
            f"\"{sentence}\""
        )
        response = openai.chat.completions.create(
            model = gpt_model,
            messages =[{"role": "user", "content": prompt}],
            max_tokens=250,
            temperature=0.7
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        traceback.print_exc()
        return f"Error : 피드백 생성 중 오류가 발생했습니다. 오류 : {e}"
    
def load_file_content(path: str) -> str:
    _, ext = os.path.splitext(path.lower())
    if ext == ".txt":
        with open(path, "r", encoding="utf-8") as f:
            return f.read()
    elif ext == ".docx":
        #
        print(f"[load_file_content] DOCX 파싱 시작: {path}") # 디버깅
        try:
            doc = Document(path)
        except Exception as e:
            print(f"[load_file_content] DOCX 파싱 오류: {e}")
            raise
        #
        #doc = Document(path) 원 함수
        #return "\n".join([p.text for p in doc.paragraphs])

        text = "\n".join(p.text for p in doc.paragraphs)
        print(f"[load_file_content] DOCX 파싱 완료, 길이={len(text)}")
        return text
    else:
        raise ValueError(f"지원하지 않는 파일 형식입니다: {ext}")
    

    
def analyze_file(path: str) -> ResumeAnalysisResult:
    text = load_file_content(path)
    return analyze_resume(text)