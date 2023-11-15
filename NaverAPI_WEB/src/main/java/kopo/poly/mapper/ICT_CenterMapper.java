package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICT_CenterMapper {

    //게시판 리스트
    List<CenterDTO> getCT_CenterList() throws Exception;

    //게시판 글 등록
    void insertCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //게시판 상세보기
    CenterDTO getCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //게시판 글 수정
    void updateCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //게시판 글 삭제
    void deleteCT_CenterInfo(CenterDTO pDTO) throws Exception;

}
