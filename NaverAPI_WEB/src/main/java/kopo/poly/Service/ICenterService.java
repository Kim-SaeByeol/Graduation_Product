package kopo.poly.Service;

public interface ICenterService {
    List<CenterDTO> getCenterList(String selectedRegion) throws Exception;

    void insertCenterInfo(CenterDTO centerDTO);

    void updateCenterList(CenterDTO centerDTO);

    void deleteCenterList(CenterDTO centerDTO);

}
