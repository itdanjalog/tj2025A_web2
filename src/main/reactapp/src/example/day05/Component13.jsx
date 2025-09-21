// React에서 useState, useMemo 훅을 가져오기
import  { useState, useMemo } from 'react';

// 컴포넌트 시작
export default function Component13(props) {

  // [1] 입력한 숫자 관리 (useState → 상태 관리)
  const [number, setNumber] = useState(0);

  // [1-1] number의 제곱을 구하는 무거운 계산을 useMemo로 캐싱
  const squared = useMemo(() => {
    console.log('📢 제곱 계산 중...'); // 콘솔 확인용
    return number * number; // number의 제곱값 계산
  }, [number]); // number가 변경될 때만 다시 계산

  // [2] 검색어 관리 (검색창에 입력하는 값)
  const [search, setSearch] = useState('');

  // [2-1] 검색할 과일 리스트 (고정된 배열)
  const items = ['apple', 'banana', 'grape', 'orange', 'blueberry', 'avocado'];

  // [2-2] 검색어 기준으로 리스트 필터링 → useMemo로 최적화
  const filteredItems = useMemo(() => {
    console.log('🔍 필터링 실행'); // 콘솔 확인용
    // 검색어(search)를 소문자로 바꾸고 포함되는 과일만 필터링
    return items.filter(item => item.includes(search.toLowerCase()));
  }, [search]); // search 값이 바뀔 때만 실행됨

  // [3] 클릭 수 관리
  const [clicks, setClicks] = useState(0);

  // [3-1] 무거운 계산 (큰 반복문) → useMemo로 최초 1번만 실행
  const expensiveValue = useMemo(() => {
    console.log('🧠 무거운 계산 실행'); // 콘솔 확인용
    let total = 0;
    // 1억번 가까이 더하는 무거운 연산
    for (let i = 0; i < 1e7; i++) {
      total += i;
    }
    return total;
  }, []); // 빈 배열 → 컴포넌트 최초 렌더링 시 1번만 실행

  // 화면에 표시할 JSX 반환
  return (
    <>
      {/* [1] 숫자 제곱 계산 */}
      <div>
        <h3>입력한 숫자의 제곱 계산</h3>
        <input
          type="number"
          value={number}
          onChange={e => setNumber(Number(e.target.value))} // 입력값을 number로 변환해 상태 변경
        />
        <p>제곱 결과: {squared}</p>
      </div>

      {/* [2] 과일 검색 */}
      <div>
        <h3>과일 리스트 검색</h3>
        <input
          type="text"
          placeholder="검색어 입력"
          value={search}
          onChange={e => setSearch(e.target.value)} // 입력값이 바뀔 때마다 상태 업데이트
        />
        <ul>
          {filteredItems.map((fruit, idx) => (
            <li key={idx}>{fruit}</li> // 검색 결과 출력
          ))}
        </ul>
      </div>

      {/* [3] 무거운 계산과 무관한 버튼 */}
      <div>
        <h3>무거운 계산과 무관한 버튼</h3>
        <p>계산된 값: {expensiveValue}</p> {/* 최초 렌더링에서만 계산 */}
        <button onClick={() => setClicks(clicks + 1)}>
          클릭 수 증가: {clicks}
        </button>
      </div>
    </>
  );
}

/*

🎯 쉬운 정리

useState → 값 저장 + UI 업데이트용
(상태가 바뀌면 렌더링 발생)

useMemo → 계산 결과 캐싱용
(불필요한 연산을 피하고, 지정한 값이 바뀔 때만 재계산)

👉 즉, 값을 저장하고 화면에 보여주려면 useState,
계산이 무겁고 조건부로만 실행하고 싶으면 useMemo

*/