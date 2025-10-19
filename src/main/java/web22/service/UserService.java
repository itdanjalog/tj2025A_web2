package web22.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web22.model.dto.UserDto;
import web22.model.mapper.UserMapper;

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

        boolean pwdCheck = encoder.matches(dto.getUpwd(), userDto.getUpwd() );
        if( pwdCheck == true ){
            userDto.setUpwd( null ); // 로그인 전용 dto가 있으면 더 좋다.
            return userDto;
        }else{
            return null;
        }
    }

}
