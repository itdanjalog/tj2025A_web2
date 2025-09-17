export default function Component5( props ){
    const items = [ '사과' , '바나나' , '딸기' ]
    // 1. forEach 반복문은 return 할수 없다.
    const newItems = items.forEach( ( item ) => { console.log( item ); return item; } );    console.log( newItems );
    // 2. map 반복문은 return 할수 있다.
    const newItems2 = items.map( ( item ) => { console.log( item); return item; } );      console.log( newItems2 );
    // 3. 함수 : 변수의 변화를 주는 함수 
    const onAdd = ( )=>{ items.push('수박'); console.log( items ); }
    // ** 함수(컴포넌트) 는 1번 호출에 1번 리턴 , 즉 : 한번 return 된 html(JSX)는 수정불가능 **
    // 해결책 : return 다시 하자 --> 함수 다시 호출 --> 재 렌더링 : 훅( useState 함수 )
    return (<> 
        <h3> JSX 반복문 , forEach : return없다 vs map : return있다 </h3>
        <ul>
            { items.map( (item) => { return <li> { item } </li> } ) }
        </ul>
        순수 JS 방식 : <button onclick = "onAdd()"> item 추가 </button>
        REACT 방식 : <button onClick={ onAdd } > item 추가 </button>
    </>)
} // func end 
