package poly.graduation_products.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.dto.CKEditorBoardDTO;
import poly.graduation_products.repository.CKEditorBoardRepository;
import poly.graduation_products.repository.entity.CKEditorBoardEntity;
import poly.graduation_products.service.ICKEditorBoardService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CKEditorBoardService implements ICKEditorBoardService {

    private final CKEditorBoardRepository boardRepository;

    @Override
    public String searchTitle(String title) throws Exception {

        log.info(this.getClass().getName() + "제목으로 검색하기 실행");

        String res = "";
        log.info("title : " + title);

        Optional<CKEditorBoardEntity> rEntity = boardRepository.findByTitle(title);

        if (rEntity.isPresent()) {
            res = "Y";     // 조회된 값이 있음

        } else {
            res = "N";     // 조회된 값이 없음

        }

        log.info(this.getClass().getName() + "제목으로 검색하기 종료");

        return res;
    }

    @Override   //수정해야함
    public Optional<CKEditorBoardEntity> searchContents(String contents) {
        log.info(this.getClass().getName() + "내용으로 검색하기 실행");

        String res = "";
        log.info("contents : " + contents);

        Optional<CKEditorBoardEntity> rEntity = boardRepository.findByContents(contents);

        if (rEntity.isPresent()) {
            res = "Y";     // 조회된 값이 있음

        } else {
            res = "N";     // 조회된 값이 없음

        }

        log.info(this.getClass().getName() + "내용으로 검색하기 종료");

        return rEntity;
    }

    @Override
    public String searchRegId(String regId) throws Exception {
        return null;
    }

    @Override
    public List<CKEditorBoardDTO> getBoardList(String category) throws Exception {
        return null;
    }

    @Override
    public int insertBoard(CKEditorBoardDTO pDTO) throws Exception {
        return 0;
    }

    @Override
    public int updateBoard(CKEditorBoardDTO pDTO) throws Exception {
        return 0;
    }

    @Override
    public int deleteBoard(CKEditorBoardDTO pDTO) throws Exception {
        return 0;
    }

    @Override
    public CKEditorBoardDTO getBoardInfo(CKEditorBoardDTO pDTO) throws Exception {
        return null;
    }
}
