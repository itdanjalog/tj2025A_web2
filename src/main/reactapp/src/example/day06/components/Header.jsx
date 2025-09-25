import { Link } from "react-router-dom";

export default function Header( props ){
    return<>
        <h3> 헤더 </h3>
        <ul>
            <li> <Link to="/">홈</Link></li>
            <li> <Link to="/menu">제품목록</Link></li>
            <li> <Link to="/cart">카트목록</Link></li>
        </ul>
    </>
}