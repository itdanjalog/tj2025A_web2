package example.day11;

// [2] 인터페이스 : 주로 추상메소드(구현부없는) 를 정의한다.
interface Calculator{
    // 추상메소드
    int plus( int x , int y );
}



public class Example1 {
    // [1] 일반 static 메소드 정의
    public static int plus( int x , int y ){ return x + y; }
    public static void main(String[] args) {
        // [1] 일반 메소드 호출 : static 일때 객체가 필요없다.
        int result = plus( 3 , 5 );
        System.out.println("[1] 일반 메소드 호출 : " + result );

        // [2] 인터페이스 추상메소드 호출 : 1)구현체 2)익명구현체
        // 1) implements 구현체 만든다.
        // 2) 익명구현체(1회성) : new 인터페이스(){ 구현 };
        Calculator calc = new Calculator(){
            @Override // 오버라이딩 : 상속 또는 추상 메소드를 재구현
            public int plus(int x, int y) {
                return x + y ;
            }
        };
        int value1 = calc.plus( 3 , 5 );
        System.out.println("[2] 익명 구현체 메소드 호출 : " + value1 );

        // [3] 람다식 으로 익명 구현체, java 8 , ( 매개변수 ) -> { 구현부 }
        Calculator calc2 = ( x , y ) -> x + y;
        int value2 = calc2.plus( 3 , 5 );
        System.out.println("[3] 람다식 메소드 호출 : " + value2 );


    } // main end
} // class end
