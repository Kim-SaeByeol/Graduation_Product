package kopo.poly.mapper;

import kopo.poly.DTO.CenterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ICT_CenterMapper {

    //센터 리스트
    List<CenterDTO> getCT_CenterList() throws Exception;

    //센터 정보 등록
    void insertCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //센터 상세보기
    CenterDTO getCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //센터 정보 수정
    void updateCT_CenterInfo(CenterDTO pDTO) throws Exception;

    //센터 정보 삭제
    void deleteCT_CenterInfo(CenterDTO pDTO) throws Exception;


    void insertGeocoding(CenterDTO pDTO) throws Exception;

    void updateGeocoding(CenterDTO pDTO) throws Exception;



    // 검색 기능
////    List<CenterDTO> searchCenterAll(Map<String, Object> paramMap);
//
//    List<CenterDTO> searchCenterBySido(String isSido);
//
//    List<CenterDTO> searchCenterByAddress(String centerAddress);

    List<CenterDTO> searchCenter_all(String isSido, String centerAddress);

    List<CenterDTO> searchCenter_sido(String isSido);

    List<CenterDTO> searchCenter_address(String centerAddress);
}
