package example.day11;

import java.util.List;
import java.util.function.Function;

public class Example3 {
    public static void main(String[] args) {

        // [*] 메소드 레퍼런스 API : **이미 정의된** 메소드를 참조해서 사용하는 표현식 + 람다
        // 클래스명.정적메소드명( ) vs 클래스명 :: 정적메소드명
        // 객체명.메소드명( ) vs 객체명 :: 메소드명

        // [1] 예1
        System.out.println( Integer.parseInt("123") ); // 문자타입 --> 숫자타입 변환

        Function< String , Integer > func = Integer::parseInt;
        System.out.println(  func.apply( "123" ) );

        // [2] 예2
        List<String> names = List.of("유재석" , "강호동" , "신동엽" );
        for( int i = 0 ; i<names.size() ; i++ ){ System.out.println( names.get(i) ); }

        names.stream().forEach( name -> System.out.println( name ) );

        names.stream().forEach( System.out :: println  );

    } // main end
} // class end

// 정적메소드 : 객체(인스턴스)없이 사용 가능한 메소드 , 즉] static 있는
// (멤버)메소드 : 객체(인스턴스) 통해 사용 가능한 메소드, 즉] static 없는
