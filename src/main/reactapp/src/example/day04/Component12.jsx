import { BrowserRouter, Link, Route, Routes, useSearchParams } from 'react-router-dom'
// [1] 메인페이지 컴포넌트 
function Home(props){ return ( <> 메인페이지 </>) }
// [2] 소개페이지 컴포넌트
function About( props ){ return (<> 소개페이지 </> ) }
// [3] 마이페이지 컴포넌트 : 쿼리스트링 매개변수 전달
function MyPage( props ){    
    // JS(web1) 방식 : const name = new URL( location.href ).searchParams.get('name')
    // ** 리액트 queryString 방식 **
    const [ searchParams ] = useSearchParams();     // 1. const [ searchParams ] = useSearchParams();
    const name = searchParams.get('name');          // 2. const 변수명 = searchParams.get( 매개변수명 );
    const age = searchParams.get('age');
    return(<>
        <h3> 마이페이지 </h3>
        <p> 이름 : { name } / 나이 : { age } </p>
    </>)
}







// [*] 라우터 : 하나의 컴포넌트가 여러 컴포넌트를 연결 구조 // 가상의 URL 만들기
// 1. 라이브러리 (터미널) 설치 : npm i react-router-dom , *리액트 종료된 상태에서 진행, 설치후 리액트 실행
export default function Component12( props ){
    return (<>
        <BrowserRouter>
            <ul>        { /* 진짜URL 이 아닌 가상의 URL 로 이동할때는 <a> 마크업 대신 <Link to="가상URL"> 사용  */} 
                <a href="/" > 메인페이지(home/html방식) </a>
                <Link to="/" > 메인페이지(home/react라우터방식)</Link> 
                <Link to="/about"> 소개페이지 </Link>
                <Link to="/mypage"> 마이페이지1(쿼리스트링x) </Link>
                <Link to="/mypage?name=유재석&age=40"> 마이페이지2(쿼리스트링o) </Link>
            </ul>
            <Routes>    { /* 가상의 URL 정의하고 정의한 URL 과 매핑할 컴포넌트 정의 */ }
                <Route path='/' element = { <Home/> } />        { /* Route path="가상URL정의" element = { <컴포넌트/> } */}
                <Route path='/about'  element = { <About /> }/>
                <Route path="/mypage" element = { <MyPage /> }></Route>
            </Routes>
        </BrowserRouter>
    </>)
}