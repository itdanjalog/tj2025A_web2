package web2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web2.model.dto.UserDto;
import web2.model.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 회원가입 (PK 반환)
    public int signup(UserDto dto) {
        dto.setUpwd(encoder.encode(dto.getUpwd())); // 비밀번호 암호화

        userMapper.save(dto); // INSERT 후 dto.uno 채워짐
        return dto.getUno(); // 생성된 회원번호 반환
    }

    // 로그인용 사용자 조회
    public UserDto login( UserDto dto) {
        UserDto userDto =  userMapper.findByUid( dto.getUid() );
        if( userDto == null ){ return null; }


        //해시값을 알면 그 해시에 사용된 Salt는 추출 가능하다.
        //그러나 Salt가 있어도 **복호화(원문 복원)**는 불가능하다.

        // 사용자(클라이언트)가 로그인 폼에 비밀번호(평문)를 입력해서 서버로 전송.
        //서버는 DB에서 해당 사용자의 저장된 해시 문자열(예: $2a$10$WFmNdF...AtKa)을 가져옴.
        //저장된 해시 문자열에서 salt와 cost 정보를 꺼냄(이 값은 해시 내부에 포함되어 있음).
        //서버는 입력된 평문에 동일한 salt와 cost를 적용해 같은 방식으로 해시를 계산함.
        //새로 계산된 해시와 DB에 저장된 해시를 비교(==) — 같으면 인증 성공, 다르면 실패.
        //요점: 비교는 해시 ↔ 해시 이므로 복호화 필요 없음.

        boolean pwdCheck = encoder.matches(dto.getUpwd(), userDto.getUpwd() );
        if( pwdCheck == true ){
            userDto.setUpwd( null ); // 로그인 전용 dto가 있으면 더 좋다.
            return userDto;
        }else{
            return null;
        }
    }

}
