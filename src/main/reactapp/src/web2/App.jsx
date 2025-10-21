import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import Header from "./components/Header";

import ProtectedRoute  from "./components/ProtectedRoute";


import Home from "./pages/Home";

// 페이지 컴포넌트
import Signup from "./pages/user/Signup";
import Login from "./pages/user/Login";
import Info from "./pages/user/Info";

import Dashboard from "./pages/admin/Dashboard";


export default function App() {
  return (
    <Router>
        <Header />
        {/* ✅ 라우터 경로별 페이지 표시 */}
        <Routes>

                   {/* ✅ 로그인은 누구나 접근 가능 */}
                   <Route path="/" element={<Home />} />
                  <Route path="/signup" element={<Signup />} />
                  <Route path="/login" element={<Login />} />

                   {/* ✅ USER와 ADMIN 모두 접근 가능 */}
                   <Route element={<ProtectedRoute allowedRoles={["USER", "ADMIN"]} />}>
                     <Route path="/user/info" element={<Info />} />
                   </Route>

                   {/* ✅ ADMIN 전용 페이지 */}
                   <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
                     <Route path="/admin/dashboard" element={<Dashboard />} />
                   </Route>

                   {/* ✅ 접근 불가 페이지 */}
                   <Route path="/forbidden" element={<h2>🚫 접근 권한이 없습니다.</h2>} />

        </Routes>
    </Router>
  );
}
