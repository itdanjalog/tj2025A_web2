import React, { useState, useRef } from 'react';
import { BrowserRouter, Routes, Route, Link, useNavigate } from 'react-router-dom';
// import axios from 'axios'; // 실제 서버 연동 시 사용 예정

// [1] 회원가입 페이지
function SignUpPage() {
  const idRef = useRef(null);
  const pwRef = useRef(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleSignUp = async () => {
    const id = idRef.current.value;
    const pw = pwRef.current.value;

    if (!id || !pw) {
      setMessage('아이디와 비밀번호를 입력하세요.');
      return;
    }

    try {
      // [추후 axios 코드 예시]
      // const res = await axios.post('/api/signup', { id, pw });
      // if (res.data.success) {
      //   setMessage('회원가입 성공!');
      //   navigate('/login');
      // }

      // 지금은 서버가 없으니 단순히 성공 메시지
      setMessage('회원가입 성공! 로그인 페이지로 이동합니다.');
      setTimeout(() => navigate('/login'), 1500);
    } catch (err) {
      setMessage('회원가입 실패: 서버 오류');
    }
  };

  return (
    <div>
      <h3>회원가입 페이지</h3>
      <input ref={idRef} type="text" placeholder="아이디" /> <br />
      <input ref={pwRef} type="password" placeholder="비밀번호" /> <br />
      <button onClick={handleSignUp}>회원가입</button>
      <p>{message}</p>
    </div>
  );
}

// [2] 로그인 페이지
function LoginPage() {
  const idRef = useRef(null);
  const pwRef = useRef(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    const id = idRef.current.value;
    const pw = pwRef.current.value;

    try {
      // [추후 axios 코드 예시]
      // const res = await axios.post('/api/login', { id, pw });
      // if (res.data.success) {
      //   setMessage(`✅ ${id}님, 환영합니다!`);
      //   navigate('/welcome');
      // } else {
      //   setMessage('❌ 로그인 실패: 아이디/비밀번호 확인');
      // }

      // 지금은 서버가 없으니 단순히 성공/실패 분기
      if (id === 'test' && pw === '1234') {
        setMessage(`✅ ${id}님, 환영합니다!`);
        setTimeout(() => navigate('/welcome'), 1000);
      } else {
        setMessage('❌ 로그인 실패 (임시 로직)');
      }
    } catch (err) {
      setMessage('로그인 실패: 서버 오류');
    }
  };

  return (
    <div>
      <h3>로그인 페이지</h3>
      <input ref={idRef} type="text" placeholder="아이디" /> <br />
      <input ref={pwRef} type="password" placeholder="비밀번호" /> <br />
      <button onClick={handleLogin}>로그인</button>
      <p>{message}</p>
    </div>
  );
}

// [3] 로그인 성공 후 Welcome 페이지
function WelcomePage() {
  return (
    <div>
      <h3>🎉 환영합니다!</h3>
      <p>로그인에 성공하셨습니다.</p>
      <Link to="/login">로그아웃</Link>
    </div>
  );
}

// [4] 홈 페이지
function HomePage() {
  return (
    <div>
      <h3>홈 페이지</h3>
      <p>좌측 메뉴에서 회원가입 또는 로그인으로 이동해보세요.</p>
    </div>
  );
}

// [5] Task6 메인 라우터 컴포넌트
export default function Task6() {
  return (
    <BrowserRouter>
      <div style={{ display: 'flex', gap: '20px' }}>
        {/* 네비게이션 메뉴 */}
        <nav>
          <ul>
            <li><Link to="/">홈</Link></li>
            <li><Link to="/signup">회원가입</Link></li>
            <li><Link to="/login">로그인</Link></li>
          </ul>
        </nav>

        {/* 라우트 영역 */}
        <div style={{ borderLeft: '1px solid gray', paddingLeft: '20px' }}>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/welcome" element={<WelcomePage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}
/*
📝 Task6 실습 요구사항
🎯 목표
React의 라우팅(react-router-dom) 과 훅(useRef, useState, useEffect) 을 동시에 활용한다.
회원가입 / 로그인 / 환영 페이지를 구현한다.
추후 서버와 axios로 통신한다고 가정한다.

✅ 기본 요구사항
라우터 구조 만들기
/ → 홈 페이지
/signup → 회원가입 페이지
/login → 로그인 페이지
/welcome → 로그인 성공 후 이동하는 페이지
좌측에 메뉴(Link)를 만들어 각 페이지로 이동 가능하게 할 것

✅ 회원가입(SignUp) 페이지 요구사항
아이디 입력창 / 비밀번호 입력창 / 회원가입 버튼 존재
입력값은 useRef를 활용해 가져온다.
아이디나 비밀번호가 비어있으면 경고 메시지를 표시한다.
회원가입 성공 시:
"회원가입 성공!" 메시지를 보여준다.
잠시 후 로그인 페이지(/login)로 이동한다.
실제 로직은 추후 axios.post('/api/signup')으로 서버에 요청한다고 가정한다.

✅ 로그인(Login) 페이지 요구사항
아이디 입력창 / 비밀번호 입력창 / 로그인 버튼 존재
입력값은 useRef를 활용해 가져온다.
로그인 성공 시:
"환영합니다, {아이디}님" 메시지를 보여준다.
잠시 후 /welcome 페이지로 이동한다.
로그인 실패 시:
"아이디 또는 비밀번호가 올바르지 않습니다." 메시지를 보여준다.
실제 로직은 추후 axios.post('/api/login')으로 서버에 요청한다고 가정한다.

✅ 환영(Welcome) 페이지 요구사항
"환영합니다!" 메시지를 표시한다.
"로그아웃" 버튼 또는 링크를 제공하여 /login으로 이동할 수 있게 한다.

✅ 추가 요구사항 (심화)
useEffect + useRef를 활용해 로그인 페이지에서 이전 로그인 시도한 아이디를 기억하고 표시한다.
useState를 활용해 메시지(성공/실패)를 화면에 표시한다.
모든 페이지에서 공통으로 사용할 수 있는 네비게이션 메뉴를 만들고, Link로 라우팅 이동을 처리한다.

📌 실습 단계 제안
Step 1: 라우터 기본 구조 만들기 (/, /signup, /login, /welcome)
Step 2: 회원가입 페이지 구현 (useRef로 입력값 받기)
Step 3: 로그인 페이지 구현 (useRef로 입력값 검증 + useState로 메시지 표시)
Step 4: 로그인 성공 시 /welcome 페이지로 이동
Step 5 (심화): useEffect로 이전 로그인 아이디 기억하기

*/