// 📌 리덕스 훅 불러오기
import { useSelector, useDispatch } from "react-redux";

// 📌 리덕스 액션 함수 불러오기 (로그인, 로그아웃)
import { login, logout } from "./userSlice";

// 📦 컴포넌트 정의 시작
const Component15 = () => {
  // 🔄 dispatch: 리덕스에 액션을 보내는 함수
  const dispatch = useDispatch();

  // 🔍 useSelector: 리덕스 store에서 상태(state)를 꺼내오는 함수
  // state.user 안에 있는 userInfo, isAuthenticated 값을 꺼냄
  const { userInfo, isAuthenticated } = useSelector((state) => state.user);

  // ✅ 로그인 버튼 클릭 시 실행될 함수
  const handleLogin = () => {
    // 예시용 사용자 객체 (현재는 사용하지 않음)
    const fakeUser = { username: "itdanja", email: "it@itdanja.com" };

    // 🎯 실제 로그인 액션 보내기
    // 사용자 정보를 전달하지 않고 단순히 로그인 상태로만 변경
    // dispatch(login(fakeUser)); // ← userInfo 사용 시 활성화
    dispatch(login()); // ← 로그인 상태만 true로 변경
  };

  // 🔓 로그아웃 버튼 클릭 시 실행될 함수
  const handleLogout = () => {
    // 로그아웃 액션 호출 → 로그인 상태 false로 변경
    dispatch(logout());
  };

  // ✅ JSX 반환 (화면에 보여질 UI)
  return (
    <div>
      <h2>Component15 - Redux 로그인 예제</h2>

      {/* 로그인 되어 있다면... */}
      {isAuthenticated ? (
        <div>
          {/* 사용자 정보를 쓰지 않는 구조이므로 닉네임 생략 */}
          {/* <p>✅ 환영합니다, {userInfo.username}님!</p> */}
          <p>✅ 환영합니다, 님!</p>
          <button onClick={handleLogout}>로그아웃</button>
        </div>
      ) : (
        // 로그인 안 되어 있다면...
        <div>
          <p>🔒 로그인 상태가 아닙니다.</p>
          <button onClick={handleLogin}>로그인</button>
        </div>
      )}
    </div>
  );
};

// ✅ 컴포넌트를 외부에서 사용할 수 있도록 export
export default Component15;

/*
=======================================
📦 설치해야 할 패키지 (터미널 명령어)
=======================================

1. 리덕스 공식 툴킷 (store 만들기 쉽게 해줌)
npm install @reduxjs/toolkit

2. 리액트와 리덕스를 연결해주는 라이브러리
npm install react-redux

3. 로그인 상태 유지 시 (옵션)
npm install redux-persist
*/
