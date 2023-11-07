package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;

import java.util.List;

public interface ICT_CenterMapper {
    List<CenterDTO> getCenterList(String selectedRegion) throws Exception;

    void insertCenterInfo(CenterDTO centerDTO);

    void updateCenterList(CenterDTO centerDTO);

    void deleteCenterList(CenterDTO centerDTO);

}
