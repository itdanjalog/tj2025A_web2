package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsService {
    private final GoodsRepository goodsRepository;
    // 1. 저장
    public GoodsDto goodsSave( GoodsDto goodsDto ){
        // 1. 저장할 dto를 매개변수로 받는다.
        GoodsEntity entity = goodsDto.toEntity();// 2. 저장할 dto를 entity 변환한다.
        GoodsEntity savedEntity = goodsRepository.save( entity );// 3. .save() 이용한 엔티티 영속화(저장) 하기
        // 4. 만약에 pk가 생성 되었으면 생성된 엔티티를 dto로 변환하여 반환
        if( savedEntity.getGno() >= 0 ){ return savedEntity.toDto(); }
        return goodsDto;
    }

}
















