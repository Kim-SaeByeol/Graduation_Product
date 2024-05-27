package poly.graduation_products.service;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import poly.graduation_products.dto.CKEditorBoardDTO;
import poly.graduation_products.repository.entity.CKEditorBoardEntity;

import java.util.List;
import java.util.Optional;

public interface ICKEditorBoardService {

    // 제목 검색기능
    String searchTitle(final String title) throws Exception;

    // 내용 검색기능
    @Query(value = "SELECT * FROM BOARD_INFO WHERE CONTENTS LIKE %:contents%", nativeQuery = true)
    Optional<CKEditorBoardEntity> searchContents(@Param("contents") String contents);


    // 작성자 검색기능
    String searchRegId(final String regId) throws Exception;

    // 게시글 전체 조회
    List<CKEditorBoardDTO> getBoardList(final String category) throws Exception;

    // 게시글 저장
    int insertBoard(CKEditorBoardDTO pDTO) throws Exception;

    // 게시글 수정
    int updateBoard(CKEditorBoardDTO pDTO) throws Exception;

    // 게시글 삭제
    int deleteBoard(CKEditorBoardDTO pDTO) throws Exception;

    // 게시글 상세보기
    CKEditorBoardDTO getBoardInfo(CKEditorBoardDTO pDTO) throws Exception;
}
