package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;
import kopo.poly.DTO.SearchListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICenterMapper {
    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    List<CenterDTO> findAll(CenterDTO params);

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(CenterDTO params);
    void insertCenterInfo(CenterDTO centerDTO);

    void updateCenterList(CenterDTO centerDTO);

    void deleteCenterList(CenterDTO centerDTO);

}
