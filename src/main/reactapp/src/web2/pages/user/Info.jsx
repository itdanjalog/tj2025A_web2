import React, { useEffect, useState } from "react";
import axios from "axios";

export default function Info() {
  const [data, setData] = useState(null);
  const [msg, setMsg] = useState("");

  // ✅ 내정보 로드
  const loadUserInfo = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/user/info", {
        withCredentials: true, // ✅ JWT 쿠키 전송 허용
      });
      setData(res.data);
      setMsg("");
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message;
      setMsg("❌ 정보 불러오기 실패: " + errorMsg);
      setData(null);
      console.error("내정보 요청 실패:", errorMsg);
    }
  };

  const onAdminDashboard = async ()=>{
    try{
      const res = await axios.get("http://localhost:8080/api/admin/dashboard", {
        withCredentials: true, // ✅ JWT 쿠키 전송 허용
      });
      console.log( res.data );
  } catch (err) {
      console.log( err );
  }
  }

  // ✅ 컴포넌트 마운트 시 한 번만 실행
  useEffect(() => {
    loadUserInfo();
  }, []);

  // ✅ 렌더링
  return (
    <div style={{ maxWidth: 400, margin: "0 auto" }}>
      <h2>내정보</h2>

      {msg && (
        <div style={{ color: msg.startsWith("❌") ? "red" : "teal", marginBottom: 10 }}>
          {msg}
        </div>
      )}

      {data ? (
        <>
          <pre
            style={{
              background: "#f4f4f4",
              padding: 10,
              borderRadius: 6,
              textAlign: "left",
            }}
          >
            {JSON.stringify(data, null, 2)}
          </pre>
        </>
      ) : (
        <div>로그인 되어있지 않습니다.</div>
      )}

      <button onClick={ onAdminDashboard }> 관리자API 요청 </button>

    </div>
  );
}
