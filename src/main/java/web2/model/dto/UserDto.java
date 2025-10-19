package web2.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private int uno;             // 회원번호
    private String uid;          // 아이디
    private String upwd;         // 비밀번호
    private String uname;        // 닉네임(이름)
    private String uphone;       // 연락처
    private String urole;        // 권한
    private String udate; // 가입일
}