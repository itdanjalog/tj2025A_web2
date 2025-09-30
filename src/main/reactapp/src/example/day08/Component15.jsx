
import axios from 'axios';

export default function Component15(props){
    // [1]
    const axios1 = async()=>{
        try{
            const response
                = await axios.get("http://localhost:8080/axios");
            const data = response.data;
            console.log( "[1] : " , data );
        }catch(e){ console.log( e ) }
    }

    return(<>
        <h5> AXIOS 테스트 </h5>
        <button onClick={ axios1 }> axios1 </button>
    </>)
}