package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICT_CenterMapper {

    /**
     * 센터 리스트 조회
     * @return 게시글 리스트
     */
    List<CenterDTO> findAll(CenterDTO params);

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(CenterDTO params);

    // 센터 정보 등록
    void insertCenterInfo(CenterDTO centerDTO);

    // 센터 정보 수정
    void updateCenterList(CenterDTO centerDTO);

    // 센터 정보 삭제
    void deleteCenterList(CenterDTO centerDTO);

}
