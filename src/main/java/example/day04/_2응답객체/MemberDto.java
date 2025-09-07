package example.day04._2응답객체;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                       // getter, setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor          // 기본 생성자
@AllArgsConstructor         // 모든 필드 생성자
@Builder                    // 빌더 패턴 지원
public class MemberDto {
    private int id;
    private String name;
    private String email;
}