package example.day09.controller;

import example.day09.TransService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/day09/trans")
@RequiredArgsConstructor
@Log4j2
public class TransController {
    private final TransService transService;
    // 1.
    @PostMapping public boolean trans1(){
        return transService.trans1();
    }
    // 2. 신동엽이 서장훈 에게 10만원 보내는 예제 ,신동엽 -빼기 , 서장훈 +더하기
    @PostMapping("/transfer") // { "fromname" : "신동엽" , "toname" : "서장훈" , "money" : "100000"}
    public boolean transfer(
            @RequestBody Map<String,Object> fransInfo ){
        log.info("계좌이체 요청: {}", fransInfo);   // info 로그
        boolean result = false;
        try {
             result = transService.transfer(fransInfo);
            if (result) {
                log.info("이체 성공: {} → {} ({}원)",
                        fransInfo.get("fromname"),
                        fransInfo.get("toname"),
                        fransInfo.get("money"));
            } else {
                log.error("이체 실패: {}", fransInfo);
            }
        } catch (Exception e) {
            log.error("이체 실패: {}", fransInfo);
        }
        return result;
    }

    // 3. 로깅 예제
    @GetMapping public void log(){
        log.debug("debug log - 디버깅용 상세 정보");
        log.info("info log - 서비스 흐름, 주요 상태");
        log.warn("warn log - 잠재적 문제 (예: 잔액 부족)");
        log.error("error log - 예외/실패 상황");
    }

} // class end










