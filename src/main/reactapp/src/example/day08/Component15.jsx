
import axios from 'axios';

export default function Component15(props){
    // [1]
    const axios1 = async()=>{
        try{
            const response = await axios.get("http://localhost:8080/axios");
            const data = response.data;
            console.log( "[1] : " , data );
        }catch(e){ console.log( e ) }
    }
    // [2] 로그인
    const axios2 = async()=>{
        try{
            const obj = { id : "qwe" , password : "1234"}
            const response = await axios.post( "http://localhost:8080/axios/login" , obj )
            const data = response.data;
            console.log( "[2] : " , data );
        }catch(e){console.log(e)}
    }
    // [3] 내정보
    const axios3 = async()=>{
        try{
            const response = await axios.get( "http://localhost:8080/axios/info" );
            const data = response.data;
            console.log( "[3] : " , data );
        }catch(e){console.log(e)}
    }
    return(<>
        <h5> AXIOS 테스트 </h5>
        <button onClick={ axios1 }> axios1 </button>
        <button onClick={ axios2 }> axios2 </button>
        <button onClick={ axios3 }> axios3 </button>
    </>)
}













