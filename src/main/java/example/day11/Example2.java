package example.day11;

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

    }
}
