
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

// // ✅ Component15 컴포넌트 불러오기
// import Component15 from './example/day05/Component15.jsx';

// // ✅ Redux 상태관리의 핵심 도구인 Provider 컴포넌트 불러오기
// import { Provider } from 'react-redux';

// // ✅ Redux 스토어와 퍼시스터(persistor) 불러오기
// //import store, { persistor } from './example/day05/store.js';

// // ✅ redux-persist를 위한 PersistGate 컴포넌트 불러오기
// import { PersistGate } from 'redux-persist/integration/react';

// ✅ 앱을 화면에 렌더링 (예: React 17에서는 ReactDOM.render, React 18 이상에서는 createRoot 사용)
// create.render(

//   // ✅ Redux의 상태 전역 공유를 위해 <Provider>로 전체 앱 감싸기
//   <Provider store={store}>

//     {/* ✅ redux-persist를 위해 PersistGate로 감싸기
//         - loading={null} : 초기 로딩 중 보여줄 화면 (지금은 비워둠)
//         - persistor: redux-persist로 만든 객체로, 저장소를 불러오는 역할 */}
//     <PersistGate loading={null} persistor={persistor}>

//       {/* ✅ 실제 보여줄 컴포넌트 */}
//       <Component15 />

//     </PersistGate>
//   </Provider>
// );

import Component16 from './example/day05/Component16.jsx'
//create.render( <Component16 /> );


import App from './example/day05/실습7/App.jsx'
import store, { persistor } from './example/day05/실습7/store/store.jsx';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
create.render(

  // ✅ Redux의 상태 전역 공유를 위해 <Provider>로 전체 앱 감싸기
  <Provider store={store}>

    {/* ✅ redux-persist를 위해 PersistGate로 감싸기
        - loading={null} : 초기 로딩 중 보여줄 화면 (지금은 비워둠)
        - persistor: redux-persist로 만든 객체로, 저장소를 불러오는 역할 */}
    <PersistGate loading={null} persistor={persistor}>

      {/* ✅ 실제 보여줄 컴포넌트 */}
      <App />

    </PersistGate>
  </Provider>
);