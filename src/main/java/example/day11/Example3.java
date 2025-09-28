package example.day11;

import lombok.ToString;

import java.util.List;
import java.util.function.Supplier;

// 사람 클래스
@ToString
class Person {
    String name; // 멤버변수
    // [생성자]
    public Person() { this.name="익명이름";} // 기본 생성자
    public Person(String name) {
        this.name = name;
        System.out.println("Person 객체 생성됨: " + name);
    }

    // [일반 메소드]
    public void onMessage1(String message) {
        System.out.println("onMessage1 호출: " + message);
    }

    // [static 메소드]
    public static void onMessage2(String message) {
        System.out.println("onMessage2 호출: " + message);
    }
}

public class Example3 {
    public static void main(String[] args) {
        // * 임의의 컬렉션
        List<String> names = List.of("유재석", "강호동", "신동엽");

        // -----------------------------------------------------------
        // 1. static 메소드 참조
        // - 클래스명::static메소드명
        // - 조건: forEach 의 매개변수 타입 == static메소드의 매개변수 타입
        System.out.println("=== 1. static 메소드 참조 ===");
        // (1) 람다식
        names.forEach((name) -> { Person.onMessage2(name); });
        // (2) 메소드 레퍼런스 (::)
        names.forEach(Person::onMessage2);

        // -----------------------------------------------------------
        // 2. 생성자 참조
        // - 클래스명::new
        // - 조건: forEach 매개변수 == 생성자의 매개변수
        System.out.println("=== 2. 생성자 참조 ===");
        // (1) 람다식
        names.forEach((name) -> { new Person(name); });
        // (2) 메소드 레퍼런스 (::)
        names.forEach(Person::new);

        // -----------------------------------------------------------
        // 3. 일반 메소드 참조
        // - 객체명::메소드명
        // - 조건: forEach 의 매개변수 타입 == 해당 객체 메소드의 매개변수 타입
        System.out.println("=== 3. 일반 메소드 참조 ===");
        Person person1 = new Person();
        // (1) 람다식
        names.forEach((name) -> { person1.onMessage1(name); });
        // (2) 메소드 레퍼런스 (::)
        names.forEach(person1::onMessage1);

        // -----------------------------------------------------------
        // 4. Supplier 와 생성자 참조
        // - Supplier<T> : 인자 없이 객체를 생성해 반환
        System.out.println("=== 4. Supplier 와 생성자 참조 ===");
        // (1) 메소드 레퍼런스 (::)
        Supplier<Person> ref = Person::new; // 기본생성자 호출
        Person personA = ref.get();
        System.out.println("personA = " + personA);
        // (2) 람다식
        Supplier<Person> ref2 = () -> new Person();
        Person personB = ref2.get();
        System.out.println("personB = " + personB);
    }
}
