import { Link } from "react-router-dom";

export default function Header( props ){
    return<>
        <h3> 헤더 </h3>
        <ul>
            <li> <Link to="/">홈</Link></li>
            <li> <Link to="/menu">회원가입</Link></li>
            <li> <Link to="/cart">로그인</Link></li>
        </ul>
    </>
}