import { useDispatch, useSelector } from 'react-redux'
import { login, logout } from './userSlice.jsx';

export default function Component13(props){
    // [1] store 등록된 상태를 가져오기 , useSelector( (state) => state.store등록된상태명 )
    // const { 상태변수명 } = useSelector( (state)=> state.상태명 )
    const { isAuthenticated } = useSelector( (state)=> state.user );
    console.log( isAuthenticated );

    // [2] 액션 이용한 전역변수 상태 변경한다. , useDispatch();
    const dispatch = useDispatch(); 
    
    // [4] 로그인 처리
    const loginHandle = ( ) => {    // 로그인 axios 했다 가정하에
        dispatch( login() ); // dispatch 이용한 login 액션을 요청한다. dispatch( login리듀서 )
    }
    // [5] 로그아웃 처리 
    const logoutHandle = ( ) => {   // 로그아웃 axios 했다 가정하에
        dispatch( logout() ); // dispatch 이용한 logout 액션을 요청한다. dispath( logout리듀서 )
    }
    return(<>
            <h3> 리덕스 예제 </h3>
            { isAuthenticated == true 
                ? 
                <div>  
                    <p> 안녕하세요 회원님! </p>
                    <button onClick={ logoutHandle }> 로그아웃 버튼 </button> 
                </div>
                :
                <div>  
                    <p> 로그인 상태가 아닙니다. </p>
                    <button onClick={ loginHandle }> 로그인 버튼 </button> 
                </div>
            }
    </>)
}
