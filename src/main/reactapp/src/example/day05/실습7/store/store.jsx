// src/store/store.js
import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice.jsx";

// 🔒 redux-persist 관련 함수 및 저장소(localStorage) 불러오기
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // 기본적으로 localStorage 사용


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



const store = configureStore({
  reducer: {
    user: persistedReducer,
  },
});

export default store;
// ==========================
// 4. persistor 생성 (App.js에서 <PersistGate>에 사용됨)
// ==========================
export const persistor = persistStore(store);
