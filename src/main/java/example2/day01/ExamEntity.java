package example2.day01;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // 해당 클래스를 엔티티 사용
@Data
public class ExamEntity { // 클래스
    // 멤버변수
    @Id // 식별키(primary key) 필수
    private int col1;
    private String col2;
    private double col3;
    // ---> 데이터베이스 생성된 테이블 확인
}