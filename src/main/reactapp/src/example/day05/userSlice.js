// ✅ Redux Toolkit에서 slice를 생성하는 함수 import
import { createSlice } from "@reduxjs/toolkit";

// ✅ 초기 상태값 정의 (Redux 상태 초기값)
const initialState = {
  // userInfo: null, // 사용자 정보 (예: 이메일, 이름 등) → 현재 주석처리
  isAuthenticated: false, // 로그인 여부를 나타내는 상태 (true: 로그인됨, false: 로그아웃됨)
};

// ✅ createSlice를 사용해서 "user"라는 이름의 slice 생성
const userSlice = createSlice({
  name: "user", // slice의 이름 (Redux DevTools 등에서 표시됨)
  initialState, // 초기 상태값 연결

  // ✅ 상태를 변경하는 리듀서 함수들 정의 (login, logout)
  reducers: {
    // 📌 로그인 액션
    login: (state, action) => {
      state.isAuthenticated = true; // 로그인 상태를 true로 변경
      //state.userInfo = action.payload; // 사용자 정보 저장 (주석 처리됨)
    },

    // 📌 로그아웃 액션
    logout: (state) => {
      state.isAuthenticated = false; // 로그인 상태를 false로 변경
      //state.userInfo = null; // 사용자 정보 제거 (주석 처리됨)
    },
  },
});

// ✅ 액션 함수들(login, logout)과 리듀서 내보내기

// login(), logout()을 다른 파일에서 import 해서 사용할 수 있게 export
export const { login, logout } = userSlice.actions;

// 이 slice의 리듀서를 store에서 사용할 수 있도록 export
export default userSlice.reducer;
