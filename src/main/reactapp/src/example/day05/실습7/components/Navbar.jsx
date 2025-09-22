// src/components/Navbar.jsx
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../store/userSlice";

export default function Navbar() {
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  const dispatch = useDispatch();

  return (
    <nav style={{ padding: "10px", borderBottom: "1px solid gray" }}>
      <Link to="/">홈</Link> |{" "}
      {isAuthenticated ? (
        <>
          <Link to="/profile">프로필</Link> |{" "}
          <button onClick={() => dispatch(logout())}>로그아웃</button>
        </>
      ) : (
        <Link to="/login">로그인</Link>
      )}
    </nav>
  );
}
