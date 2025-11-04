package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // 2. 전체조회
    public List<GoodsDto> goodsAll(){
        List<GoodsEntity> goodsEntityList = goodsRepository.findAll(); // 1. 모든 엔티티를 조회한다.
        // 2. 모든 엔티티를 DTO로 변환한다.
        // 방법2 : 스트림API , java : 리스트명.stream().map() vs js : 리스트명.map()
        List<GoodsDto> goodsDtoList = goodsRepository.findAll()
                .stream().map( GoodsEntity :: toDto ) // 엔티티 하나씩 dto로 메소드 호출
                .collect( Collectors.toList() ); // map에서 반환된 값들을 리스트로 반환

        return goodsDtoList; // DTO LIST 반환한다.
    }

    // 3. 개별조회
    public GoodsDto goodsGet( int gno ){
        Optional<GoodsEntity> optional = goodsRepository.findById( gno ); // 1. 개별 조회할 gno 의 엔티티 조회한다.
        if( optional.isPresent() ){ // 2. 조회 결과가 있으면
            GoodsEntity entity = optional.get(); // 3. 엔티티 꺼내기
            return entity.toDto(); // 4. 엔티티를 dto로 반환한다.
        }
        return null; // 2.조회 결과가 없으면
    }

} // class end



//// 방법1 :
//List<GoodsDto> goodsDtoList = new ArrayList<>();
//        for( int i = 0 ; i < goodsEntityList.size() ; i++ ){
//GoodsEntity entity = goodsEntityList.get( i );  // i번째 엔티티 꺼내서
//            goodsDtoList.add( entity.toDto() ); // 엔티티를 dto로 변환후 리스트에 저장
//        }
//// 방법2 : 스트림API
//List<GoodsDto> goodsDtoList = goodsEntityList
//        .stream().map( GoodsEntity :: toDto )
//        .collect( Collectors.toList() );












