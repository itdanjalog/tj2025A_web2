export default function CartPage( props ){
    // ** 리덕스에 저장된 카트 목록으로 가정한다. **
    const cart = [
        { id : 1 , name: "아메리카노", price: 3000 , count : 3 } ,
        { id : 3 , name: "카푸치노", price: 4500 , count : 1 },
    ] 
    return<>
        <h3> 카트페이지 </h3>
        {
            cart.map( ( c )=>{
                return <> <div> { c.name } { c.count } { c.count*c.price } </div> </>
            })
        }
    </>
}