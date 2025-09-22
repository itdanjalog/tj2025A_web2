import { useState, useMemo } from 'react';

export default function Component13() {
  // 숫자 상태
  const [number, setNumber] = useState(0);

  // 입력한 숫자의 제곱 계산 (number가 바뀔 때만 계산)
  const squared = useMemo(() => number * number, [number]);

  // 검색어 상태
  const [search, setSearch] = useState('');
  const fruits = ['apple', 'banana', 'grape', 'orange', 'blueberry', 'avocado'];

  // 검색어에 맞는 과일만 필터링
  const filteredFruits = useMemo(() => {
    return fruits.filter(fruit => fruit.includes(search.toLowerCase()));
  }, [search]);

  return (
    <div style={{ padding: '20px' }}>
      {/* 숫자 입력 → 제곱 출력 */}
      <div>
        <h3>숫자 제곱 계산</h3>
        <input
          type="number"
          value={number}
          onChange={e => setNumber(Number(e.target.value))}
        />
        <p>제곱 결과: {squared}</p>
      </div>

      {/* 검색어 입력 → 과일 리스트 필터 */}
      <div style={{ marginTop: '20px' }}>
        <h3>과일 검색</h3>
        <input
          type="text"
          placeholder="검색어를 입력하세요"
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <ul>
          {filteredFruits.map((fruit, index) => (
            <li key={index}>{fruit}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
