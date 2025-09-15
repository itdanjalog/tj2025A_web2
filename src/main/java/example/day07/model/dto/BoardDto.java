package example.day07.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@ToString
public class BoardDto {
    private int bno;
    private String bcontent;
    private String bwriter;
}
