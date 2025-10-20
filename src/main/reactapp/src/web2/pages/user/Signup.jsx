import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const navigate = useNavigate();

  // ✅ 폼 상태
  const [form, setForm] = useState({
    uid: "",
    upwd: "",
    uname: "",
    uphone: "",
    urole: "USER"
  });

const [msg, setMsg] = useState('');

  // ✅ 입력 핸들러 (공통)
  const handleChange = (key) => (e) =>
    setForm({ ...form, [key]: e.target.value });

  // ✅ 회원가입 요청
  const handleSubmit = async (e) => {
    e.preventDefault();

      const res =  await axios.post("http://localhost:8080/api/user/signup", form );
      if( res.data > 0 ){

          alert("회원가입 성공!");
          navigate("/login");
      }else{
      setMsg("❌ 회원가입 실패: ");
      }

  };

  // ✅ 렌더링
  return (
    <div style={{ maxWidth: 400, margin: "0 auto" }}>
      <h2>회원가입</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <input
            placeholder="아이디 (uid)"
            value={form.uid}
            onChange={handleChange("uid")}
            required
          />
        </div>
        <div>
          <input
            placeholder="비밀번호 (upwd)"
            type="password"
            value={form.upwd}
            onChange={handleChange("upwd")}
            required
          />
        </div>
        <div>
          <input
            placeholder="이름 (uname)"
            value={form.uname}
            onChange={handleChange("uname")}
            required
          />
        </div>
        <div>
          <input
            placeholder="전화번호 (uphone)"
            value={form.uphone}
            onChange={handleChange("uphone")}
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <button type="submit">회원가입</button>
        </div>
      </form>

      {msg && (
        <div style={{ marginTop: 12, color: msg.startsWith("✅") ? "teal" : "red" }}>
          {msg}
        </div>
      )}
    </div>
  );
}
