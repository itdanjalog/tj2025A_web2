

import { BrowserRouter, Link, Route, Routes } from 'react-router-dom'

// [1] CSS 가져오기 
import './Task6.css'

// [3] 홈 컴포넌트 
function Home( props ){
    return (<> <h3> 홈 페이지 </h3></>)
}
// [4] 회원가입 컴포넌트
function Signup( props ){
    return (<> <h3> 회원가입 페이지 </h3> </>)
}
// [5] 로그인 컴포넌트
function Login( props ){
    return (<> <h3> 로그인 페이지 </h3> </>)
} 




// [2] 라우터로 사용할 최초 컴포넌트
export default function Task6( props ){
    return (<>
        <BrowserRouter>
            <div class="container">
                <ul>
                    <li> <Link to="/"> 홈 </Link> </li>
                    <li> <Link to="signup"> 회원가입 </Link> </li>
                    <li> <Link to="login"> 로그인 </Link> </li>
                </ul>
                <div> { /* 여기가 (라우터) 렌더링 되는곳  */}
                    <h6>여기가 (라우터)렌더링 되는곳</h6>
                    <Routes>
                        <Route path='/' element={ <Home/> } />
                        <Route path="/signup" element={ <Signup/> } />
                        <Route path="/login" element={ <Login/> } />
                    </Routes>
                </div>
            </div> 
        </BrowserRouter>
    </>)
} 


