package example2.day03._자바참조;

import lombok.Data;

@Data
public class Board {
    private int bno; // 게시물번호
    private String btitle; //게시물제목
    private String bcontent; // 게시물내용
    private Category category; // FK
}
