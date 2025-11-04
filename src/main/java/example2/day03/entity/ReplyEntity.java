package example2.day03.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder
@Entity@Table( name = "examreply")
@NoArgsConstructor
@AllArgsConstructor
public class ReplyEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int rno; // 댓글번호
    private String rcontent; // 댓글내용

    // + 단방향 , 게시물 참조 , FK 필드
    @ManyToOne // fk필드 선언 방법
    @JoinColumn( name = "bno")
    private BoardEntity boardEntity;

}