package kopo.poly.Service.impl;


import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.mapper.ICT_CenterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CT_CenterService implements ICenterService {

    private final ICT_CenterMapper centerMapper;


    @Override
    public List<CenterDTO> getCenterList() throws Exception {
        log.info(this.getClass().getName() + ".getCenterList start!");

        List<CenterDTO> resultList = centerMapper.getCT_CenterList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        return centerMapper.getCT_CenterList();
    }

    @Transactional
    @Override
    public void insertCenterInfo(CenterDTO centerDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertCenterInfo Start!");
        centerMapper.insertCT_CenterInfo(centerDTO);
        log.info(this.getClass().getName() + ".insertCenterInfo End!");
    }

    @Transactional
    @Override
    public void updateCenterList(CenterDTO centerDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateCenterList start!");
        centerMapper.updateCT_CenterInfo(centerDTO);
        log.info(this.getClass().getName() + ".updateCenterList End!");
    }



    @Transactional
    @Override
    public void deleteCenterList(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteCenterList start!");
        centerMapper.deleteCT_CenterInfo(pDTO);
        log.info(this.getClass().getName() + ".deleteCenterList End!");
    }
}