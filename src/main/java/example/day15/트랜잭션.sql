use springweb2;
SET SQL_SAFE_UPDATES = 0;
-- =====================================================================
-- 💰 예제 1. 기본 트랜잭션 — 송금 성공 (COMMIT)
-- =====================================================================
SET autocommit = 0;   -- 자동 커밋 비활성화 (직접 COMMIT 해야 반영됨)

START TRANSACTION;  -- 트랜잭션 시작
UPDATE trans SET money = money - 50000 WHERE name = '신동엽';  -- 출금자
UPDATE trans SET money = money + 50000 WHERE name = '서장훈';  -- 입금자
COMMIT;  -- 모든 작업 정상 완료 → 변경사항 확정 (DB 반영)

SELECT * FROM trans;  -- 결과 확인


-- =====================================================================
-- 💥 예제 2. 송금 중 오류 발생 → ROLLBACK
-- =====================================================================
START TRANSACTION;
UPDATE trans SET money = money - 100000 WHERE name = '신동엽';  -- 출금
UPDATE trans SET money = money + 100000 WHERE name = '없는사람';  -- ❌ 존재하지 않는 회원 → 오류 발생
ROLLBACK;  -- 오류 감지 시 이전 상태로 되돌림 (변경사항 무효)

SELECT * FROM trans;  -- 변경되지 않음 (ROLLBACK 확인용)


-- =====================================================================
-- 💡 예제 3. SAVEPOINT 활용 — 부분 되돌리기
-- =====================================================================
START TRANSACTION;
UPDATE trans SET money = money - 30000 WHERE name = '신동엽';  -- 첫 번째 출금
SAVEPOINT pointA;  -- 🔖 저장지점 설정 (여기까지는 유지 가능)
UPDATE trans SET money = money - 30000 WHERE name = '서장훈';  -- 두 번째 출금
ROLLBACK TO pointA;  -- pointA 시점으로 되돌림 (두 번째 출금만 취소)
COMMIT;  -- 첫 번째 출금만 확정

SELECT * FROM trans;  -- 신동엽 금액만 감소한 상태 확인


-- =====================================================================
-- 🔁 예제 4. 여러 단계의 SAVEPOINT — 세부 제어
-- =====================================================================
START TRANSACTION;
UPDATE trans SET money = money - 10000 WHERE name = '유재석';  -- ① 출금
SAVEPOINT step1;
UPDATE trans SET money = money - 10000 WHERE name = '강호동';  -- ② 출금
SAVEPOINT step2;
UPDATE trans SET money = money + 20000 WHERE name = '서장훈';  -- ③ 입금
SAVEPOINT step3;

-- 🎯 여기서 일부만 되돌리기
ROLLBACK TO step2;  -- step2 이후(③) 변경만 되돌림
COMMIT;              -- ①, ②까지만 반영

SELECT * FROM trans;  -- 유재석, 강호동만 변경됨


-- =====================================================================
-- ✅ 핵심 요약
-- =====================================================================
-- 1️⃣ START TRANSACTION : 트랜잭션 시작
-- 2️⃣ COMMIT             : 정상 완료 시 변경사항 확정
-- 3️⃣ ROLLBACK           : 오류 또는 취소 시 이전 상태로 복원
-- 4️⃣ SAVEPOINT / ROLLBACK TO : 특정 지점까지만 되돌리기 가능
-- 5️⃣ SET autocommit = 0 : 수동 커밋 모드로 전환 (학습 시 추천)
-- 6️⃣ COMMIT 또는 ROLLBACK 후 자동으로 트랜잭션 종료
-- =====================================================================