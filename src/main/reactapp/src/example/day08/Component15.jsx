
import axios from 'axios';
import { useRef } from 'react'

export default function Component15(props){
    // [1]
    const axios1 = async()=>{
        try{
            const response = await axios.get("http://localhost:8080/axios");
            const data = response.data;
            console.log( "[1] : " , data );
        }catch(e){ console.log( e ) }
    }
    // [2] 로그인 , axios.post( url , body , option )
    const axios2 = async()=>{
        try{
            const obj = { id : "qwe" , password : "1234"}
            const option = { withCredentials: true }
            const response = await axios.post( "http://localhost:8080/axios/login" , obj , option);
            const data = response.data;
            console.log( "[2] : " , data );
        }catch(e){console.log(e)}
    }
    // [3] 내정보 , axios.get( url , option )
    const axios3 = async()=>{
        try{
            const option = { withCredentials: true }
            const response = await axios.get( "http://localhost:8080/axios/info" , option  );
            const data = response.data;
            console.log( "[3] : " , data );
        }catch(e){console.log(e)}
    }

    // fetch는 기본전송 타입이 'form' , axios는 기본전송 타입이 'json'
    // [4] 일반 폼 : 폼전송시 자바의 변수명 매핑은 form 안에 name 속성으로 매핑
    const formRef1 = useRef();
    const axios4 = async() => {
        try{
            const form = formRef1.current; // 4-1 : useRef 참조중인 dom객체 가져오기
            const option = { headers: { "Content-Type": "application/x-www-form-urlencoded"}  }
            const response = await axios.post( "http://localhost:8080/axios/form" , form , option );
            const data = response.data;
            console.log( '[4] : ' , data );
        }catch(e){ console.log(e)}
    } // func end

    // [5] 첨부파일 폼
    const formRef2 = useRef();
    const axios5 = async() => {
        try{
            const form = formRef2.current;
            const formData = new FormData( form ); // 폼 데이터를 바이너리(바이트) 폼 변환
            const option = { headers: { "Content-Type": "multipart/form-data" }  }
            const response = await axios.post( "http://localhost:8080/axios/formdata" , formData , option );
            const data = response.data;
            console.log( '[5] : ' , data );
        }catch(e){ console.log(e) }
    }

    return(<>
        <h5> AXIOS 테스트 </h5>
        <button onClick={ axios1 }> axios1 </button>
        <button onClick={ axios2 }> axios2 </button>
        <button onClick={ axios3 }> axios3 </button>
        <form ref = { formRef1 }>
            <input name="id" /> <input name="password" />
            <button onClick={ axios4 } type="button"> axios4 </button>
        </form>
        <form ref = { formRef2 }>
            <input type="file" name="file" />
            <button onClick={ axios5 } type="button" > axios5 </button>
        </form>
    </>)
}













