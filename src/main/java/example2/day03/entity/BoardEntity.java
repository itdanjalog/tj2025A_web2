package example2.day03.entity;

import example2.day03.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data@Builder
@Entity@Table(name = "examboard")
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int bno; // 게시물번호
    private String btitle; // 게시물제목
    private String bcontent; // 게시물내용

    // + 단방향 , 카테고리 참조 , FK필드
    @ManyToOne // fk필드 선언 방법
    @JoinColumn( name = "cno")
    private CategoryEntity categoryEntity;

    // + 양방향
    @ToString.Exclude // 순환참조 방지
    @Builder.Default // 빌더패턴 사용시 초기값 대입
    @OneToMany( mappedBy = "boardEntity"  , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ReplyEntity> replyList = new ArrayList<>();

    /** ✅ Entity → DTO 변환 메서드 */
    public BoardDto toDto() {
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .cno(this.categoryEntity.getCno())
                .cname( this.categoryEntity.getCname())
                .build();
    }

}