package example2.day02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// *엔티티는 서비스에만 사용*
@Data@AllArgsConstructor@NoArgsConstructor@Builder // 롬복
public class GoodsDto {
    // 데이터베이스/엔티티 필드/속성 기반으로 구성
    private int gno;
    private int gprice;
    private String create_date;
    private String update_date;
    private String gname;
    private String gdesc;

    // ++++++ DTO --> ENTITY +++++
    // ++ Controller -> Service ++ : C(등록) U(수정)
    public GoodsEntity toEntity(){
        return GoodsEntity.builder()
                .gno( this.gno )
                .gname( this.gname )
                .gprice( this.gprice )
                .gdesc( this.gdesc )
                .build();
    }

} // class end





