import { Link } from "react-router-dom";

export default function Header( props ){
    return(<>
        <div>
            <h3> 헤더 </h3>
            <ul>
                <li> <Link to ="/"> 홈 </Link></li>
                <li> <Link to ="/login"> 로그인 </Link></li>
                <li> <Link to ="/profile"> 프로필 </Link></li>
            </ul>
        </div>
    </>)
}