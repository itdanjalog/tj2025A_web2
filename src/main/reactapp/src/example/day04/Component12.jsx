import { BrowserRouter, Link, Route, Routes, useNavigate, useParams, useSearchParams } from 'react-router-dom'
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

// [4] 제품 소개 페이지 : path 매개변수 전달
function Product( props ){
    // ** 리액트 path 방식 ** :  /product/코카콜라/1000 vs /product?name=코카콜라&price=1000
    const { name , price } = useParams(); // 1.  const { 매개변수명1, 매개변수명2 } = useParams()
    return (<>
        <h3> 제품 소개 페이지 </h3>
        <p> 제품명 : { name } / 
            가격 : { price }
        </p>
    </>)
} 

// [5] 404페이지 컴포넌트
function Page404( props ) {
    // 5-1 : useNavigate() 반환값 저장 
    const navigate = useNavigate();
    const 이동함수 = ()=>{
        // HTML 페이지전환 : <a> , location.href = "경로"
        // location.href="/"

        // 라우터 페이지전환 : <Link> , navigate( "경로" )
        // 5-2 useNavigate() 반환값 인 navigate 사용한
        navigate( "/" );
    }
    return (<>
        <h3> 404 존재 하지 않는 페이지 입니다. </h3>
        <a href="/"> 홈으로1(get방식) </a>
        <Link to="/"> 홈으로2(라우터방식*) </Link>
        <button onClick={ 이동함수 }> 홈으로3 </button>
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
                <Link to="/product"> 제품소개페이지1 (path X )</Link>
                <Link to="/product/코카콜라/1000"> 제품소개페이지2(path O )</Link>
            </ul>
            <Routes>    { /* 가상의 URL 정의하고 정의한 URL 과 매핑할 컴포넌트 정의 */ }
                <Route path='/' element = { <Home/> } />        { /* Route path="가상URL정의" element = { <컴포넌트/> } */}
                <Route path='/about'  element = { <About /> }/>
                <Route path="/mypage" element = { <MyPage /> }></Route>
                <Route 
                    path="/product/:name/:price" 
                    element = { <Product />} >
                </Route>

                { /* 만약에 존재하지 않는 가상 URL 요청하면 404 컴포넌트 */}
                <Route
                    path='*' 
                    element = { <Page404 /> }>
                </Route>
            </Routes>
        </BrowserRouter>
    </>)
}