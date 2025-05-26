from sentence_transformers import SentenceTransformer
from keybert import KeyBERT
import traceback


def test_extract_keyword(text: str) -> list[str]:
    if not text.strip():
        return []
    try:
        model = SentenceTransformer("snunlp/KR-SBERT-V40K-klueNLI-augSTS")
        kw_model = KeyBERT(model)
        keywords = kw_model.extract_keywords(text, 
                                             keyphrase_ngram_range=(2, 3),
                                             stop_words=None,
                                             top_n=10,
                                             use_mmr=True,
                                             diversity=0.5,
                                             nr_candidates=50)
        return [kw for kw, _ in keywords]
    except Exception as e:
        traceback.print_exc()
        return []
    

ex_text = """"끊임없이 노력하며 나아가는 삶" 제가 개발자가 되기로 결심한 뒤, 마음속에 항상 품고 있는 저의 신조입니다. 남들보다 늦은 대학교 3학년 때에 저는 제 진로를 결심할 수 있었습니다. 그때에 저는 웹과 앱 만들기에 관심이 있었고, 두 가지 모두에 쓰이는 Java 언어를 공부하기로 마음먹었습니다. 이후로 프로젝트를 진행하며 Spring Boot, Spring Data JPA, QueryDSL을 사용한 개발 공부를 진행 중이고, 이를 사용한 여러 프로젝트를 진행해 보았습니다. 해당 IDE와 언어에 대해 단순히 원하는 기능을 구현하는 것을 넘어, 코드의 유지보수성과 확장성을 고려한 개발을 지향하고 있습니다."""
ex_text1 = """컴퓨터공학을 전공하며 자연스럽게 개발에 흥미를 가지게 되었고, 특히 백엔드 시스템을 설계하고 구현하는 데 깊은 관심을 가지게 되었습니다. 

대학 시절 팀 프로젝트로 게시판 웹 서비스를 개발하며 Spring Boot와 MySQL을 활용해 REST API를 설계하고 구현한 경험이 있습니다. 당시 Git을 활용한 협업, ERD 설계, 예외 처리 등을 직접 경험하며 백엔드 개발의 핵심을 체득할 수 있었습니다.

이후에도 개인 프로젝트를 통해 JWT 기반 인증 시스템, Docker를 활용한 배포 자동화 등을 구현해보며 개발 역량을 높이고자 노력했습니다. 저는 기술의 깊이뿐 아니라 협업 능력도 중요하다고 생각하며, 꾸준히 커뮤니케이션 역량을 키우고 있습니다.

앞으로도 백엔드 분야에서 지속적으로 학습하고 성장하며, 서비스의 안정성과 확장성을 고려한 개발을 지향하고 싶습니다.
"""
ex_text2 = """저는 소비자의 행동을 분석하고, 그에 맞는 전략을 기획하는 일에 매력을 느껴 마케팅 직무를志望하게 되었습니다. 

대학교 재학 중에는 소비자 심리학과 빅데이터 분석 과목을 수강하며 마케팅의 기초 이론과 실무 적용 방법을 학습했습니다. 특히 교내 광고 공모전에서 팀장 역할을 맡아, 시장조사부터 캠페인 기획, 홍보 영상 제작까지 전 과정을 주도적으로 수행하였습니다.

그 과정에서 데이터 기반 의사결정의 중요성을 느껴 Python을 활용한 설문 분석 및 SNS 키워드 분석 프로젝트를 진행하였고, 실제로 브랜드 인지도 개선에 기여한 바 있습니다. 또한, 다양한 팀원과의 협업을 통해 커뮤니케이션 역량을 키울 수 있었습니다.

앞으로도 데이터를 기반으로 한 정교한 마케팅 전략을 기획하고 실행할 수 있는 마케터가 되기 위해 끊임없이 배우고 도전하고자 합니다.
"""
print(test_extract_keyword(ex_text2))