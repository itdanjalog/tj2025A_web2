// 📦 Redux Toolkit의 store 설정 함수 불러오기
import { configureStore } from '@reduxjs/toolkit';

// 🔒 redux-persist 관련 함수 및 저장소(localStorage) 불러오기
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // 기본적으로 localStorage 사용

// 🧩 우리가 만든 유저 리듀서 불러오기 (userSlice.js에서 export된 것)
import userReducer from './userSlice';

// ==========================
// 1. redux-persist 설정
// ==========================
const persistConfig = {
  key: 'root',   // 저장 시 사용할 key 이름 (localStorage에 저장될 이름)
  storage,       // 어떤 스토리지를 사용할지 지정 (여기서는 localStorage)
  // whitelist: ['user'], // 여러 개 리듀서 중 일부만 저장할 경우 사용
};

// ==========================
// 2. 리듀서에 persist 설정 적용
// ==========================
// userReducer를 persistReducer로 감싸서 저장 기능을 추가
const persistedReducer = persistReducer(persistConfig, userReducer);

// ==========================
// 3. 리덕스 스토어 생성
// ==========================
const store = configureStore({
  reducer: {
    // user 상태에 persist가 적용된 리듀서 연결
    user: persistedReducer,
  },
  // 미들웨어 설정은 필요 시 추가 (예: serializableCheck 비활성화 등)
});

// ==========================
// 4. persistor 생성 (App.js에서 <PersistGate>에 사용됨)
// ==========================
export const persistor = persistStore(store);

// 🧩 store 내보내기 (Provider에 사용)
export default store;
