import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function Header( props ){
    // 1. 로그인된 유저 정보 저장 
    const [ user , setUser ] = useState( null ); 
    // 2. 최초로 컴포넌트 실행시 유저 정보 요청하기
    const getMyInfo = async()=>{
        try{
            const url = "http://localhost:8080/api/user/info"
            const res = await axios.get( url , { withCredentials : true } );
            setUser( res.data ); // 반환된 유저 정보를 저장 
        }catch( err ){ setUser(null); } // 오류시 null 
    }
    useEffect( () => { getMyInfo(); } , [] );
    // 3. 로그아웃 요청하기 
    const getLogout = async()=>{
        try{
            const url = 'http://localhost:8080/api/user/logout'
            const res = await axios.get( url , { withCredentials : true } );
            alert('로그아웃 되었습니다.');
            // navigate("/login"); // 라우터( 클라이언트 사이드 렌더링 )
            location.href="/login" // ( 서버 사이드 렌더링 )
        }catch( err ){ }
    }

    return(<>
        <div>
            <nav>
                { user ? <> 로그인 </> : <> 비로그인 </>}
                { user ? 
                    <>  
                        { /* 로그인 상태 */}
                        <span to="/login"> 유재석 님 </span>
                        <button onClick={ getLogout } > 로그아웃 </button>
                        <Link to="/user/info"> 마이페이지 </Link>
                        { /* 로그인 상태 이면서 관리자 이면 */}
                        { user.urole == "ADMIN" ? 
                            <>
                                <Link to="/admin/dashboard"> 관리자페이지 </Link>
                            </> : <></>}
                    </> : 
                    <>
                        { /* 비로그인 상태  */}
                        <Link to="/"> 홈 </Link>
                        <Link to="/login"> 로그인 </Link>
                        <Link to="/signup"> 회원가입 </Link>
                    </>}
            </nav>
        </div>
    </>);
}