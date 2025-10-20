import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

import logo from "../../assets/react.svg"; // ✅ 상대경로로 import


export default function Header() {
  const navigate = useNavigate();

  const [user, setUser] = useState(null); // 로그인된 유저정보 저장
  const [loading, setLoading] = useState(true);

  // ✅ 1. 컴포넌트 마운트 시 로그인 상태 확인
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/user/info", {
          withCredentials: true,
        });
        setUser(res.data); // { uid, role } 형태로 반환됨
      } catch (err) {
        setUser(null);
      } finally {
        setLoading(false);
      }
    };
    fetchUser();
  }, []);

  // ✅ 2. 로그아웃 기능
  const handleLogout = async () => {
    try {
      await axios.get("http://localhost:8080/api/user/logout", { withCredentials: true });
      alert("로그아웃 되었습니다.");
      // navigate("/login");
      location.href = '/login'
    } catch (err) {
      alert("로그아웃 실패: " + (err.response?.data || err.message));
    }
  };

  // ✅ 4. 렌더링
  return (
    <div style={{ padding: 10, borderBottom: "1px solid #ccc" }}>
      <nav style={{ marginBottom: 12 }}>

        <Link to="/">
          <img src= {logo} />
        </Link>

        {user ? (
          // 🔐 로그인 중
          <>
            <span style={{ marginRight: 10 }}>
              <strong>{user.uname }</strong> 님
            </span>
            <button onClick={handleLogout}>로그아웃</button>{" "}
            <Link to="/info">
              <button>내정보</button>
            </Link>
          </>
        ) : (
          // 🚪 비로그인 상태
          <>
            <Link to="/signup">
              <button>회원가입</button>
            </Link>
            <Link to="/login">
              <button>로그인</button>
            </Link>
          </>
        )}
      </nav>
    </div>
  );
}
