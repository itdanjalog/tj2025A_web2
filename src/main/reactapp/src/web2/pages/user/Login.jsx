import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  // ✅ 입력 상태
  const [uid, setUid] = useState("");
  const [upwd, setUpwd] = useState("");
  // ✅ 로그인 요청
  const handleSubmit = async (e) => {
    e.preventDefault();

      // 백엔드로 로그인 요청
      const res = await axios.post(
        "http://localhost:8080/api/user/login",
        { uid, upwd },
        {
          withCredentials: true, // ✅ 쿠키 전송 허용
        }
      );

        console.log( res.data );
        if( res.data != '' ){
              alert("로그인 성공!");
              //navigate("/user/info"); // 로그인 후 프로필 페이지로 이동
             location.href = '/user/info'
        }else{
              alert("로그인 실패!");
        }
  };

  // ✅ 렌더링
  return (
    <div style={{ maxWidth: 400, margin: "0 auto" }}>
      <h2>로그인</h2>

      <form >
        <div>
          <input
            placeholder="아이디"
            value={uid}
            onChange={(e) => setUid(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            placeholder="비밀번호"
            type="password"
            value={upwd}
            onChange={(e) => setUpwd(e.target.value)}
            required
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <button type="button" onClick={ handleSubmit }>로그인</button>
          <a href="http://localhost:8080/oauth2/authorization/google">
            구글 로그인
          </a>
                    <a href="http://localhost:8080/oauth2/authorization/kakao">
                      카카오 로그인
                    </a>
        </div>
      </form>

    </div>
  );
}
