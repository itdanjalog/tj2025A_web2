import { Navigate, Outlet } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";

// ✅ 1. 통합 인증 체크 컴포넌트
const ProtectedRoute = ({ allowedRoles }) => {
  const [auth, setAuth] = useState({ isAuthenticated: null, role: null  });

  // ==========================
  // ✅ JWT 인증 요청 함수
  // ==========================
  const checkAuth = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/auth/check", {
        withCredentials: true, // HttpOnly 쿠키 자동 포함
      });
      console.log( res.data );
      setAuth(res.data);
    } catch (err) {
      setAuth({ isAuthenticated: false });
    }
  };

  // ==========================
  // ✅ 최초 마운트 시 토큰 검증
  // ==========================
  useEffect(() => {
    checkAuth();
  }, []);

  // ==========================
  // ✅ 로딩 중 표시
  // ==========================
  if (auth.isAuthenticated === null) return <div>⏳ 인증 확인 중...</div>;

  // ==========================
  // ✅ 인증 실패 시 로그인 페이지로 이동
  // ==========================
  if (!auth.isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // ==========================
  // ✅ 권한(role) 미일치 시 접근 불가 페이지로 이동
  // ==========================
  if (allowedRoles && !allowedRoles.includes(auth.role)) {
    return <Navigate to="/forbidden" replace />;
  }

  // ==========================
  // ✅ 통과 시 자식 라우트 렌더링
  // ==========================
  return <Outlet context={{ user: auth.id, role: auth.role }} />;
};

export default ProtectedRoute;
