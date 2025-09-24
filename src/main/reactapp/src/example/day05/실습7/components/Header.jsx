import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux'
import { logout } from '../store/userSlice.jsx'

export default function Header( props ){
    // [1] store 저장된 상태를 가져오기 , useSelector( (state)=>state.상태명 )
    // const { isAuthenticated } = useSelector( (state)=> state.user );        console.log( isAuthenticated );
    const { isAuthenticated , userInfo } = useSelector( ( state )=> state.user );

    // [2] dispatch 이용한 상태 변경 하기 
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const onLogout = async()=>{
        alert('[로그아웃 성공]');
        dispatch( logout() ); // 상태가 변경되면 **리렌더링** 된다.
        navigate('/login')
    }

    // 서버에게 로그인 상태 요청 useEffect( ()=>{} , [] ) 

    return(<>
        <h3> 헤더 </h3>
        <ul>
            <li> <Link to ="/"> 홈 </Link></li>
            { isAuthenticated==true ? 
                <> 
                    <li> <span> 안녕하세요. { userInfo.name } 님 </span></li>
                    <li> <Link to ="/profile"> 프로필 </Link></li>
                    <li> <button onClick={ onLogout }> 로그아웃 </button></li>
                </> 
                : 
                <>  <li> <Link to ="/login"> 로그인 </Link></li> </> 
            }
        </ul>
    </>)
}