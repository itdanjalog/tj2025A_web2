USE springweb2;

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,
    salary INT,
    hire_date DATE,
    email VARCHAR(100)
);
-- 부서 테이블 생성
DROP TABLE IF EXISTS department;
CREATE TABLE department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50)
);

-- 샘플 데이터
INSERT INTO department VALUES
(1, '개발팀'),
(2, '기획팀'),
(3, '디자인팀');

INSERT INTO employee (name, dept_id, salary, hire_date, email) VALUES
('유재석', 1, 5000, '2023-01-10', 'yu@test.com'),
('강호동', 2, 4000, '2024-02-12', 'kang@test.com'),
('신동엽', 1, 7000, '2022-06-05', 'shin@test.com'),
('이수근', 2, 5500, '2025-03-22', 'lee@test.com'),
('하하', 3, 3500, '2025-04-10', 'haha@test.com'),
('정형돈', 1, 6200, '2023-07-11', 'don@test.com'),
('박명수', 2, 4800, '2023-09-02', 'park@test.com'),
('노홍철', 3, 3700, '2024-05-14', 'noh@test.com'),
('김종국', 1, 8000, '2022-11-01', 'kim@test.com'),
('양세형', 2, 4300, '2024-06-21', 'yang@test.com'),
('이광수', 3, 3900, '2023-12-12', 'kwang@test.com'),
('조세호', 1, 5100, '2023-03-18', 'cho@test.com'),
('김용만', 2, 4600, '2022-09-23', 'yong@test.com'),
('정준하', 3, 3600, '2024-04-04', 'jun@test.com'),
('김태호', 1, 9000, '2021-10-15', 'taeho@test.com'),
('서장훈', 2, 5800, '2024-08-25', 'seo@test.com'),
('전현무', 3, 4000, '2022-12-01', 'jeon@test.com'),
('김구라', 1, 7500, '2023-11-05', 'gura@test.com'),
('유병재', 2, 4200, '2025-01-20', 'yoo@test.com'),
('김민아', 3, 3800, '2024-10-08', 'mina@test.com');


-- 💡 인덱스(Index)란?
-- 인덱스란, 테이블의 데이터를 빠르게 검색하기 위해 생성하는 “색인(索引)” 구조입니다.
-- 도서관에서 “책 제목 순서대로 정리된 색인표”처럼,
-- 인덱스는 MySQL이 데이터를 빠르게 찾을 수 있도록 만들어둔 별도의 자료 구조입니다.

-- ✅ 검색 속도 향상 (SELECT)	WHERE 조건 검색, JOIN, ORDER BY 등의 속도를 크게 향상시킴
-- ✅ 정렬 효율 향상 (ORDER BY)	인덱스 컬럼 기준으로 정렬 시, 별도의 정렬 연산 없이 결과를 반환
-- ✅ 그룹핑 효율 향상 (GROUP BY)	인덱스 컬럼이 그룹화 기준이면 빠르게 집계 가능
-- ⚙️ 제약조건 유지 (UNIQUE INDEX)	중복값을 자동으로 방지
-- 🔍 텍스트 검색 (FULLTEXT INDEX)	긴 문자열에서 빠른 검색 지원 (ex. 게시판 내용 검색 등)


-- 🔍 [2] 인덱스 기본 생성
-- 1️⃣ 단일 컬럼 인덱스
CREATE INDEX idx_name ON employee(name);
-- 2️⃣ 복합 인덱스
CREATE INDEX idx_dept_salary ON employee(dept_id, salary);
-- 3️⃣ UNIQUE 인덱스 (중복 방지)
CREATE UNIQUE INDEX idx_email_unique ON employee(email);
-- 4️⃣ 인덱스 목록 조회
SHOW INDEX FROM employee;
-- 5️⃣ 인덱스 삭제
DROP INDEX idx_name ON employee;

-- 🧩 [3] 인덱스 전/후 성능 비교
-- 인덱스 없는 상태 (테이블 전체 스캔)
EXPLAIN ANALYZE SELECT * FROM employee WHERE name = '유재석';
-- 인덱스 추가
CREATE INDEX idx_emp_name ON employee(name);
-- 인덱스 적용 후
EXPLAIN ANALYZE SELECT * FROM employee WHERE name = '유재석';

-- 📊 [4] ORDER BY 최적화 예제
-- 인덱스 없는 정렬
EXPLAIN ANALYZE SELECT * FROM employee ORDER BY salary DESC;
-- 정렬 인덱스 추가
CREATE INDEX idx_salary ON employee(salary);
DROP INDEX idx_salary ON employee;
EXPLAIN ANALYZE SELECT * FROM employee ORDER BY salary DESC;

-- ⚡ [5] 복합 인덱스 효과 (조합 순서 비교)
-- 복합 인덱스 생성 (dept_id, salary)
CREATE INDEX idx_dept_salary2 ON employee(dept_id, salary);
-- ① dept_id만 사용하는 경우 → ✅ 인덱스 사용됨
EXPLAIN ANALYZE SELECT * FROM employee WHERE dept_id = 1;
-- ② salary만 사용하는 경우 → ❌ 인덱스 사용 안 됨
EXPLAIN ANALYZE SELECT * FROM employee WHERE salary = 7000;
-- ③ dept_id + salary 함께 사용하는 경우 → ✅ 인덱스 사용됨
EXPLAIN ANALYZE SELECT * FROM employee WHERE dept_id = 1 AND salary > 4000;

-- 🔠 [6] FULLTEXT 인덱스 (문자열 검색용)
-- Fulltext 인덱스는 TEXT, CHAR, VARCHAR 컬럼에서만 가능
CREATE FULLTEXT INDEX idx_name_full ON employee(name);
-- 자연어 검색
SELECT * FROM employee WHERE MATCH(name) AGAINST('유재석');

-- 🧮 [7] 함수 기반 인덱스 (MySQL 8.0 이상)
-- 대소문자 구분 없는 검색용 인덱스
CREATE INDEX idx_lower_email ON employee ((LOWER(email)));

-- 실행 테스트
EXPLAIN ANALYZE SELECT * FROM employee WHERE LOWER(email) = 'yu@test.com';

-- 🔗 [8] JOIN 최적화 예제

-- 조인 시 인덱스 없이
EXPLAIN ANALYZE
SELECT e.name, d.dept_name
FROM employee e
JOIN department d ON e.dept_id = d.dept_id;

-- dept_id 인덱스 추가
CREATE INDEX idx_emp_dept ON employee(dept_id);
DROP INDEX idx_emp_dept ON employee;

-- 인덱스 조인 최적화 확인
EXPLAIN ANALYZE
SELECT e.name, d.dept_name
FROM employee e
JOIN department d ON e.dept_id = d.dept_id;

-- 🔎 [9] 인덱스 조회 정리
SHOW INDEX FROM employee;
SHOW INDEX FROM department;


