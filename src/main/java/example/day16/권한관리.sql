-- 🧱 1️⃣ @Host(도메인)의 의미
--   예시	의미	허용 범위
--   'user'@'localhost'	    현재 PC(로컬) 접속만 허용	내부 접속 전용
--   'user'@'%'	            모든 외부 IP 허용	외부 접속 가능
--   'user'@'192.168.%'	    특정 네트워크 대역 허용	192.168.. 전용
--   'user'@'itdanja.com'	특정 도메인에서만 접속	itdanja.com 도메인

-- 관리자(root)로 로그인한 상태
CREATE USER 'dev1'@'localhost' IDENTIFIED BY '1234';    -- 로컬 전용
CREATE USER 'dev2'@'%' IDENTIFIED BY '1234';            -- 외부 접속 허용
CREATE USER 'dev3'@'localhost' IDENTIFIED BY 'abcd';    -- 제한된 사용자

SELECT * FROM mysql.user;


-- ⚙️ 3️⃣ 권한 부여 (GRANT)
-- 특정 데이터베이스 전체 권한 부여
GRANT ALL PRIVILEGES ON springweb2.* TO 'dev1'@'localhost';
-- 데이터베이스 단위 생성 권한
GRANT CREATE ON springweb2.* TO 'dev1'@'localhost';
-- 뷰 생성만 허용
GRANT CREATE VIEW ON springweb2.* TO 'dev1'@'localhost';
-- 특정 테이블만 조회 가능
GRANT SELECT ON springweb2.student TO 'dev2'@'%';


-- 일부 권한만 부여 (조회 + 수정)
GRANT SELECT, UPDATE ON springweb2.* TO 'dev3'@'localhost';
-- 권한 변경 즉시 적용
FLUSH PRIVILEGES;

-- 🔍 4️⃣ 권한 확인 (SHOW GRANTS)
SHOW GRANTS FOR 'dev1'@'localhost';
SHOW GRANTS FOR 'dev2'@'%';
SHOW GRANTS FOR 'dev3'@'localhost';


-- 🚫 5️⃣ 권한 회수 (REVOKE)
-- dev3의 수정 권한 회수
REVOKE UPDATE ON springweb2.* FROM 'dev3'@'localhost';

-- 🔄 6️⃣ 비밀번호 변경 / 사용자 삭제
ALTER USER 'dev2'@'%' IDENTIFIED BY 'newpass1234';
DROP USER 'dev3'@'localhost';

-- 📘 7️⃣ 시스템 테이블로 계정 전체 보기
-- 모든 사용자 목록 확인
SELECT User, Host, plugin, authentication_string FROM mysql.user;


select * from student;

-- (2) 성적 우수자 뷰 생성
CREATE OR REPLACE VIEW student_view AS
SELECT name, math FROM student WHERE math >= 80;

-- (3) VIEW 권한 설정
-- dev2 계정은 student_view만 접근 가능
GRANT SELECT ON springweb2.student_view TO 'dev2'@'%';

-- (4) 권한 테스트
-- dev2로 접속 후 실행 시 결과
SELECT * FROM springweb2.student_view;  -- ✅ 가능 (허용된 뷰)
SELECT * FROM springweb2.trans;       -- ❌ 권한 없음

update student_view set name ="유재석";

-- 계정 삭제
DROP USER 'dev1'@'localhost';
DROP USER 'dev2'@'%';
DROP USER 'dev3'@'localhost';

