package example.day04;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crawling")
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    // 1.
    @GetMapping("/task1")
    public List<String> task1(){
        return crawlingService.task1();
    }

    // 2.
    @GetMapping("/task2")
    public List<Map<String, String>>  task2(){
        return crawlingService.task2();
    }

    // 3.
    @GetMapping("/task3")
    public Map<String, String>  task3(){
        return crawlingService.task3();
    }

    // 4.

}
