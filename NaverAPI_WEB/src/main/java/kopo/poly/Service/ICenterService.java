package kopo.poly.Service;

import kopo.poly.DTO.CenterDTO;

import java.util.List;

public interface ICenterService {
    List<CenterDTO> getCenterList() throws Exception;

    void insertCenterInfo(CenterDTO centerDTO);

    void updateCenterList(CenterDTO centerDTO);

    void deleteCenterList(CenterDTO centerDTO);

}
