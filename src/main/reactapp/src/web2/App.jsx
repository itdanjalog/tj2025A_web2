import { BrowserRouter as Router , Routes , Route , Link } from 'react-router-dom'
import RoleRoute from './components/RoleRoute';
import Header from './components/Header';
import Login from './pages/user/Login';

export default function App() {
    return(
        <Router>
            <Header />
            <Routes> 
                { /* 권한에 따른 조건 */}
                { /* 1. 누구나 접근 가능 */}
                <Route path='/' element={ <h1> 메인페이지 </h1> } />
                <Route path='/signup' element={ <h1> 회원가입 </h1> } />
                <Route path="/login" element={ <Login /> } />

                { /* 2. USER(로그인된상태) 또는 그외 접근 가능 */}
                <Route element={ <RoleRoute roles={ ["USER", "ADMIN" ] } /> }>
                    <Route path='/user/info' element={ <h1> 마이페이지 </h1> } />
                </Route>

                { /* 3. ADMIN 또는 그외 접근 가능 */}
                <Route element={ <RoleRoute roles={ ["ADMIN"] } /> } >
                    <Route path='/admin/dashboard' element={ <h1> 관리자페이지 </h1>}/>
                </Route>
                
                { /* 4. 에러 페이지 : 404 , 403 등등 */ }
                <Route path='/forbidden' element = { <h1> 접근 권한 없음 </h1> }/>

            </Routes>
        </Router>
    );
}