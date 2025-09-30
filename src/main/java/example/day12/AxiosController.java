package example.day12;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/axios")
public class AxiosController {
    // [1]
    @GetMapping
    public int axios1(){
        System.out.println("AxiosController.axios1");
        return 10;
    }
}









