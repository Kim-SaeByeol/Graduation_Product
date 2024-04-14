package service;

import dto.UserInfoDTO;

import java.util.List;

public interface IUserInfoService {

    /**
     * 회원가입
     * @param pDTO  회원 가입을 위한 정보
     * @return      회원가입 결과
     */
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;


}
