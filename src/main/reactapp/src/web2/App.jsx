import React, { useState } from "react";
import Signup from "./pages/user/Signup";
import Login from "./pages/user/Login";
import Profile from "./pages/user/Profile";

export default function App() {
  const [view, setView] = useState("login"); // "signup" | "login" | "profile"
  const [userUno, setUserUno] = useState(null);

  return (
    <div style={{ padding: 24, fontFamily: "Arial, sans-serif" }}>
      <h1>Auth Test (React)</h1>
      <div style={{ marginBottom: 12 }}>
        <button onClick={() => setView("signup")}>회원가입</button>{" "}
        <button onClick={() => setView("login")}>로그인</button>{" "}
        <button onClick={() => setView("profile")}>내정보</button>
      </div>

      {view === "signup" && <Signup onSigned={(uno) => { setUserUno(uno); setView("login"); }} />}
      {view === "login" && <Login onLogin={(uno) => { setUserUno(uno); setView("profile"); }} />}
      {view === "profile" && <Profile onLogout={() => { setUserUno(null); setView("login"); }} />}
    </div>
  );
}
