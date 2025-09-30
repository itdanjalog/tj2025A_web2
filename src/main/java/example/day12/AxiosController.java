package example.day12;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    // [4]
    @PostMapping("/form")
    public boolean axios4( @RequestParam Map<String,String> map ){
        System.out.println("AxiosController.axios4");
        System.out.println("map = " + map);
        return true;
    }

    // [5]
    @PostMapping("/formdata")
    public boolean axios5( @RequestParam MultipartFile file ){
        System.out.println("AxiosController.axios5"); // soutm
        System.out.println("file = " + file); // soutp
        return true;
    }
}









