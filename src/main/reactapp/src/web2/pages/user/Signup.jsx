import React, { useState } from "react";
import userApi from "../../api/userApi";

export default function Signup({ onSigned }) {
  const [form, setForm] = useState({
    uid: "",
    upwd: "",
    uname: "",
    uphone: "",
    urole: "USER"
  });
  const [msg, setMsg] = useState("");

  const change = (k) => (e) => setForm({ ...form, [k]: e.target.value });

  const submit = async () => {
    try {
      const res = await userApi.post("/signup", form);
      // 응답에서 uno 확인
      const data = res.data;
      setMsg(JSON.stringify(data));
      if (data && data.uno) {
        onSigned && onSigned(data.uno);
      }
    } catch (err) {
      setMsg(err.response?.data || err.message);
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <div>
        <input placeholder="아이디(uid)" value={form.uid} onChange={change("uid")} />
      </div>
      <div>
        <input placeholder="비밀번호(upwd)" type="password" value={form.upwd} onChange={change("upwd")} />
      </div>
      <div>
        <input placeholder="이름(uname)" value={form.uname} onChange={change("uname")} />
      </div>
      <div>
        <input placeholder="전화번호(uphone)" value={form.uphone} onChange={change("uphone")} />
      </div>
      <div style={{ marginTop: 8 }}>
        <button onClick={submit}>회원가입</button>
      </div>
      <div style={{ marginTop: 8, color: "teal" }}>{msg && typeof msg === "string" ? msg : JSON.stringify(msg)}</div>
    </div>
  );
}
