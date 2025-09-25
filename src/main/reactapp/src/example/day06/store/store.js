import { configureStore } from "@reduxjs/toolkit";
import 카트슬라이스 from './cartSlice.js'

// [1] 스토어 만들기 
const store = configureStore( {
    // [2] 내가만든 슬라이스를 등록해주기 
    reducer : { cart : 카트슬라이스 }
} ) 
// [3] 스토어 내보내기 
export default store;