
# DCL : 계정 관리, 계정의 사용권한 관리
# [1] 계정 만들기 : root(최고관리자) 계정으로 가능하다.
# 데이터베이스 설치하면 기본적으로 root 계정이 존재한다. < 강의기준 : root 사용중 >

# CREATE USER '계정명'@'허용도메인' identified by '비밀번호'
CREATE USER 'dev1'@'localhost' identified by '1234';		-- 로컬 전용 
create user 'dev2'@'%' identified by 'abcd';				-- 외부 접속 모두 허용 

# [2] (워크벤치) 다른 계정 접속하기 : 
# 1. 워크벤치 홈 메뉴( database -> Connect to database) -
# 2. usename : 계정명 , passwaord : 비밀번호 , [ok]
# 다른 (dev1)계정으로 접속후 : show databases;

# [3] 계정의 권한 부여 ( GRANT ) , *(와일드카드) : 전체 뜻한다.
# GRANT ALL privileges on 테이터베이스명.테이블/뷰명 to '계정명'@'도메인';
GRANT all privileges on springweb2.* to 'dev1'@'localhost'; # 확인 : (dev1)계정에서 접속후 확인 
grant select on springweb2.student to 'dev2'@'%'; # 확인 : (dev2)계정에서 접속후 확인 , delete 불가능.
# SHOW GRANTS FOR '계정명'@'도메인';
SHOW GRANTS FOR 'dev1'@'localhost';

# [4] 계정의 권한 취소/회수 ( REVOKE ) 
# REVOKE 회수명령어 ON 데이터베이스명.테이블명/뷰명 FROM '계정명'@'도메인';
REVOKE SELECT ON springweb2.student FROM 'dev2'@'%'; # 확인 : (dev2)계정에서 접속후 확인

# [5] 계정의 비밀번호 변경 
# ALTER USER '계정명'@'도메인' identified by '새로운비밀번호';
alter user 'dev2'@'%' identified by '1234';
# 인텔리제이/VSCODE (IDE통합개발환경 , 코드를 편하게 사용할수 있게 도와주는 소프트웨어 )
# 워크벤치/HeidiSQL ( DB를 편하게 사용할수 있게 도와주는 소프트웨어 )

# [6] 계정의 삭제 
# drop user '계정명'@'도메인';
drop user 'dev2'@'%';

# 1. 새로운 계정 생성 
create user 'dev3'@'localhost' identified by '1234';
# 2. 새로운 뷰 생성 
use springweb2;
create or replace view student_view as select * from student;
# 3. 새로운 계정에게 뷰 조회 권한 만 부여 
grant select on springweb2.student_view to 'dev3'@'localhost';
# 4. (dev) dev3 접속후 테스트
select * from student_view; -- 가능
update student_view set name ='유재석'; -- 불가능

# 데이터베이스 내 모든 계정 목록 확인 
select * from mysql.user;






































