import React, { useRef } from "react";
import axios from "axios";

export default function Component15() {
  const uploadFormRef = useRef(null); // 파일 업로드 form
  const loginFormRef = useRef(null);  // 로그인 form

  // 1. JSON 요청/응답
  const axios1 = async () => {
    try {
    const obj = {  name: "홍길동",  age: 30, }
      const res = await axios.post("http://localhost:8080/api/json", obj );
      console.log("[1] JSON 응답:", res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 2. Multipart (파일 업로드 - form 전체 useRef)
  const axios2 = async (e) => {
    e.preventDefault();
    try {
      const fd = new FormData(uploadFormRef.current); // formRef에서 바로 FormData 생성
      const option = { headers: { "Content-Type": "multipart/form-data" }  }

      const res = await axios.post("http://localhost:8080/api/upload", fd, option );
      console.log("파일 업로드 응답: " ,  res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 3. Form-urlencoded (로그인 - form 전체 useRef)
  const axios3 = async (e) => {
    e.preventDefault();
    try {
      const fd = loginFormRef.current; // form 데이터를 통째로 가져오기
        const option = { headers: { "Content-Type": "application/x-www-form-urlencoded"}  }

      const res = await axios.post("http://localhost:8080/api/loginForm", fd, option );

      console.log("Form 응답: " , res.data );
    } catch (err) {
      console.error(err);
    }
  };

    // 4. Text 응답 (쿼리스트링으로 데이터 전송)
    const axios4 = async () => {
      try {
       const obj = {
                params: { msg: "Hello", user: "hong" }, // 쿼리스트링 파라미터
                 responseType: "text",
               }
        const res = await axios.get("http://localhost:8080/api/text", obj );
        console.log("Text 응답: " , res.data);
      } catch (err) {
        console.error(err);
      }
    };

  // 5. 세션 로그인 (withCredentials) // withCredentials는 서버에서 받은 로그인 쿠키를 다시 보내서, 로그인 상태를 유지하기 위해 꼭 필요한 옵션이에요. <세션 인증 유지용>
  const axios5 = async () => {
    try {
        const  obj = { username: "hong", password: "1234" }
        const option = { withCredentials: true }

      const res = await axios.post(
        "http://localhost:8080/api/loginSession", obj , option

      );
      console.log("세션 로그인 응답: " , res.data);
    } catch (err) {
      console.error(err);
    }
  };

    // 6. 로그인된 회원 확인 , axios.get("url", { withCredentials: true });
  const axios6 = async () => {
    try {
        const option = { withCredentials: true }
      const res = await axios.get("http://localhost:8080/api/me", option );
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
  const axios7 = async () => {
    try {
    const option = { withCredentials: true }
      const res = await axios.get(
        "http://localhost:8080/api/logout", option
      );
      console.log("로그아웃: " , res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <h2>Axios 요청/응답 예제</h2>

      <button onClick={axios1}>[1] JSON 요청</button>

      {/* 2. 파일 업로드 Form */}
      <form ref={uploadFormRef} >
        <h4>[2] 파일 업로드 (Form + useRef)</h4>
        <input type="file" name="file" />
        <input type="text" name="desc" placeholder="파일 설명" />
        <button type="button" onClick={ axios2 }>업로드</button>
      </form>

      {/* 3. 로그인 Form */}
      <form ref={loginFormRef} >
        <h4>[3] 로그인 Form (Form-urlencoded + useRef)</h4>
        <input type="text" name="username" placeholder="아이디" />
        <input type="password" name="password" placeholder="비밀번호" />
        <button type="button" onClick={ axios3 }>로그인</button>
      </form>

      <button onClick={axios4}>[4] Text 요청</button>
      <button onClick={axios5}>[5] 세션 로그인</button>
      <button onClick={axios6}>[6] 로그인된 회원 확인</button>
      <button onClick={axios7}>[7] 로그아웃</button>
    </div>
  );
}
