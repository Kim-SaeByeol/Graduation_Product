package poly.graduation_products.service;

import poly.graduation_products.dto.MedicineDTO;

import java.util.List;

public interface IMedicineService {

    List<MedicineDTO> fetchMedicineInfo(MedicineDTO pDTO) throws Exception;
}