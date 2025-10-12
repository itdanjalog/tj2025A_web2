USE springweb2;

# 💰 예제 1. 기본 트랜잭션 — 송금 성공 (COMMIT)
SET autocommit = 0;   -- 자동 커밋 해제
START TRANSACTION;
UPDATE trans SET money = money - 50000 WHERE name = '신동엽';  -- 출금
UPDATE trans SET money = money + 50000 WHERE name = '서장훈';  -- 입금
COMMIT;  -- 모든 작업 확정

# 💥 예제 2. 송금 중 오류 발생 (ROLLBACK)
START TRANSACTION;
UPDATE trans SET money = money - 100000 WHERE name = '신동엽';
UPDATE trans SET money = money + 100000 WHERE name = '없는사람';  -- ❌ 오류 발생
ROLLBACK;  -- 모든 변경 되돌림

# 💡 예제 3. SAVEPOINT 활용
START TRANSACTION;
UPDATE trans SET money = money - 30000 WHERE name = '신동엽';
SAVEPOINT pointA;
UPDATE trans SET money = money - 30000 WHERE name = '서장훈';
ROLLBACK TO pointA;
COMMIT;

# ✅ 설명
# SAVEPOINT를 설정한 시점까지만 되돌릴 수 있음
# ROLLBACK TO pointA 이후 첫 번째 출금만 유지됨


CREATE TABLE trans_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),          -- 거래 대상자
    action_type VARCHAR(20),    -- 'DEPOSIT' / 'WITHDRAW'
    change_amount INT,          -- 변동 금액
    balance_after INT,          -- 변경 후 잔액
    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

# ✅ 트리거나 프로시저 작성 시 필수
DELIMITER //
CREATE TRIGGER trg_after_update_trans
AFTER UPDATE ON trans
FOR EACH ROW
BEGIN
    DECLARE diff INT;
    SET diff = NEW.money - OLD.money;

    IF diff > 0 THEN
        INSERT INTO trans_log (name, action_type, change_amount, balance_after)
        VALUES (NEW.name, 'DEPOSIT', diff, NEW.money);
    ELSEIF diff < 0 THEN
        INSERT INTO trans_log (name, action_type, change_amount, balance_after)
        VALUES (NEW.name, 'WITHDRAW', ABS(diff), NEW.money);
    END IF;
END;
//
DELIMITER ;


START TRANSACTION;
UPDATE trans SET money = money - 30000 WHERE name = '신동엽';
UPDATE trans SET money = money + 30000 WHERE name = '서장훈';
COMMIT;

SELECT * FROM trans;
SELECT * FROM trans_log;

SHOW TRIGGERS;
DROP TRIGGER IF EXISTS trg_after_update_trans;