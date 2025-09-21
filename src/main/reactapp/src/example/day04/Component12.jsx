// React에서 필요한 훅(hook)을 불러옵니다
import { useEffect, useRef, useState } from 'react';

// 컴포넌트 정의: 여러 가지 useRef 예제를 하나로 모은 컴포넌트입니다
export default function Component12(props) {

  // [예제 1] input 요소에 포커스 이동
  const inputRef = useRef(null); // input DOM 요소에 접근할 수 있는 참조값을 생성합니다

  // 버튼 클릭 시 input에 포커스를 주는 함수
  const handleClick1 = () => {
    inputRef.current.focus(); // inputRef가 참조하는 input 요소에 포커스 적용
  };

  // [예제 2] 렌더링 없이 값 증가 (useRef로 값 기억하기)
  const countRef = useRef(0); // 숫자를 기억할 수 있는 ref 생성 (렌더링과 무관하게 유지됨)
  const [render, setRender] = useState(false); // 리렌더링을 테스트할 때 사용할 state

  // 버튼 클릭 시 ref 값을 증가시키고 alert로 출력
  const handleClick = () => {
    countRef.current += 1; // ref로 저장된 숫자 1 증가
    alert(`클릭 수: ${countRef.current}`); // 현재까지 클릭된 수를 알림창으로 표시
  };

  // [예제 3] 이전 count 값을 기억하기
  const [count, setCount] = useState(0); // 현재 숫자를 상태로 관리
  const prevCountRef = useRef(count); // 이전 값을 저장할 ref 생성 (초기값은 현재 count)

  // count 값이 변경될 때마다 이전 값을 업데이트하는 useEffect
  useEffect(() => {
    prevCountRef.current = count; // 현재 count를 ref에 저장하여 다음 렌더링에서 이전값으로 사용
  }, [count]); // count가 변경될 때마다 실행됨

  // 화면에 출력할 내용 구성
  return (
    <>
      {/* 예제 1: input에 포커스 주기 */}
      <div>
        {/* input 요소에 ref 연결해서 DOM 제어 */}
        <input ref={inputRef} type="text" placeholder="여기에 입력하세요" />
        {/* 클릭하면 input에 포커스가 가도록 설정 */}
        <button onClick={handleClick1}>포커스 이동</button>
      </div>

      {/* 예제 2: 렌더링 없이 값 증가 */}
      <div>
        <button onClick={handleClick}>클릭</button>
        <p>렌더링과 무관하게 countRef 값만 변합니다</p>
      </div>

      {/* 예제 3: 이전 값 기억하기 */}
      <div>
        <p>현재 값: {count}</p> {/* 현재 state 값 출력 */}
        <p>이전 값: {prevCountRef.current}</p> {/* 이전 count 값 출력 */}
        {/* 버튼을 누르면 count 값이 1 증가 */}
        <button onClick={() => setCount(count + 1)}>+1</button>
      </div>
    </>
  );
}
/*

💬 비유로 이해해 보기
useState = React에게 알려주는 노트북
→ "나 이 값 바꿨어! 화면 다시 그려줘!"

useRef = 나 혼자 보는 메모지
→ "조용히 값만 기억하고 싶어. 화면 안 바꿔도 돼."

항목	useState	useRef
렌더링 발생	
✅ 발생	
❌ 발생 안 함

주로 쓰는 목적	
상태 변화 UI 반영	
DOM 접근, 값 기억

값 접근 방법	
[state, setState]	
ref.current

기억되는가?	
✅	✅

변화 감지	
자동으로 리렌더링	
수동으로만 추적 가능
*/