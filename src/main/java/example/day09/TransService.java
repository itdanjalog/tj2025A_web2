package example.day09;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransService {
    private final TransMapper transMapper;
    // 1. '유재석' 과 '강호동' insert 하는게 목적(commit)
    // '만약에' 한명이라도 insert가 실패하면 취소(rollback)
    @Transactional // 지정한 함수내 모든 SQL은 트랜잭션 적용
    public boolean trans1(){
        // [1-2] AOP가 Before로 먼저 commit 준비
        // 1-1 유재석 insert1 하고
        transMapper.trans1( "유재석" ); // 정상
        // 1-2 강호동 insert2 한다.
        transMapper.trans2( "강호동" ); // 예제 특성상 억지로 비정상(예외처리)
        // SQLSyntaxErrorException 반환되면 AOP는 rollback(취소) 처리한다.
        // 앞전에 실행한 모든 SQL 은 COMMIT(완료) 안되므로 없는일로 된다.
        return true;
        // [1-2] AOP가 After로 처리후 commit 또는 rollback 한다.
    } // func end

    // 2.
    @Transactional // 만약에 지정한 함수내 예외( RuntimeException 실행예외 ) 가 발생하면 함수내 SQL 모두 취소한다.
    boolean transfer( Map<String,Object> transInfo ){
        int money = Integer.parseInt( String.valueOf( transInfo.get( "money") ) );
        // 1. 신동엽의 10만원 차감
        String fromname = String.valueOf( transInfo.get("fromname") );
        transMapper.withdraw( fromname , money );

        // 만약에 강제로 예외 발생해서 rollback , *주석처리 했을때 안했을때 비교하기 *
        if( true ){ // 신동엽이 돈이 10만원 없거나 내부적으로 로직/조건 문제가 있을떄
            throw new RuntimeException("강제예외"); // throw new 예외클래스명("메시지") // 강제 예외 발생
        }

        // 2. 서장훈의 10만원 증가
        String toname = String.valueOf( transInfo.get( "toname") );
        transMapper.deposit( toname , money );
        return true;
    } // func end

} // class end
