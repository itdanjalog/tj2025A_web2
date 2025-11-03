package example2.day02;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsDto {
    private int gno;
    private String gname;
    private int gprice;
    private String gdesc;

    // ✅ DTO → Entity 변환
    public GoodsEntity toEntity() {
        return GoodsEntity.builder()
                .gno(gno)
                .gname(gname)
                .gprice(gprice)
                .gdesc(gdesc)
                .build();
    }

    // ✅ Entity → DTO 변환
    public static GoodsDto toDto(GoodsEntity entity) {
        return GoodsDto.builder()
                .gno(entity.getGno())
                .gname(entity.getGname())
                .gprice(entity.getGprice())
                .gdesc(entity.getGdesc())
                .build();
    }
}