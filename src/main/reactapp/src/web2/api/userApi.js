import axios from "axios";

const userApi = axios.create({
  baseURL: "http://localhost:8080/users", // 백엔드 주소 (필요시 변경)
  withCredentials: true,            // 중요: 세션 쿠키 전송 허용
  headers: {
    "Content-Type": "application/json"
  }
});

export default userApi;