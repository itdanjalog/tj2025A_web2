package example2.day02;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goods")
@NoArgsConstructor @AllArgsConstructor
@Builder @Data
@EqualsAndHashCode(callSuper = false) // ✅ 추가
public class GoodsEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int gno;

    @Column(nullable = false, length = 100)
    private String gname;

    @Column(nullable = false)
    private int gprice;

    @Column(length = 500)
    private String gdesc;
}