package example.day11;

import lombok.extern.log4j.Log4j2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// [1] 인터페이스 선언
// - 인터페이스는 주로 '추상 메소드(구현부 없는 메소드)'를 정의한다.
// - Calculator 인터페이스는 plus 라는 더하기 메소드를 정의만 하고,
//   실제 동작(구현)은 다른 클래스, 익명 객체, 또는 람다식에서 처리해야 한다.
interface Calculator {
    int plus(int x, int y); // 추상메소드 (몸체 없음)
}

@Log4j2
public class Example1 { // 클래스 선언
    // [2] 일반 static 메소드 정의
    // - static 이므로 new 객체 생성 없이 바로 호출 가능하다.
    public static int plus(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {

        // -----------------------------------------------------------
        // 1. 일반 메소드 호출
        // - 클래스 내부의 static 메소드를 바로 호출하는 방식
        int result = plus(3, 5);
        System.out.println("일반 메소드 호출 결과: " + result); // 출력: 8

        // -----------------------------------------------------------
        // 2. 인터페이스를 이용한 메소드 호출
        // - 인터페이스는 직접 객체 생성이 불가능하다.
        //   따라서 반드시 '구현'이 필요하다.
        // - 구현 방법:
        //   (1) implements 로 구현 클래스 작성
        //   (2) 익명 클래스 (1회성 구현) 사용
        // 아래 코드는 (2)번 방식: 익명 클래스 사용
        Calculator calc = new Calculator() {
            @Override
            public int plus(int x, int y) {
                return x + y; // 더하기 기능 구현
            }
        };
        int result2 = calc.plus(3, 5);
        System.out.println("익명 클래스 호출 결과: " + result2); // 출력: 8

        // -----------------------------------------------------------
        // 3. 람다식으로 인터페이스 구현
        // - 자바 8부터는 인터페이스에 추상 메소드가 하나뿐이라면(함수형 인터페이스)
        //   람다식(->)으로 더 간단하게 구현 가능하다.
        // - (x, y) -> x + y  는 메소드 본문을 바로 정의하는 방식
        Calculator calc3 = (x, y) -> x + y;
        int result3 = calc3.plus(3, 5);
        System.out.println("람다식 호출 결과: " + result3); // 출력: 8

        // -----------------------------------------------------------
        // 4. 자바에서 제공하는 함수형 인터페이스들
        // java.util.function 패키지에 포함되어 있다.
        // -----------------------------------------------------------

        // [4-1] Function<T, R>
        // - 입력(T)을 받아서 결과(R)를 반환하는 함수형 인터페이스
        // - apply(T) 메소드를 사용
        Function<Integer, Integer> function = x -> x * 2; // 입력값에 2배
        System.out.println("Function 적용(3): " + function.apply(3)); // 출력: 6
        System.out.println("Function 적용(7): " + function.apply(7)); // 출력: 14

        // [4-2] Predicate<T>
        // - 입력(T)을 받아서 true / false 를 반환하는 함수형 인터페이스
        // - test(T) 메소드를 사용
        Predicate<Integer> predicate = x -> x % 2 == 0; // 짝수 여부 판단
        System.out.println("Predicate 적용(3): " + predicate.test(3)); // 출력: false
        System.out.println("Predicate 적용(4): " + predicate.test(4)); // 출력: true

        // [4-3] Supplier<T>
        // - 매개변수 없이 결과(T)만 반환하는 함수형 인터페이스
        // - get() 메소드를 사용
        Supplier<Double> supplier = () -> Math.random(); // 난수 제공
        System.out.println("Supplier 난수: " + supplier.get());

        // [4-4] Consumer<T>
        // - 입력(T)을 받고, 반환값은 없는 함수형 인터페이스
        // - accept(T) 메소드를 사용
        Consumer<String> consumer = str -> System.out.println("Consumer 출력: " + str);
        consumer.accept("안녕하세요."); // 입력값을 소비(출력)만 함

    } // main 메소드 끝
} // Example1 클래스 끝
