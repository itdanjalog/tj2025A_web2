/* ============================================================
 [ springweb2 : VIEW(뷰) 실습 종합 예제 ]
============================================================ */

USE springweb2;

-- 1.기존 테이블 초기화
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS member;

-- 2.회원(member) 테이블 생성
CREATE TABLE member (
    mno INT AUTO_INCREMENT PRIMARY KEY,   -- 회원번호 (PK)
    name VARCHAR(50),                     -- 이름
    grade VARCHAR(20)                     -- 등급 (VIP, GOLD, SILVER)
);

-- 3. 주문(orders) 테이블 생성
CREATE TABLE orders (
    ono INT AUTO_INCREMENT PRIMARY KEY,   -- 주문번호 (PK)
    mno INT,                              -- 회원번호 (FK)
    product VARCHAR(50),                  -- 상품명
    price INT,                            -- 가격
    order_date DATE,                      -- 주문일자
    FOREIGN KEY (mno) REFERENCES member(mno)
);

-- 4. 샘플 데이터 삽입
INSERT INTO member (name, grade)
VALUES ('유재석', 'VIP'), ('강호동', 'GOLD'), ('신동엽', 'SILVER');

INSERT INTO orders (mno, product, price, order_date)
VALUES
(1, '노트북', 1500000, '2025-10-10'),
(1, '마우스', 30000, '2025-10-11'),
(2, '키보드', 50000, '2025-10-11'),
(3, '모니터', 200000, '2025-10-12');


/* ============================================================
 🧩 예제 0. 기본 뷰 생성
============================================================ */
-- 뷰 생성
CREATE OR REPLACE VIEW 뷰이름 AS
SELECT product,price FROM orders;
select * from 뷰이름;
-- 뷰 목록확인
SHOW FULL TABLES WHERE TABLE_TYPE='VIEW';
-- 뷰 수정
ALTER VIEW 뷰이름 AS
SELECT product,price,order_date FROM orders;
select * from 뷰이름;
-- 뷰 삭제
DROP VIEW IF EXISTS 뷰이름;

/* ============================================================
 💎 예제 1. 조건 기반 뷰
============================================================ */
CREATE OR REPLACE VIEW view_vip_member AS
SELECT * FROM member WHERE grade = 'VIP';

SELECT * FROM view_vip_member;

-- ✅ 특정 조건(VIP 회원)만 필터링한 뷰
-- ✅ 보안·조회제한용으로 자주 사용


/* ============================================================
 ✏️ 예제 4. 뷰 수정 및 삭제
============================================================ */
-- 뷰 수정 (VIP + GOLD)
ALTER VIEW view_vip_member AS
SELECT * FROM member WHERE grade IN ('VIP', 'GOLD');

-- 뷰 삭제
DROP VIEW IF EXISTS view_member_total;
SELECT * FROM view_member_total ORDER BY total_price DESC;


/* ============================================================
 📋 예제 5. 현재 존재하는 뷰 목록 확인
============================================================ */
SHOW FULL TABLES WHERE TABLE_TYPE LIKE 'VIEW';




/* ============================================================
 🧩 예제 2. 기본 뷰 생성 (JOIN)
============================================================ */
CREATE OR REPLACE VIEW view_order_summary AS
SELECT o.ono, m.name, m.grade, o.product, o.price, o.order_date
FROM orders o
JOIN member m ON o.mno = m.mno;

SELECT * FROM view_order_summary;

-- ✅ JOIN 결과를 재사용 가능한 뷰로 정의
-- ✅ SQL을 간결화하고 유지보수 용이


/* ============================================================
 🧮 예제 3. 집계 뷰 (GROUP BY)
============================================================ */
CREATE OR REPLACE VIEW view_member_total AS
SELECT m.mno, m.name, SUM(o.price) AS total_price
FROM member m
JOIN orders o ON m.mno = o.mno
GROUP BY m.mno, m.name;

SELECT * FROM view_member_total ORDER BY total_price DESC;

-- ✅ SUM + GROUP BY를 이용한 통계형 뷰
-- ✅ 회원별 총 구매 금액 확인







/* ============================================================
 ⚙️ 예제 6. 다중 JOIN + 필터링 뷰
============================================================ */
CREATE OR REPLACE VIEW view_high_value_orders AS
SELECT m.name, m.grade, o.product, o.price, o.order_date
FROM member m
JOIN orders o ON m.mno = o.mno
WHERE o.price >= 100000;

SELECT * FROM view_high_value_orders;

-- ✅ 고가 주문(10만원 이상)만 포함하는 뷰


/* ============================================================
 🧮 예제 7. 서브쿼리 기반 뷰 (Top Spender)
============================================================ */
CREATE OR REPLACE VIEW view_top_spender AS
SELECT m.name, m.grade, SUM(o.price) AS total_spent
FROM member m
JOIN orders o ON m.mno = o.mno
GROUP BY m.mno, m.name, m.grade
HAVING SUM(o.price) = (
    SELECT MAX(total_price) FROM (
        SELECT SUM(price) AS total_price
        FROM orders
        GROUP BY mno
    ) AS sub
);

SELECT * FROM view_top_spender;

-- ✅ 서브쿼리 + HAVING을 이용해 가장 많이 구매한 회원 자동 추출


/* ============================================================
 📅 예제 8. 날짜 조건 + 정렬 포함 뷰
============================================================ */
CREATE OR REPLACE VIEW view_recent_orders AS
SELECT o.ono, m.name, o.product, o.price, o.order_date
FROM orders o
JOIN member m ON o.mno = m.mno
WHERE o.order_date >= DATE_SUB(CURDATE(), INTERVAL 3 DAY)
ORDER BY o.order_date DESC;

SELECT * FROM view_recent_orders;

-- ✅ 최근 3일간 주문 내역을 실시간으로 조회


/* ============================================================
 📊 예제 9. VIEW + JOIN + 서브쿼리 통합 리포트
============================================================ */
CREATE OR REPLACE VIEW view_member_order_summary AS
SELECT
    m.name,
    m.grade,
    COUNT(o.ono) AS order_count,
    SUM(o.price) AS total_spent,
    (SELECT AVG(price) FROM orders WHERE mno = m.mno) AS avg_spent
FROM member m
LEFT JOIN orders o ON m.mno = o.mno
GROUP BY m.mno, m.name, m.grade
ORDER BY total_spent DESC;

SELECT * FROM view_member_order_summary;

-- ✅ LEFT JOIN + 서브쿼리 + 집계 조합
-- ✅ 회원별 주문 횟수, 총금액, 평균금액 리포트


/* ============================================================
 📘 VIEW 핵심 정리
============================================================
CREATE [OR REPLACE] VIEW 뷰명 AS SELECT문        -- 뷰 생성
SELECT * FROM 뷰명                               -- 뷰 조회
ALTER VIEW 뷰명 AS SELECT문                      -- 뷰 수정
DROP VIEW IF EXISTS 뷰명                         -- 뷰 삭제
SHOW FULL TABLES WHERE TABLE_TYPE='VIEW'         -- 뷰 목록확인

장점 : SQL 간결화, 재사용, 보안 강화
단점 : 너무 복잡한 뷰는 성능 저하 가능
============================================================ */
