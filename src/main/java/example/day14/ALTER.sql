-- ===========================================
-- ✅ 데이터베이스 선택
-- springweb2 데이터베이스를 사용하겠다는 뜻입니다.
-- (USE 명령은 현재 작업할 DB를 지정할 때 사용)
-- ===========================================
USE springweb2;


-- ===========================================
-- [1] employee 테이블 생성 (기본 구조 만들기)
-- ===========================================

-- 만약 employee 테이블이 이미 존재한다면 삭제합니다.
DROP TABLE IF EXISTS employee;

-- employee 테이블을 새로 만듭니다.
-- id(사번), name(이름), dept(부서) 3개의 컬럼을 정의합니다.
CREATE TABLE employee (
    id INT,              -- 사번 (정수형)
    name VARCHAR(50),    -- 이름 (문자열, 최대 50자)
    dept VARCHAR(30)     -- 부서명 (문자열, 최대 30자)
);


-- ===========================================
-- [2] 컬럼 추가: 나이(age)
-- ===========================================

-- employee 테이블에 age 컬럼을 추가합니다.
ALTER TABLE employee
ADD COLUMN age INT;      -- 나이(정수형)


-- ===========================================
-- [3] 컬럼 추가: 급여(salary), 기본값 3000
-- ===========================================

-- 새로운 컬럼 salary를 추가하고
-- 아무 값도 입력하지 않으면 자동으로 3000이 들어가게 설정합니다.
ALTER TABLE employee
ADD COLUMN salary INT DEFAULT 3000;


-- ===========================================
-- [4] 컬럼 추가: 입사일(date)
-- ===========================================

-- DATE 타입의 date 컬럼을 추가합니다.
-- 날짜 형식(예: 2025-10-14) 데이터를 저장할 수 있습니다.
ALTER TABLE employee
ADD COLUMN date DATE;

-- 한 번에 여러 컬럼도 추가할 수 있습니다.
ALTER TABLE employee
ADD COLUMN email VARCHAR(100),   -- 이메일 주소
ADD COLUMN phone VARCHAR(20);    -- 전화번호


-- ===========================================
-- [5] 컬럼 수정: salary 데이터 타입 변경
-- ===========================================

-- 기존 salary 컬럼의 데이터 타입을 INT → BIGINT로 변경합니다.
-- BIGINT는 더 큰 숫자를 저장할 수 있습니다.
ALTER TABLE employee
MODIFY COLUMN salary BIGINT;


-- ===========================================
-- [6] 컬럼 이름 변경: name → nickname
-- ===========================================

-- 컬럼 이름(name)을 nickname으로 바꾸고,
-- 타입은 VARCHAR(100)으로 확장합니다.
ALTER TABLE employee
CHANGE COLUMN name nickname VARCHAR(100);


-- ===========================================
-- [9] 컬럼 삭제: phone
-- ===========================================

-- phone 컬럼을 테이블에서 제거합니다.
ALTER TABLE employee
DROP COLUMN phone;


-- ===========================================
-- [10] 컬럼 삭제: email
-- ===========================================

-- email 컬럼을 테이블에서 제거합니다.
ALTER TABLE employee
DROP COLUMN email;


-- ===========================================
-- 현재 employee 테이블 구조 확인
-- ===========================================

-- SHOW COLUMNS 명령은 테이블의 컬럼 정보(이름, 타입, NULL 허용 등)를 보여줍니다.
SHOW COLUMNS FROM employee;


-- ==================================================
-- ✅ 제약조건(Constraints) 추가 및 관리
-- ==================================================

-- 제약조건(Constraint)이란 데이터의 정확성과 일관성을 유지하기 위한 규칙입니다.
-- (예: 중복 금지, NULL 금지, 특정 범위 제한 등)


-- [2] 기본키(Primary Key) 설정
-- id 컬럼을 기본키로 지정합니다.
-- 기본키는 중복 불가 + NULL 불가 조건을 자동으로 가집니다.
ALTER TABLE employee
ADD CONSTRAINT pk_employee PRIMARY KEY (id);


-- [3] UNIQUE 제약조건 추가
-- dept 컬럼의 값은 중복될 수 없게 합니다.
-- (같은 부서명이 2개 이상 들어가면 오류 발생)
ALTER TABLE employee
ADD CONSTRAINT uq_dept UNIQUE (dept);


-- [4] CHECK 제약조건 추가 (나이 제한)
-- age 컬럼의 값이 19 이상일 때만 허용합니다.
ALTER TABLE employee
ADD CONSTRAINT chk_age CHECK (age >= 19);


-- [5] CHECK 제약조건 추가 (급여 제한)
-- salary가 1000 이상일 때만 허용합니다.
ALTER TABLE employee
ADD CONSTRAINT chk_salary CHECK (salary >= 1000);


-- [6] 제약조건 삭제
-- 급여 관련 제약조건(chk_salary)을 삭제합니다.
ALTER TABLE employee
DROP CONSTRAINT chk_salary;


-- [7] 수정된 제약조건 다시 추가
-- 이번에는 급여 기준을 2000 이상으로 변경합니다.
ALTER TABLE employee
ADD CONSTRAINT chk_salary_new CHECK (salary >= 2000);


-- [8] 제약조건 목록 확인
-- 현재 employee 테이블에 걸려 있는 제약조건을 모두 확인할 수 있습니다.
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
	WHERE TABLE_SCHEMA = 'springweb2'   -- 데이터베이스 이름 지정
	AND TABLE_NAME = 'employee';         -- 테이블 이름 지정
