USE springweb2;

# 🧩 1 단일행 서브쿼리
# 전체 평균 점수보다 높은 학생 찾기
# (단일 행: AVG() 결과는 1개의 값만 반환됨)
SELECT name, (kor + math) / 2 AS avg_score
FROM student
WHERE (kor + math) / 2 > (
    SELECT AVG((kor + math) / 2) FROM student
);
# 🧠 설명
# 서브쿼리 (SELECT AVG(...))는 전체 평균 점수를 계산
# 메인쿼리에서 학생별 평균이 그 값보다 큰 학생만 조회

# 🧮 2 다중행 서브쿼리 (IN)
# 국어 점수가 평균 이상인 학생과 동일한 점수를 가진 학생들 찾기
SELECT name, kor
FROM student
WHERE kor IN (
    SELECT kor
    FROM student
    WHERE kor >= (SELECT AVG(kor) FROM student)
);
# 🧠 설명
# 내부 서브쿼리에서 평균 이상의 국어 점수를 찾고
# 외부 쿼리에서 그 점수와 같은 국어 점수를 가진 학생들을 출력


# 🔁 3 상관 서브쿼리 (Correlated Subquery)
# 각 학생보다 점수가 높은 학생 수를 구하기
SELECT s1.name,
       (SELECT COUNT(*)
        FROM student s2
        WHERE (s2.kor + s2.math) > (s1.kor + s1.math)) AS higher_count
FROM student s1
ORDER BY higher_count;
# 🧠 설명
# s1 학생의 총점과 비교하여 더 높은 학생 수를 s2 서브쿼리로 계산
# 각 학생별 순위를 계산하는 응용 예제


# 💡 4. EXISTS 활용
# 수학 점수가 90점 이상인 학생이 존재할 경우만 결과 출력
SELECT name, kor, math
FROM student s
WHERE EXISTS (
    SELECT 1 FROM student WHERE math >= 90
);
# 🧠 설명
# EXISTS는 조건을 만족하는 레코드가 존재하는지만 판단
# 단 1건이라도 존재하면 외부 쿼리 전체를 실행


# 📊 5. FROM 절 서브쿼리
# 서브쿼리로 학생별 평균 점수를 계산한 결과를 정렬해서 출력
SELECT name, avg_score
FROM (
    SELECT name, (kor + math) / 2 AS avg_score
    FROM student
) AS sub
ORDER BY avg_score DESC;
# 🧠 설명
# FROM 절의 서브쿼리를 가상 테이블처럼 활용
# 실제 뷰(View)와 유사한 형태
