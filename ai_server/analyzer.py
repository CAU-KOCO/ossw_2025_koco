from models import ResumeAnalysisResult, SentenceAnalysisItem
from hanspell import spell_checker
from keybert import KeyBERT
from sentence_transformers import SentenceTransformer
from transformers import AutoTokenizer, AutoModelForCausalLM
from dotenv import load_dotenv, find_dotenv
from docx import Document
from huggingface_hub import login
import openai
import traceback
import kss
import json
import os
import re


dotenv_path = find_dotenv()
load_dotenv(dotenv_path)

openai.api_key = os.getenv("OPENAI_API_KEY")
gpt_model = "gpt-3.5-turbo"

sbert_model = SentenceTransformer("snunlp/KR-SBERT-V40K-klueNLI-augSTS")
kw_model = KeyBERT(sbert_model)

hyperclovax_model = "naver-hyperclovax/HyperCLOVAX-SEED-Text-Instruct-1.5B"
login(token=os.getenv("HUGGINGFACE_TOKEN"))

# 전역 변수로 모델 한 번만 로딩
_readability_tokenizer = None
_readability_model = None

def get_readability_model():
    """가독성 모델을 싱글톤 패턴으로 로딩"""
    global _readability_tokenizer, _readability_model
    
    if _readability_tokenizer is None or _readability_model is None:
        print("HyperCLOVAX 모델 로딩 중...")
        _readability_tokenizer = AutoTokenizer.from_pretrained(hyperclovax_model)
        _readability_model = AutoModelForCausalLM.from_pretrained(hyperclovax_model)
        print("모델 로딩 완료!")
    
    return _readability_tokenizer, _readability_model


def analyze_resume(content: str) -> ResumeAnalysisResult:
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

    # 문장 분리
    for sent in split_sentence(corrected_text):
        # 키워드 추출
        keywords = extract_keyword(sent)
        
        # 피드백 생성
        comment = generate_feedback(sent)

        readability_score = calculate_readability_score(sent)

        sentence_results.append(SentenceAnalysisItem(
            sentence=sent,
            feedback=[comment],
            keywords=keywords,
            readability_score=readability_score
        ))
            
    return ResumeAnalysisResult(
        original_text=content,
        corrected_text=corrected_text,
        sentences=sentence_results,
        grammar_issues=grammar_issues,
        suggested_sentences=[]
    )

# 문장 분리
def split_sentence(text: str) -> list[str]:
    return list(kss.split_sentences(text))

# 맞춤법 검사
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

# 키워드 추출   
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

# 피드백 생성    
def generate_feedback(sentence: str) -> str:
    try:
        prompt = (
            "아래 문장의 장점과 개선할 점을 간단히 알려줘:\n"  
            f"\"{sentence}\""
        )
        response = openai.chat.completions.create(
            model = gpt_model,
            messages =[{"role": "user", "content": prompt}],
            max_tokens=256,
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
        try:
            doc = Document(path)
            return "\n".join(p.text for p in doc.paragraphs if p.text.strip())
        except Exception as e:
            raise RuntimeError(f"DOCX 파싱 중 오류 발생: {e}")
    
    else:
        raise ValueError(f"지원하지 않는 파일 형식입니다: {ext}")

    
def analyze_file(path: str) -> ResumeAnalysisResult:
    text = load_file_content(path)
    return analyze_resume(text)

def calculate_readability_score(text: str) -> float:
    try:
        # 싱글톤 패턴으로 모델 한 번만 로딩
        tokenizer, model = get_readability_model()
        
        prompt = (
            "<|user|> "
            "다음 문장의 가독성을 0부터 100까지의 숫자로만 평가하세요.\n"
            "0은 매우 읽기 어려움, 100은 매우 읽기 쉬움을 의미합니다.\n"
            "반드시 숫자만 출력하세요. 설명이나 다른 텍스트는 출력하지 마세요.\n\n"
            "예시:\n"
            "문장: 팀원과의 협업은 저에게 큰 도움이 되었습니다.\n답: 85\n"
            "문장: 항상 최선을 다하는 자세로 임합니다.\n답: 72\n\n"
            f"문장: {text}\n답: "
            "<|assistant|> "
        )

        input_ids = tokenizer(prompt, return_tensors="pt").input_ids 
        output_ids = model.generate(
            input_ids, 
            max_new_tokens=5,  
            temperature=0.7,  
            do_sample=True,
            pad_token_id=tokenizer.eos_token_id
        )
        
        new_tokens = output_ids[0][len(input_ids[0]):]
        response = tokenizer.decode(new_tokens, skip_special_tokens=True).strip()
        
        # 프로덕션에서는 디버깅 출력 제거 또는 로깅으로 변경
        if os.getenv("DEBUG") == "true":
            print(f"가독성 분석 - 입력: '{text[:30]}...' -> 점수: {response}")
        
        matches = re.findall(r'\b\d{1,3}\b', response)
        
        if matches:
            score = int(matches[0])  
            if 0 <= score <= 100:
                return float(score)
        
        all_numbers = re.findall(r'\d{1,3}', response)
        for num_str in all_numbers:
            score = int(num_str)
            if 0 <= score <= 100:
                return float(score)

        print(f"경고: 유효한 점수를 추출할 수 없습니다. 응답: '{response}'")
        return 50.0  # 기본값을 0.0 대신 50.0으로 변경

    except Exception as e:
        print(f"가독성 점수 계산 오류: {e}")
        traceback.print_exc()
        return 50.0  # 오류 시에도 50.0 반환

# 메모리 정리용 함수 (선택적)
def cleanup_models():
    """메모리 정리가 필요할 때 사용"""
    global _readability_tokenizer, _readability_model
    _readability_tokenizer = None
    _readability_model = None
    print("가독성 모델 메모리에서 제거됨")