import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

export default function ProfilePage() {
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);

  // 로그인이 안 되어 있으면 로그인 페이지로 리다이렉트
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <h2>👤 프로필 페이지 (로그인 성공)</h2>;
}
