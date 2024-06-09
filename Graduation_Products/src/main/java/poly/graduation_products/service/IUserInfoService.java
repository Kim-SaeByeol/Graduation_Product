package poly.graduation_products.service;

import poly.graduation_products.dto.UserInfoDTO;

public interface IUserInfoService {

    // 아이디 중복체크
    int UserIdExists(final String userId) throws Exception;

    // 이메일 중복체크
    String UserEmailExists(final String email) throws Exception;

    // 별명 중복체크
    int UserNickExists(final String nickName) throws Exception;

    //회원가입
    int insertUserInfo(final String userId,     //아이디
                       final String password,   //비밀번호
                       final String nickname,   // 별명
                       final String email,      // 대표 이메일
                       final String profilePath
                       ) throws Exception;

    //로그인
    int Login(final String userId,      // 아이디
              final String password     // 비밀번호
    ) throws Exception;

    // 아이디 찾기
    String searchUserId(final String nickName,  // 별명
                        final String email      // 이메일
    ) throws Exception;

    // 이메일이 없을 경우 이메일 인증번호 받기
    UserInfoDTO UserEmailAuthNumber(final String email) throws Exception;

    //이메일이 있을 경우 이메일 인증번호 받기
    UserInfoDTO emailAuthNumber(final String email) throws Exception;

    // 비밀번호 찾기
    int searchPassword(final String userId,     // 아이디
                       final String nickName,   // 별명
                       final String email
    ) throws Exception;

    //     비밀번호 재설정
    int newPassword(final String userId,
                    final String newPassword
    ) throws Exception;


    /**
     *
     *  마이페이지 내 정보 수정
     *
     */

    // 프로필 수정

    // 비밀번호 변경  이메일 인증을 할 로직으로 newPassword() 로 진행

    // 대표 이메일 수정

    // 별명 수정

    // 소셜 연동 정보

    // 태그 수정

}
