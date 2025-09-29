package example.day11;

import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

// 사람 클래스
@ToString
class Person {

    String name; // 멤버변수

    // [생성자]
    public Person() {
        this.name="익명이름"; // 기본 생성자: 매개변수가 없을 때 "익명이름"으로 초기화
        System.out.println("Person.Person");
    }

    public Person(String name) {
        this.name = name; // 생성자로 전달받은 값을 멤버변수에 저장
        System.out.println("Person.Person");
        System.out.println("name = " + name);
    }

    // [일반 메소드]
    public void onMessage1(String message) {
        // 인스턴스 메소드: 객체가 존재해야 호출 가능
        System.out.println("Person.onMessage1");
        System.out.println("message = " + message);
    }

    // [static 메소드]
    public static void onMessage2(String message) {
        // 정적 메소드: 객체 생성 없이 클래스명으로 호출 가능
        System.out.println("Person.onMessage2");
        System.out.println("message = " + message);
    }

}

public class Example3 {
    public static void main(String[] args) {
        // * 임의의 문자열 리스트 생성
        List<String> names = List.of("유재석", "강호동", "신동엽");

        // -----------------------------------------------------------
        // 1. static 메소드 참조
        // - 형식: 클래스명::static메소드명
        // - 조건: forEach 의 매개변수 타입 == static메소드의 매개변수 타입
        System.out.println("=== 1. static 메소드 참조 ===");
        // (1) 람다식 사용
        names.forEach((name) -> { Person.onMessage2(name); });
        // (2) 메소드 레퍼런스 (::) 사용 → 위 코드를 더 간단히 표현
        names.forEach(Person::onMessage2);

        // -----------------------------------------------------------
        // 2. 생성자 참조
        // - 형식: 클래스명::new
        // - 조건: forEach 매개변수 == 생성자의 매개변수
        System.out.println("=== 2. 생성자 참조 ===");
        // (1) 람다식 사용 → new Person(name)
        names.forEach((name) -> { new Person(name); });
        // (2) 메소드 레퍼런스 (::) 사용
        names.forEach(Person::new);

        // -----------------------------------------------------------
        // 3. 일반 메소드 참조 (인스턴스 메소드 참조)
        // - 형식: 객체명::메소드명
        // - 조건: forEach 의 매개변수 타입 == 해당 객체 메소드의 매개변수 타입
        System.out.println("=== 3. 일반 메소드 참조 ===");
        Person person1 = new Person(); // person1 객체 생성
        // (1) 람다식 사용
        names.forEach((name) -> { person1.onMessage1(name); });
        // (2) 메소드 레퍼런스 (::) 사용
        names.forEach(person1::onMessage1);

        // -----------------------------------------------------------
        // 4. Stream API + 메소드 레퍼런스 활용
        // - Stream API와 결합하면 더 간결하고 강력하게 사용 가능
        System.out.println("=== 4. Stream API 예제 ===");
        names.stream()
                .map(Person::new)          // 생성자 참조: 문자열(name)을 받아 Person 객체 생성
                .forEach(System.out::println); // 출력 메소드 참조: System.out.println 실행


    }
}