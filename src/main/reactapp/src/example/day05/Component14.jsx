// React에서 필요한 훅(hook) 불러오기
import React, { useState, useCallback, useMemo, useEffect } from 'react';

// 컴포넌트 시작
export default function Component14() {

  // ==========================
  // [예제 1] useMemo + useCallback
  // 자식 컴포넌트(버튼)를 메모이제이션해서 불필요한 렌더링 방지
  // ==========================
  const [count1, setCount1] = useState(0); // 카운터 상태 (숫자)
  const [text, setText] = useState('');    // 입력창 상태 (문자열)

  // count1을 1 증가시키는 함수 → useCallback으로 메모이제이션
  const handleIncrement1 = useCallback(() => {
    setCount1(prev => prev + 1); // 이전 값(prev)에 +1
  }, []); // 의존성 배열이 비어 있으므로 항상 같은 함수 유지

  // ChildButton을 useMemo로 메모이제이션 (JSX 자체를 캐싱)
  const ChildButton = useMemo(() => {
    console.log('🔁 ChildButton 렌더링됨'); // 콘솔에서 확인용
    return <button onClick={handleIncrement1}>+1 증가</button>;
  }, [handleIncrement1]); // handleIncrement1이 바뀌면 다시 생성

  // ==========================
  // [예제 2] useCallback + useEffect
  // multiplier 값에 따라 새로운 함수 생성
  // ==========================
  const [multiplier, setMultiplier] = useState(2); // 곱할 숫자 상태

  // multiplier가 바뀔 때마다 새로운 multiply 함수 생성
  const multiply = useCallback((n) => {
    return n * multiplier; // n에 multiplier를 곱한 값 반환
  }, [multiplier]); // multiplier가 바뀔 때만 새 함수로 교체

  // multiply 함수가 바뀔 때마다 실행 (디버깅/학습용)
  useEffect(() => {
    console.log('✅ multiply 함수가 새로 생성됨');
  }, [multiply]);

  // ==========================
  // [예제 3] useCallback
  // 이벤트 핸들러 최적화
  // ==========================
  const [count2, setCount2] = useState(0); // 두 번째 카운터 상태

  // count2를 1 증가시키는 함수 → useCallback으로 메모이제이션
  const handleIncrement2 = useCallback(() => {
    setCount2(prev => prev + 1); // 이전 값(prev)에 +1
  }, []); // 항상 같은 함수 유지

  // ==========================
  // 최종 UI 반환 (렌더링 부분)
  // ==========================
  return (
    <div style={{ padding: '20px', border: '1px solid gray' }}>
      {/* 전체 제목 */}
      <h2>useCallback 실습 종합 예제</h2>

      {/* ===== 예제 1 영역 ===== */}
      <section style={{ marginBottom: '30px' }}>
        <h3>예제 1: useMemo로 자식 컴포넌트 메모이제이션</h3>
        {ChildButton} {/* useMemo로 메모이제이션된 버튼 */}
        <p>카운트: {count1}</p> {/* 현재 카운트 값 */}
        {/* 입력창: 입력한 값은 text state로 저장됨 */}
        <input
          placeholder="텍스트 입력"
          value={text}
          onChange={(e) => setText(e.target.value)} // 입력이 바뀔 때마다 text 업데이트
        />
      </section>

      {/* ===== 예제 2 영역 ===== */}
      <section style={{ marginBottom: '30px' }}>
        <h3>예제 2: multiplier 값에 따라 함수 변경</h3>
        <p>5 * multiplier = {multiply(5)}</p> {/* multiply 함수로 계산 */}
        <button onClick={() => setMultiplier(multiplier + 1)}>
          multiplier 증가: {multiplier}
        </button>
      </section>

      {/* ===== 예제 3 영역 ===== */}
      <section>
        <h3>예제 3: 이벤트 핸들러 최적화</h3>
        <p>클릭 수: {count2}</p> {/* 두 번째 카운트 값 */}
        <button onClick={handleIncrement2}>+1</button> {/* 클릭하면 count2 +1 */}
      </section>
    </div>
  );
}
