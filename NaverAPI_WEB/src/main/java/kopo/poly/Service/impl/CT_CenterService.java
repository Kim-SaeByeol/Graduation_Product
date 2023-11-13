package kopo.poly.Service.impl;


import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.mapper.ICT_CenterMapper;
import kopo.poly.mapper.ICenterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CT_CenterService implements ICenterService {

    private final ICT_CenterMapper centerMapper;

    @Override
    public List<CenterDTO> findAllPost(CenterDTO params) throws Exception {
        log.info(this.getClass().getName() + ".findAllPost start!");
        return centerMapper.findAll(params);
    }

    @Transactional
    @Override
    public void insertCenterInfo(CenterDTO pDTO) {
        log.info(this.getClass().getName() + ".InsertCenterInfo start!");
        centerMapper.insertCenterInfo(pDTO);
    }

    @Override
    public void updateCenterList(CenterDTO pDTO) {

        log.info(this.getClass().getName() + ".updateCenterList start!");

        centerMapper.updateCenterList(pDTO);
    }

    @Override
    public void deleteCenterList(CenterDTO pDTO) {

        log.info(this.getClass().getName() + ".deleteNoticeInfo start!");

        centerMapper.deleteCenterList(pDTO);
    }
}