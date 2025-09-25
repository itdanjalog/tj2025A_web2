package example.day09;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransService {
    private final TransMapper transMapper;
    // 1. '유재석' 과 '강호동' insert 하는게 목적(commit)
    // '만약에' 한명이라도 insert가 실패하면 취소(rollback)
    public boolean trans1(){
        // 1-1 유재석 insert 하고
        transMapper.trans1( "유재석" );
        // 1-2 강호동 insert 한다.
        transMapper.trans1( "강호동" );
        return true;
    }
}
