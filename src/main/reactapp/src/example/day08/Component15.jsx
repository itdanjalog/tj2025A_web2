import React, { useRef } from "react";
import axios from "axios";

export default function Component15() {
  const uploadFormRef = useRef(null); // 파일 업로드 form
  const loginFormRef = useRef(null);  // 로그인 form

  // 1. JSON 요청/응답
  const sendJson = async () => {
    try {
      const res = await axios.post("http://localhost:8080/api/json", {
        name: "홍길동",
        age: 30,
      });
      console.log("[1] JSON 응답:", res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 2. Multipart (파일 업로드 - form 전체 useRef)
  const handleUploadSubmit = async (e) => {
    e.preventDefault();
    try {
      const fd = new FormData(uploadFormRef.current); // formRef에서 바로 FormData 생성
      const res = await axios.post("http://localhost:8080/api/upload", fd, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      console.log("파일 업로드 응답: " ,  res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 3. Form-urlencoded (로그인 - form 전체 useRef)
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const fd = new FormData(loginFormRef.current); // form 데이터를 통째로 가져오기
      const params = new URLSearchParams(fd); // URLSearchParams로 변환

      const res = await axios.post("http://localhost:8080/api/loginForm", params, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      console.log("Form 응답: " , res.data );
    } catch (err) {
      console.error(err);
    }
  };

    // 4. Text 응답 (쿼리스트링으로 데이터 전송)
    const getText = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/text", {
          params: { msg: "Hello", user: "hong" }, // 쿼리스트링 파라미터
          responseType: "text",
        });
        console.log("Text 응답: " , res.data);
      } catch (err) {
        console.error(err);
      }
    };

  // 5. 세션 로그인 (withCredentials)
  const loginSession = async () => {
    try {
      const res = await axios.post(
        "http://localhost:8080/api/loginSession",
        { username: "hong", password: "1234" },
        { withCredentials: true }
      );
      console.log("세션 로그인 응답: " , res.data);
    } catch (err) {
      console.error(err);
    }
  };

    // 6. 로그인된 회원 확인 , axios.get("url", { withCredentials: true });
  const checkUser = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/me",
      { withCredentials: true,}
      );
      console.log("회원 확인: " , res.data);
    } catch (err) {
          if ( err.response.status === 401) {
            // 서버에서 내려준 메시지 출력
            console.log(err.response.data.message); // "로그인한 사용자가 없습니다."
          } else {
            console.log("알 수 없는 오류:", err);
          }
    }
  };

// 7. 로그아웃 , axios.post("url", data, { withCredentials: true });
  const logoutUser = async () => {
    try {
      const res = await axios.post(
        "http://localhost:8080/api/logout",
        {},
        { withCredentials: true }
      );
      console.log("로그아웃: " , res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <h2>Axios 요청/응답 예제</h2>

      <button onClick={sendJson}>[1] JSON 요청</button>

      {/* 2. 파일 업로드 Form */}
      <form ref={uploadFormRef} >
        <h4>[2] 파일 업로드 (Form + useRef)</h4>
        <input type="file" name="file" />
        <input type="text" name="desc" placeholder="파일 설명" />
        <button type="button" onClick={ handleUploadSubmit }>업로드</button>
      </form>

      {/* 3. 로그인 Form */}
      <form ref={loginFormRef} >
        <h4>[3] 로그인 Form (Form-urlencoded + useRef)</h4>
        <input type="text" name="username" placeholder="아이디" />
        <input type="password" name="password" placeholder="비밀번호" />
        <button type="button" onClick={ handleLoginSubmit }>로그인</button>
      </form>

      <button onClick={getText}>[4] Text 요청</button>
      <button onClick={loginSession}>[5] 세션 로그인</button>
      <button onClick={checkUser}>[6] 로그인된 회원 확인</button>
      <button onClick={logoutUser}>[7] 로그아웃</button>
    </div>
  );
}
