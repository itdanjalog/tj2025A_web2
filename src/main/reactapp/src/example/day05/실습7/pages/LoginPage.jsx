import { useDispatch } from "react-redux";
import { login } from "../store/userSlice";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = () => {
    dispatch(login());
    navigate("/profile"); // 로그인 후 프로필로 이동
  };

  return (
    <div>
      <h2>🔒 로그인 페이지</h2>
      <button onClick={handleLogin}>로그인</button>
    </div>
  );
}
