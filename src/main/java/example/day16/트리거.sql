USE springweb2;

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