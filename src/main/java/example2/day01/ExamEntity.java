package example2.day01;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // 해당 클래스에 엔티티임을 주입
@Data // 롬복
public class ExamEntity { // 클래스
    // * dto 처럼 데이터베이스에서 사용될 테이블과 속성(열) 일치
    @Id // primary key
    private int col1;
    private String col2;
    private double col3;
} // class end
