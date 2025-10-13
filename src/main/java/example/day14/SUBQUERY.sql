USE springweb2;

# ============================================================
# 🧩 1. 단일행 서브쿼리 (Single Row Subquery)
# ============================================================
# [목표] 전체 평균보다 점수가 높은 학생 찾기

-- (1) 먼저 전체 평균을 구한다.
SELECT AVG( (kor + math) / 2) AS 전체평균 FROM student;

-- (2) 그 결과값을 조건절에 넣는다.
SELECT name, (kor + math) / 2 AS 평균점수
FROM student
WHERE (kor + math) / 2 > (
    SELECT AVG((kor + math) / 2) FROM student
);

-- 🧠 사고 순서 요약
-- ① 먼저 내부쿼리(서브쿼리)로 기준값(평균)을 구하고
-- ② 메인쿼리에서 그 기준보다 큰 데이터를 필터링한다.


# ============================================================
# 🧮 2. 다중행 서브쿼리 (Multi Row Subquery)
# ============================================================
# [목표] 국어 점수가 평균 이상인 학생들과 같은 점수를 가진 학생 조회

-- (1) 국어 점수가 평균 이상인 점수를 구한다.
SELECT kor FROM student WHERE kor >= (SELECT AVG(kor) FROM student);

-- (2) 그 결과에 해당하는 점수를 가진 학생을 조회한다.
SELECT name, kor
FROM student
WHERE kor IN (
    SELECT kor
    FROM student
    WHERE kor >= (SELECT AVG(kor) FROM student)
);


# ============================================================
# 🔁 3. 상관 서브쿼리 (Correlated Subquery)
# ============================================================
# [목표] 각 학생보다 총점이 높은 학생 수 계산하기

SELECT s1.name,
       (SELECT COUNT(*) FROM student s2
        WHERE (s2.kor + s2.math) > (s1.kor + s1.math)) AS higher_count
FROM student s1
ORDER BY higher_count;

-- 🧠 특징 : s1의 한 행마다 내부 쿼리가 반복 실행됨
-- 즉, s1이 10명이면 내부 쿼리도 10번 실행


# ============================================================
# 💡 4. EXISTS / NOT EXISTS
# ============================================================
# [목표] 수학 점수 90점 이상 학생이 ‘존재할 경우만’ 전체 학생 출력

SELECT name, kor, math
FROM student s
WHERE EXISTS (
    SELECT 1 FROM student WHERE math >= 90
);

# 반대로 ‘90점 이상 학생이 존재하지 않을 경우’
SELECT name, kor, math
FROM student s
WHERE NOT EXISTS (
    SELECT 1 FROM student WHERE math >= 90
);

-- 🧠 EXISTS는 단순히 “존재 여부”를 판단
-- 조건을 만족하는 데이터가 1건이라도 있으면 TRUE


# ============================================================
# 📊 5. FROM 절 서브쿼리 (Inline View)
# ============================================================
# [목표] 서브쿼리로 평균점수 계산 후 정렬

SELECT name, avg_score
FROM (
    SELECT name, (kor + math) / 2 AS avg_score
    FROM student
) AS sub
ORDER BY avg_score DESC;

# 응용 : 평균 80점 이상만 출력
SELECT *
FROM (
    SELECT name, (kor + math) / 2 AS avg_score
    FROM student
) AS sub
WHERE avg_score >= 80
ORDER BY avg_score DESC;

-- 🧠 FROM절 서브쿼리는 “가상 테이블”처럼 동작함
-- 실제로 뷰(View)를 미리 만드는 효과


# ============================================================
# 🧠 6. JOIN vs 서브쿼리 비교
# ============================================================

# ⚖️ 비교
# JOIN  → 병합(join)된 테이블 전체를 한 번에 조회 (속도 빠름)
# Subquery → 행마다 서브쿼리가 반복 수행됨 (직관적이나 느릴 수 있음)

# 🚀 권장 기준
# 단순 조회나 데이터 병합 → JOIN
# 조건비교 / 특정 값 기준 필터링 → Subquery


# ============================================================
# 🧩 7. 서브쿼리 작성 순서 연습 예제
# ============================================================

# [문제] “전체 평균 점수보다 높은 학생의 이름, 점수”를 출력

-- (1) 평균 구하기 (기준값 찾기)
SELECT AVG((kor + math) / 2) FROM student;

-- (2) 메인 쿼리 작성
SELECT name, (kor + math) / 2 AS avg_score
FROM student
WHERE (kor + math) / 2 > (
    SELECT AVG((kor + math) / 2) FROM student
);

-- (3) 정렬 추가
SELECT name, (kor + math) / 2 AS avg_score
FROM student
WHERE (kor + math) / 2 > (
    SELECT AVG((kor + math) / 2) FROM student
)
ORDER BY avg_score DESC;

# 💬 사고 순서 요약
# ① 기준이 되는 값을 먼저 구한다 (내부쿼리)
# ② 그 기준을 조건에 넣는다 (외부쿼리)
# ③ 필요한 컬럼만 SELECT → 정렬 / 조건추가
