use springweb2;

-- [1] 기본 employee 테이블 생성
CREATE TABLE employee (
    emp_id INT,
    emp_name VARCHAR(50),
    dept VARCHAR(30)
);


-- [2] 나이 컬럼 추가
ALTER TABLE employee
ADD COLUMN age INT;

-- [3] 급여 컬럼 추가 (기본값 설정)
ALTER TABLE employee
ADD COLUMN salary INT DEFAULT 3000;

-- [4] 입사일 컬럼 추가 (DATE형)
ALTER TABLE employee
ADD COLUMN hire_date DATE;

ALTER TABLE employee
ADD COLUMN email VARCHAR(100),
ADD COLUMN phone VARCHAR(20);

-- [5] 급여 컬럼의 데이터타입을 수정 (INT → DECIMAL)
ALTER TABLE employee
MODIFY COLUMN salary DECIMAL(10,2);

-- [6] 컬럼 이름까지 변경 (CHANGE)
ALTER TABLE employee
CHANGE COLUMN emp_name employee_name VARCHAR(100);

-- [9] phone 컬럼 삭제
ALTER TABLE employee
DROP COLUMN phone;

-- [10] email 컬럼 삭제
ALTER TABLE employee
DROP COLUMN email;

SHOW COLUMNS FROM employee;

# ============================================= #

-- [2] emp_id 컬럼을 기본키로 설정
ALTER TABLE employee
ADD CONSTRAINT pk_employee PRIMARY KEY (emp_id);

-- [3] 부서명은 중복 불가 (UNIQUE)
ALTER TABLE employee
ADD CONSTRAINT uq_dept UNIQUE (dept);

-- [4] 나이는 19세 이상만 가능하도록 CHECK
ALTER TABLE employee
ADD CONSTRAINT chk_age CHECK (age >= 19);
-- [5] 급여는 1000 이상이어야 함
ALTER TABLE employee
ADD CONSTRAINT chk_salary CHECK (salary >= 1000);

-- [6] 급여 제약조건 삭제
ALTER TABLE employee
DROP CONSTRAINT chk_salary;

-- [7] 급여 기준을 2000 이상으로 변경하여 다시 추가
ALTER TABLE employee
ADD CONSTRAINT chk_salary_new CHECK (salary >= 2000);

-- [8] 제약조건 확인
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
	WHERE TABLE_SCHEMA = 'springweb2'   -- 데이터베이스명
	AND TABLE_NAME = 'employee';      -- 테이블명


