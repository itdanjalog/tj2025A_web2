package example2.day03.dto;

import example2.day03.entity.BoardEntity;
import example2.day03.entity.CategoryEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int cno;
    private String cname;

    /** ✅ DTO → Entity 변환 */
    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .bno(bno)
                .btitle(btitle)
                .bcontent(bcontent)
                .build();
    }
}
