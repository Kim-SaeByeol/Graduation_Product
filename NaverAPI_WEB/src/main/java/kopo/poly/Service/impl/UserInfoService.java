package kopo.poly.Service.impl;

import kopo.poly.DTO.UserInfoDTO;
import kopo.poly.Service.IUserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService implements IUserInfoService {
    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public UserInfoDTO getcheckAuthNumber(UserInfoDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {
        return 0;
    }

    @Override
    public List<UserInfoDTO> getUserList() throws Exception {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO, boolean type) throws Exception {
        return null;
    }

    @Override
    public UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public int newPasswordProc(UserInfoDTO pDTO) throws Exception {
        return 0;
    }
}
