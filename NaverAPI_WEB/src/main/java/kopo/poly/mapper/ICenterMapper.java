package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICenterMapper {
    List<CenterDTO> getCenterList() throws Exception;

    void insertCenterInfo(CenterDTO centerDTO);

    void updateCenterList(CenterDTO centerDTO);

    void deleteCenterList(CenterDTO centerDTO);

}
