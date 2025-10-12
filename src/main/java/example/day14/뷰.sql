USE springweb2;


-- 회원 테이블
CREATE TABLE member (
    mno INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    grade VARCHAR(20)
);
-- 주문 테이블
CREATE TABLE orders (
    ono INT AUTO_INCREMENT PRIMARY KEY,
    mno INT,
    product VARCHAR(50),
    price INT,
    order_date DATE,
    FOREIGN KEY (mno) REFERENCES member(mno)
);
INSERT INTO member (name, grade) VALUES('유재석', 'VIP'),('강호동', 'GOLD'),('신동엽', 'SILVER');

INSERT INTO orders (mno, product, price, order_date) VALUES
(1, '노트북', 1500000, '2025-10-10'),(1, '마우스', 30000, '2025-10-11'),
(2, '키보드', 50000, '2025-10-11'),(3, '모니터', 200000, '2025-10-12');

# 💡 예제 1. 기본 뷰 생성
# 회원 이름과 주문 정보를 합친 단순 뷰

CREATE OR REPLACE VIEW view_order_summary AS
SELECT o.ono, m.name, m.grade, o.product, o.price, o.order_date
FROM orders o
JOIN member m ON o.mno = m.mno;

SELECT * FROM view_order_summary;

# ✅ 특징
# JOIN 결과를 재사용 가능
# SQL 간결화 및 재활용성 향상

# 💡 예제 2. 집계 뷰
# 회원별 총 구매금액 요약 뷰

CREATE OR REPLACE VIEW view_member_total AS
SELECT m.mno, m.name, SUM(o.price) AS total_price
FROM member m
JOIN orders o ON m.mno = o.mno
GROUP BY m.mno, m.name;

SELECT * FROM view_member_total ORDER BY total_price DESC;

# ✅ 특징
# SUM, GROUP BY로 통계성 뷰 생성
# 뷰 기반으로 바로 SELECT 가능



# 💡 예제 3. 뷰를 이용한 조건 필터링
# VIP 등급 회원만 조회하는 뷰
CREATE OR REPLACE VIEW view_vip_member AS
SELECT * FROM member WHERE grade = 'VIP';

SELECT * FROM view_vip_member;

# 💡 예제 4. 뷰 수정 및 삭제
-- 뷰 변경
ALTER VIEW view_vip_member AS
SELECT * FROM member WHERE grade IN ('VIP', 'GOLD');
-- 뷰 삭제
DROP VIEW IF EXISTS view_member_total;

SHOW FULL TABLES WHERE TABLE_TYPE LIKE 'VIEW';
SHOW TABLES;
SHOW FULL TABLES;



