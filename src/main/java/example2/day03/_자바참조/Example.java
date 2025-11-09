package example2.day03._자바참조;

public class Example {
    public static void main(String[] args) {

        // 자바는 100% 객체지향(OOP) 언어
        System.out.println("출력");
        // System 클래스 , System.out 객체(new없는) , print() 함수
        // 즉] 클래스를 코드에 설계하고 클래스 기반으로 new 해서 객체 만든다.
        // -> 아파트를 도면에 설계하고 도면 기반으로 아파트를 구축한다. <101동> <102동>
        // .참조 : A.B A안에 B를 참조 , A가 NULL 이면 B를 참조할수 없다.

        // JPA는 영속성 : 데이터베이스랑 자바랑 비슷하구나.
        // 데이터베이스의 테이블 == 클래스  == 엔티티클래스
        // 데이터베이스의 테이블내 행/레코드(1줄) == 인스턴스 == 엔티티객체

        // [1] 카테고리 2개 생성 , PK( 상위 테이블 )
        Category category1 = new Category();
        category1.setCno(1); category1.setCname("공지사항");
        Category category2 = new Category();
        category2.setCno(2); category2.setCname("자유게시판");

        // [2] 게시물 생성 , FK( 하위 테이블 )
        // 공지사항에 게시물 작성
        Board board1 = new Board();
        board1.setBno( 1 ); board1.setBtitle("공지1");
        board1.setBcontent("공지내용1");
        // ** 1번 게시물에 1번(공지사항객체) 참조 **//
        board1.setCategory( category1 );
        System.out.println( board1.getCategory().getCname() );

        // [3] 공지사항 데이터로 게시물 조회 , <양방향>
        // 공지사항 객체에 1번게시물을 대입한다.
        category1.getBoardList().add( board1 );
        System.out.println( category1.getBoardList() );

        // * toString() 함수란? 객체 호출시 참조주소값 대신에 참조내용물 출력하는 함수
        // 즉] 인천광역시 부평구 xxx동 101호 --> 안방,주방,

        // [4] 상황1: 1번 공지사항에 게시물 작성
        Board board2 = new Board();
        board2.setBno( 2 ); board2.setBtitle("공지2");
        board2.setBcontent("공지내용2");
        board2.setCategory( category1 ); // ** 단방향 참조 **//
        // ++ 양방향 참조 ** //
        category1.getBoardList().add( board2 );

        // 상황1 결과 : 카테고리로 게시물 조회 , 게시물로 카테고리 조회
        System.out.println( category1.getBoardList() );
        System.out.println( board2.getCategory() );




    } // main end
} // class end
