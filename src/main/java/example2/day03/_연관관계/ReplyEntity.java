package example2.day03._연관관계;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "ereply")
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class ReplyEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int rno; //댓글번호
    private String rcontent; //댓글내용
    // 단방향 연결
    @ManyToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn( name = "bno") // fk 필드명
    private BoardEntity boardEntity;
}









