package example.day12;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/axios")
public class AxiosController {
    // [1]
    @GetMapping
    public int axios1(){
        System.out.println("AxiosController.axios1");
        return 10;
    }
    // [2]
    @PostMapping("/login")
    public boolean axios2(
            @RequestBody Map<String, String> map ,
            HttpSession session ){
        String id = map.get("id");
        // 2-1 로그인 세션의 속성 등록
        session.setAttribute("loginId" , id );
        return true;
    }
    // [3]
    @GetMapping("/info")
    public boolean axios3( HttpSession session ){
        Object object = session.getAttribute("loginId");
        if( object == null ) return false; // 비로그인중
        return true; // 로그인중
    }
}









