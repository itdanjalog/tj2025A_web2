package example.day11;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Example2 {
    public static void main(String[] args) {

        // 임의 데이터
        List< Integer> numbers = List.of( 1,2,3,4,5,6,7,8,9,10 );

        // 스트림이란? 데이터가 다니는 통로
        // 스트림 API : 데이터(매개변수) ---> 중간연산 ---> 최종출력

        // [1] stream() + forEach() : 중간연산 없이 최종출력(forEach)
        // 매개변수에 반복변수를 *하나씩* 대입하여 return 없는 반복문
        numbers.stream().forEach( x -> System.out.println("[1] forEach : " + x ) );

        // [2] stream() + map() + collect( Collectors.toXXX() ); : 중간연산(map) 최종출력(collect)
        // 매개변수에 반복변수를 *하나씩* 대입하여 return 있는 반복문
        List< Integer > newNumbers =
            numbers.stream().map( x  -> x * 2 ).collect( Collectors.toList() );
        System.out.println("[2] 새로운 리스트 : " + newNumbers );

        // [3] stream() + map() + 최종출력=forEach()
        numbers.stream()            // 스트림 시작
                .map( x -> x * 2 )  // 중간 연산
                .forEach( x -> System.out.println("[3] map+foreach : " + x ) ); // 최종 출력
        // [4] stream() + filter() + 최종출력
        numbers.stream()                    // 스트림 시작
                .filter( x -> x % 2 == 0 )  // 중간연산 : 짝수만
                .forEach( x -> System.out.println("[4] filter+foreach : " + x )); // 최종출력
        // [5] stream() + sorted() + 최종출력 , 정렬
        numbers.stream()
                //.sorted() // 기본값: 오름차순
                .sorted( Comparator.reverseOrder()) // 내림차순
                .forEach( x -> System.out.println("[5] sorted+foreach : " + x ) );
        // [6] stream() + distinct() + 최종출력 , 중복제거
        List<Integer> disList = numbers.stream().distinct().collect( Collectors.toList());
        System.out.println("[6] distinct : " + disList );
        // [7] stream() + limit(N) + 최종출력 , 처음부터 N개의 데이터만 제한
        numbers.stream()
                .limit( 5 )
                .forEach( x -> System.out.println( x ));



    } // main end
} // class end




// vs
//for( int index = 0 ; index < numbers.size() ; index++ ){
    //if( numbers.get( index ) % 2 == 0 ){
    //System.out.println("[4] filter+foreach : " + numbers.get( index ) );
    //}
//}


