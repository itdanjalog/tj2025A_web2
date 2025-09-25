package example.day09;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransService {
    private final TransMapper transMapper;
    // 1. '유재석' 과 '강호동' insert 하는게 목적(commit)
    // '만약에' 한명이라도 insert가 실패하면 취소(rollback)
    @Transactional // 지정한 함수내 모든 SQL은 트랜잭션 적용
    public boolean trans1(){
        // 1-1 유재석 insert1 하고
        transMapper.trans1( "유재석" );
        // 1-2 강호동 insert2 한다.
        transMapper.trans2( "강호동" );
        return true;
    }
}
