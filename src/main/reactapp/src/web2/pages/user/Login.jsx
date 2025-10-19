import React, { useState } from "react";
import userApi from "../../api/userApi";

export default function Login({ onLogin }) {
  const [uid, setUid] = useState("");
  const [upwd, setUpwd] = useState("");
  const [msg, setMsg] = useState("");

  const submit = async () => {
    try {
      const res = await userApi.post("/login", { uid, upwd });
      setMsg(JSON.stringify(res.data));
      if (res.data && res.data.uno) {
        onLogin && onLogin(res.data.uno);
      }
    } catch (err) {
      setMsg(err.response?.data || err.message);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <div>
        <input placeholder="아이디" value={uid} onChange={(e) => setUid(e.target.value)} />
      </div>
      <div>
        <input placeholder="비밀번호" type="password" value={upwd} onChange={(e) => setUpwd(e.target.value)} />
      </div>
      <div style={{ marginTop: 8 }}>
        <button onClick={submit}>로그인</button>
      </div>
      <div style={{ marginTop: 8, color: "teal" }}>{msg && typeof msg === "string" ? msg : JSON.stringify(msg)}</div>
    </div>
  );
}
