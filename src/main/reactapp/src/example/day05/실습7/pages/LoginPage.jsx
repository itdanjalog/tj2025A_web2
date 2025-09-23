import { useDispatch } from 'react-redux'
import { login } from '../store/userSlice.jsx'
import { useNavigate } from 'react-router-dom'
export default function LoginPage( props ){
    // [1] 액션(상태변경) 하기 위한 dispatch 함수 가져오기 
    const dispatch = useDispatch();
    // [2] 가상 URL 로 페이지 전환하기 위한 navigate 함수 가져오기 
    const navigate = useNavigate();
    //1. 로그인 처리 함수 정의 : axios 생략
    const onLogin = async ()=>{
        alert('[로그인성공]');  // 안내 
        dispatch( login() );   // [1-2] login 액션 요청 하여 상태 변경한다.(전역변수 상태변경)
        navigate( "/" );       // [2-2] 라우터에 등록된 "/" 메인페이지 URL로 이동( 페이지 전환 )
    }
    return(<>
        <h3> 로그인페이지 </h3>
        <button onClick={ onLogin } > 로그인버튼 </button>
    </>)
}