package poly.graduation_products.service;

import poly.graduation_products.dto.UserInfoDTO;

public interface IUserInfoService {

    // 아이디 중복체크
    String UserIdExists(final String userId) throws Exception;

    // 이메일 중복체크
    String UserEmailExists(final String email) throws Exception;

    // 별명 중복체크
    String UserNickExists(final String nickName) throws Exception;

    //회원가입
    int insertUserInfo(final String userId,
                       final String password,
                       final String email,
                       final String nickname,
                       final String userName) throws Exception;

    //로그인
    int Login(final String userId,
                     final String password) throws Exception;

    // 아이디 찾기
    String searchUserId(final String userName,
                            final String email) throws Exception;

    //이메일 인증번호 받기
    UserInfoDTO emailAuthNumber(final String email) throws Exception;

    // 비밀번호 찾기
    int searchPassword(final String userId,
                       final String userName,
                       final String email) throws Exception;

    //     비밀번호 재설정
    String  newPassword(final String userId, final String newPassword) throws Exception;






    void updateUserInfo(final String userId,
                        final String nickname) throws Exception;

    /**
     * 내 정보 삭제
     */
    void deleteUserInfo(final String userId) throws Exception;

}
