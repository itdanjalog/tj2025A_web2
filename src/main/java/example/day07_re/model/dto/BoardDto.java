package example.day07_re.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@ToString@Builder
public class BoardDto {
    private int bno;
    private String bcontent;
    private String bwriter;
}
