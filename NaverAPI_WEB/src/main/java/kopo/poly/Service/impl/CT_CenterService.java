package kopo.poly.service.impl;

import kopo.poly.dto.CenterDTO;
import kopo.poly.mapper.ICenterMapper;
import kopo.poly.service.ICenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CT_CenterService implements ICenterService {

    private final ICenterMapper centerMapper;

    @Override
    public List<CenterDTO> getCenterList(String selectedRegion) throws Exception {
        log.info(this.getClass().getName() + ".getCenterList start!");
        return centerMapper.getCenterList();
    }

    @Transactional
    @Override
    public void insertCenterInfo(CenterDTO pDTO) {
        log.info(this.getClass().getName() + ".InsertCenterInfo start!");

        try {
            centerMapper.insertCenterInfo(pDTO);
        } catch (Exception e) {
            log.error("Error occurred while inserting center info", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCenterList(CenterDTO centerDTO) {

    }

    @Override
    public void deleteCenterList(CenterDTO centerDTO) {

    }
}