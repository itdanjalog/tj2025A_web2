import React, { useEffect, useState } from "react";
import userApi from "../../api/userApi";

export default function Profile({ onLogout }) {
  const [data, setData] = useState(null);
  const [msg, setMsg] = useState("");

  const load = async () => {
    try {
      const res = await userApi.get("/me");
      setData(res.data);
      setMsg("");
    } catch (err) {
      setMsg(err.response?.data?.message || err.message);
      setData(null);
    }
  };

  useEffect(() => { load(); }, []);

  const logout = async () => {
    try {
      await api.get("/user/logout"); // withCredentials가 true이므로 세션 쿠키 전송
      onLogout && onLogout();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <h2>내정보</h2>
      {msg && <div style={{ color: "red" }}>{msg}</div>}
      {data ? <pre>{JSON.stringify(data, null, 2)}</pre> : <div>로그인 되어있지 않음</div>}
      <div style={{ marginTop: 8 }}>
        <button onClick={load}>새로고침</button>{" "}
        <button onClick={logout}>로그아웃</button>
      </div>
    </div>
  );
}
