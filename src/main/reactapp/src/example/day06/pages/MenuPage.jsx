import { useDispatch } from "react-redux";
import { 담기 } from "../store/cartSlice";

export default function MenuPage( props ){
    // ** 서버로 받은 제품목록 가정한다. **
    const menu = [
        { id: 1, name: "아메리카노", price: 3000 }, 
        { id: 2, name: "카페라떼", price: 4000 },
        { id: 3, name: "카푸치노", price: 4500 },
    ];
    // [1] 담기 버튼을 클릭했을때 리덕스 상태에 저장하기 
    const dispatch  = useDispatch()
    const 카트담기 = async( m )=>{
        dispatch( 담기( m ) );
    }
    return<>
        <h3> 메뉴페이지 </h3>
        { /* 컴포넌트내 return 안에서는 jsx 문법을 따라야한다.  */}
        { 
        /* jsx 시작 알림  */ /* map반복문은 1번 반복 할때마다 하나의 리턴값(HTML) 을 반환한다. */
            menu.map( (m)=>{
                return <> 
                <div> 
                    { m.name } { m.price } 
                    <button onClick={ ()=>{ 카트담기(m) } }> 담기 </button> 
                </div> </>
            } )
        /* jsx 끝 알림 */
        }
    </>
}