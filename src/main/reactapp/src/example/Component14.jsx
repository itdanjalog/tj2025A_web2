import React, { useState, useCallback, useMemo } from 'react';

export default function Component14Simple() {
  // [1] 숫자 증가 버튼 (useCallback 사용)
  const [count1, setCount1] = useState(0);
  const handleClick = useCallback(() => {
    setCount1(prev => prev + 1);
  }, []);

  // [2] 곱셈 계산기 (useCallback + useMemo)
  const [multiplier, setMultiplier] = useState(2);
  const multiply = useCallback((n) => n * multiplier, [multiplier]);
  const result = useMemo(() => multiply(5), [multiply]);

  // [3] 입력값 처리 (useCallback)
  const [input, setInput] = useState('');
  const handleChange = useCallback((e) => {
    setInput(e.target.value);
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      {/* [1] 숫자 증가 버튼 */}
      <section style={{ marginBottom: '20px' }}>
        <h3>숫자 증가 예제 (useCallback)</h3>
        <button onClick={handleClick}>+1 증가</button>
        <p>카운트: {count1}</p>
      </section>

      {/* [2] 곱셈 예제 */}
      <section style={{ marginBottom: '20px' }}>
        <h3>곱셈 계산기 (useCallback + useMemo)</h3>
        <p>5 x {multiplier} = {result}</p>
        <button onClick={() => setMultiplier(multiplier + 1)}>
          multiplier +1
        </button>
      </section>

      {/* [3] 입력창 예제 */}
      <section>
        <h3>입력값 처리 (useCallback)</h3>
        <input
          type="text"
          value={input}
          onChange={handleChange}
          placeholder="텍스트 입력"
        />
        <p>입력한 값: {input}</p>
      </section>
    </div>
  );
}
