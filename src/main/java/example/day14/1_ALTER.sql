/*
	SQL
		DDL : 정의어
			CREATE		테이블/데이터베이스 생성
            DROP		테이블/데이터베이스 삭제 
            *ALTER		테이블 정보 수정 
        DML : 조작어
			INSERT
            SELECT
            UPDATE
            DELETE
        DCL : 제어어
*/
use springweb2; -- 사용할 데이터베이스 선택 

# [1] 생성된 테이블 수정하기 
DROP TABLE IF EXISTS employee;
CREATE TABLE employee(		-- 테이블생성 
	id int ,
    name varchar(50),
    dept varchar(30)
);
# [2] 기존 테이블의 필드 추가 
# ALTER TABLE 테이블명 ADD COLUMN 새로운필드명 타입및제약조건;
ALTER TABLE employee ADD COLUMN age int default 10;
ALTER TABLE employee ADD COLUMN date DATE;

# [3] 기존 테이블의 필드 타입 수정 
# ALTER TABLE 테이블명 MODIFY COLUMN 수정할필드명 새로운타입
ALTER TABLE employee MODIFY dept longtext;

# [4] 기존 테이블의 필드명 수정
# ALTER TABLE 테이블명 CHANGE COLUMN 기존필드명 새로운필드명 타입;
ALTER TABLE employee CHANGE COLUMN name nickname varchar(100);

# [5] 기존 테이블의 필드 삭제 , 필드명==속성명==컬럼명
# ALTER TABLE 테이블명 DROP COLUMN 삭제할필드명; 
ALTER TABLE employee DROP COLUMN date;

# [6] 특정한 테이블의 필드 확인 
SHOW COLUMNS FROM employee;	-- 특정한 테이블의 필드 정보 확인( 속성 정보 확인 )

# [7] 제약조건 추가 
# ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명(아무거나/중복불가) PRIMARY KEY (pk필드명);
# ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 FOREIGN KEY(FK필드명) REGERENCES 참조테이블명(PK필드명);
ALTER TABLE employee ADD CONSTRAINT employee_id PRIMARY KEY (id);
ALTER TABLE employee ADD CONSTRAINT employee_name UNIQUE( name ); 

# [8] 제약조건 삭제 
# ALTER TABLE 테이블명 DROP PRIMARY KEY;
ALTER TABLE employee DROP PRIMARY KEY;
# ALTER TABLE 테이블명 DROP FOREIGN KEY 삭제할FK제약조건명;

# ALTER TABLE 테이블명 DROP CONSTRAINT 삭제할제약조건명;
ALTER TABLE employee DROP CONSTRAINT employee_name;

# [9] 수정 없이 삭제후 다시 제약조건 추가 
# [10] 제약조건 확인 
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS;
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = "springweb2";
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
	WHERE TABLE_SCHEMA = "springweb2" and TABLE_NAME ="employee";

SELECT * FROM employee;		-- 특정한 테이블의 레코드 정보 확인( 속성값 확인 )













