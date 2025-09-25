package example.day09;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/day09/trans")
@RequiredArgsConstructor
public class TransController {
    private final TransService transService;
    // 1.
    @PostMapping public boolean trans1(){
        return transService.trans1();
    }
}
