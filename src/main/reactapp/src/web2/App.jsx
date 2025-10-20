import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import Header from "./components/Header";
import Home from "./pages/Home";

// 페이지 컴포넌트
import Signup from "./pages/user/Signup";
import Login from "./pages/user/Login";
import Info from "./pages/user/Info";

export default function App() {
  return (
    <Router>
        <Header />
        {/* ✅ 라우터 경로별 페이지 표시 */}
        <Routes>
            <Route path="/" element={<Home />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/info" element={<Info />} />
          {/* 기본 페이지 리다이렉트 */}
        </Routes>
    </Router>
  );
}
