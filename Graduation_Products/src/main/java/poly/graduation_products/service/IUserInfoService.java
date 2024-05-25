package poly.graduation_products.service;

import poly.graduation_products.dto.UserInfoDTO;

public interface IUserInfoService {

    // 아이디 중복체크
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    // 이메일 중복체크
    UserInfoDTO getUserEmailExists(UserInfoDTO pDTO) throws Exception;

    // 별명 중복체크
    UserInfoDTO getUserNickExists(UserInfoDTO pDTO) throws Exception;

    //회원가입
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    //로그인
    int getUserLogin(UserInfoDTO pDTO) throws Exception;

    // 아이디 찾기
    String searchUserIdProc(UserInfoDTO pDTO) throws Exception;

    //이메일 인증번호 받기
    UserInfoDTO getEmailAuthNumber(UserInfoDTO pDTO) throws Exception;

    // 비밀번호 찾기
    String searchPasswordProc(UserInfoDTO pDTO) throws Exception;

    //     비밀번호 재설정
    String newPasswordProc(UserInfoDTO pDTO) throws Exception;
}
