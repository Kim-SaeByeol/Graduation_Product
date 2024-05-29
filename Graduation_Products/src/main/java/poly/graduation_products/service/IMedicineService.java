package poly.graduation_products.service;

import poly.graduation_products.dto.MedicineDTO;

import java.util.List;

public interface IMedicineService {

    /**
     * 전체 검색
     */
    List<MedicineDTO> allSearch() throws Exception;

    /**
     * 의약품 이름으로 검색
     */
    List<MedicineDTO> itemNameSearch(String itemName) throws Exception;


    /**
     * 회사 이름으로 검색
     */
    List<MedicineDTO> entpNameSearch(String entpName) throws Exception;


    /**
     * 품목기준코드로 검색
     */
    List<MedicineDTO> itemSeqSearch(String itemSeq) throws Exception;

    /**
     * 의약품 정보 상세보기
     * 다른 API를 통해 상세정보를 불러옴.
     */
    List<MedicineDTO> detailSearch(String itemSeq) throws Exception;

}