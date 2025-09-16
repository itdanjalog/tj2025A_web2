/*
    [web1] 백틱 템플릿 : 키보드 [tab] 위에 `(백틱) 기호 이용한 문자열내 JS코드 연결방법
            예] let name = "유재석"
            `<div> ${ name } </div>`
    [web2] JSX 템플릿 : 리액트내 자동 포함
            예] let name = "유재석"
            return <div> { name } </div>
*/
// [1] 컴포넌트/함수 선언 
export default function Component3( props ){
    // ---------> JS 코드 START
    let name = "유재석"
    // <--------- JS 코드 END : return 전까지

    // ---------> JSX 코드 START : return 부터는 JSX 문법
    return (<> 
        <div> { name } 입니다. </div>
        <div> { 13+20 } </div>
    </>)
    // <-------- JSX 코드 END 
} // func end 










