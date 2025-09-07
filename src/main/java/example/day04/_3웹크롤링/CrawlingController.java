package example.day04._3웹크롤링;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task/day04")
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    // 1.
    @GetMapping("/crawling1")
    public List<String> task1(){
        return crawlingService.task1();
    }

    // 2.
    @GetMapping("/crawling2")
    public List<Map<String, String>>  task2(){
        return crawlingService.task2();
    }

    // 3.
    @GetMapping("/crawling3")
    public Map<String, String>  task3(){
        return crawlingService.task3();
    }

    // 3.
    @GetMapping("/crawling4")
    public List<String>  task4(){
        return crawlingService.task4();
    }



}
