package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    /** ✅ 상품 등록 */
    public GoodsDto create(GoodsDto dto) {
        GoodsEntity entity = goodsRepository.save(dto.toEntity());
        return GoodsDto.toDto(entity);
    }

    /** ✅ 전체 상품 조회 */
    public List<GoodsDto> findAll() {
        return goodsRepository.findAll()
                .stream()
                .map(GoodsDto::toDto)
                .collect(Collectors.toList());
    }

    /** ✅ 특정 상품 조회 */
    public GoodsDto findById(int gno) {
        return goodsRepository.findById(gno)
                .map(GoodsDto::toDto)
                .orElse(null);
    }

    /** ✅ 상품 수정 */
    @Transactional
    public GoodsDto update( GoodsDto dto) {
        Optional<GoodsEntity> entityOptional = goodsRepository.findById( dto.getGno() );
        if( entityOptional.isPresent() ){
            GoodsEntity entity = entityOptional.get();
            entity.setGname(dto.getGname());
            entity.setGprice(dto.getGprice());
            entity.setGdesc(dto.getGdesc());
            return GoodsDto.toDto(entity);
        }
        return dto;
    }

    /** ✅ 상품 삭제 */
    public boolean delete( int gno) {
        if (goodsRepository.existsById(gno)) {
            goodsRepository.deleteById(gno);
            return true;
        }
        return false;
    }
}