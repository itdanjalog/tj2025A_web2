import { useState } from "react";

export default function Login(){

    // 1. 입력받은 아이디/패스워드 상태
    const [ uid , setUid ] = useState("");
    const [ upwd , setUpwd ] = useState("");

    // 2. 로그인 요청
    const postLogin = async ( )=>{
        try{
            const url = "http://localhost:8080/api/user/login";
            const obj = { uid , upwd }
            const res = await axios.post( url , obj , { withCredentials : true } );
            if( res.data != '' ){
                alert('로그인 성공');
                location.href = '/'; // location.href 이용한 페이지 전체 렌더링
            }else{ alert('로그인 실패' ); }
        }catch( err ){}
    }

    return (<>
        <h3> 로그인 페이지 </h3>
        <form>
            아이디 : <input value={ uid } onChange={ (e) => setUid( e.target.value ) } /> <br/>
            비밀번호 : 
            <input type="password" value={ upwd } onChange={ (e) => setUpwd( e.target.value ) } /> <br/>
            <button type="button" onClick={ postLogin }> 로그인 </button>
            <a href="http://localhost:8080/oauth2/authorization/google"> 구글 로그인 </a>
            <a href="http://localhost:8080/oauth2/authorization/kakao"> 카카오 로그인 </a>
        </form>
    </>);
}