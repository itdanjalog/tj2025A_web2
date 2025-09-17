import { useState } from "react"
export default function Task3( props ){
    // [1] 현재 수량을 관리하는 useState  , const [ 변수명 , set변수명 ] = useState( 초기값 );
        // 1-1.선언 
    const [ count , setCount ] = useState( 0 ); // useState( 초기값 );  
        // 1-2 증가함수 , set변수명( 수정할값 ) : 수정할 값을 대입하여 새로고침(재렌더링/함수재실행) 한다.
    const countIn = ()=>{ setCount( count + 1 ); }
        // 1-3 감소함수
    const countDe = ()=>{ setCount( count - 1 ); }

    // [2] 현재 입력받은 제품명 관리 하는 useState 
        // 2-1 선언
    const [ name , setName ] = useState( '' ); // '' 빈문자열 
        // 2-2 입력받은 제품명 변경 함수  , e : onChange를 이벤트결과 정보 담긴 객체
    const nameChange = ( e ) => { setName( e.target.value ); }
    
    return(<> 
        <h3> Task3 </h3>
        제품명 : <input value={ name } onChange={ nameChange } /> <br/>
        현재 수량 : <span> { count } </span> <br/>
        <button onClick={ countDe } > 감소 </button>
        <button onClick={ countIn } > 증가 </button>
    </>)
} // func end 