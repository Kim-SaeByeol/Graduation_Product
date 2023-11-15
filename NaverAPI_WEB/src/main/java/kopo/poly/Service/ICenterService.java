package kopo.poly.Service;

import kopo.poly.DTO.CenterDTO;

import java.util.List;

public interface ICenterService {

    //조회
    List<CenterDTO> getCenterList() throws Exception;


    //추가
    void insertCenterInfo(CenterDTO pDTO) throws Exception;

    //수정
    void updateCenterList(CenterDTO centerDTO) throws Exception;

    //삭제
    void deleteCenterList(CenterDTO pDTO) throws Exception;

}
