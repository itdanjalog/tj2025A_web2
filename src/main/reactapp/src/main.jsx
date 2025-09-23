
// ======================== 고정 =====================
// 1. 리액트 라이브러리의 최초(ROOT) 생성하는 함수 IMPORT
import { createRoot } from 'react-dom/client'
// 2. index.html 에서 root 마크업 가져오기
const root = document.querySelector('#root')
// 3. 가져온 root 마크업을 createRoot 함수의 매개변수로 전달
const create = createRoot( root );

// =============== 최초 렌더링할 컴포넌트 ===============
// 4. 렌더링(화면출력)할 컴포넌트(함수) import 
// import App from './App.jsx'
// 5. 컴포넌트 렌더링하기 create.render( )
// create.render( <App> </App> )

// day01 
import Component1 from './example/day01/Component1.jsx'
import Component2 from './example/day01/Component2.jsx'
import Component3 from './example/day01/Component3.jsx'
// *** render() 1번만 가능하다.! ***
// create.render( <Component1> </Component1> )
// create.render( <Component1 /> )
// create.render( <Component2/> )
// create.render( <Component3 /> )

// day 01 Task
import Task1 from './example/day01/Task1.jsx'
// create.render( <Task1 /> )
import Task2 from './example/day01/Task2.jsx'
// create.render( <Task2 /> )

// day 02
import Component4 from './example/day02/Component4.jsx';
// create.render( <Component4 /> )
import Component5 from './example/day02/Component5.jsx';
// create.render( <Component5 /> )
import Component6 from './example/day02/Component6.jsx';
//create.render( <Component6 /> )
import Component7 from './example/day02/Component7.jsx';
// create.render( <Component7 /> )

// day 02 task 
import Task3 from './example/day02/Task3.jsx';
//create.render( <Task3 /> )
import Task4 from './example/day02/Task4.jsx';
// create.render( <Task4 /> )

// day03 
import Component8 from './example/day03/Component8.jsx'
//create.render( <Component8 /> );
import Component9 from './example/day03/Component9.jsx'
// create.render( <Component9 /> );
import Component10 from './example/day03/Component10.jsx';
// create.render( <Component10 /> );


// day04 
import Component11 from './example/day04/Component11.jsx'
//create.render( <Component11 /> );

// import Component12 from './example/day04/Component12.jsx'
// create.render( <Component12 /> );

import Task6 from './example/day04/Task6.jsx'
// create.render( <Task6 /> );

// // day05
// import Component13 from './example/day05/Component13.jsx'
// // [1] 내가 만든 스토어(여러개 상태(전역변수) 를 갖는 저장소) 불러오기
// import store from './example/day05/store.jsx'
// // [2] store 를 사용할 곳에 store 공급 해주기 , <Provider store={ 내가만든스토어 } >
// import { Provider } from 'react-redux';
// // * 주의할점 : dispath 보다 먼저 'Provider'실행 되어야 한다. // 관례적으로 main.jsx에서 공급한다.
// create.render(
//     <Provider store={ store }>
//         <Component13 />
//     </Provider>
// );

import App from './example/day05/실습7/App.jsx'
create.render( <App /> );