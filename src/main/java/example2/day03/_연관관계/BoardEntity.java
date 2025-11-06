package example2.day03._연관관계;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@Table(name = "eboard")
public class BoardEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int bno;
    private String btitle;
    private String bcontent;
    // * 단방향 연결 * //
    // 하위 엔티티가 상위 엔티티 참조관계
    @ManyToOne( cascade = CascadeType.ALL , fetch = FetchType.EAGER ) // M:1 , 다수FK가 하나PK 에게
    @JoinColumn( name = "cno") // FK 필드명 (+PK필드명과 동일 권장)
    private CategoryEntity categoryEntity;
}






