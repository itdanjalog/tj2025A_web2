package example2.day04.model.entity;


import example2.day04.model.dto.TodoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity // 데이터베이스의 테이블과 영속 관계
@Table( name = "todo") // 데이터베이스의 테이블명 정의
@Data @EqualsAndHashCode(callSuper = false)  // ✅ 경고 해결
@NoArgsConstructor@AllArgsConstructor@Builder // 룸북
public class TodoEntity extends BaseTime  {
    @Id // pk 설정
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int id; // 할일 식별번호

    @Column(nullable = false, length = 100)
    private String title; // 할일 제목

    @Column(columnDefinition = "LONGTEXT")
    private String content; // 할일 내용

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean done; // 할일 상태

    // entity --> dto 변환함수.
    public TodoDto toDto(){
        // entity 에서 dto로 변환할 필드 선택하여 dto객체만들기
        return TodoDto.builder()
                .id( this.id )
                .title( this.title )
                .content( this.content )
                .done( this.done )
                .createAt( this.getCreateAt().toString() ) // BaseTime
                .createAt( this.getUpdateAt().toString() )
                .build();
    }
}












