package poly.graduation_products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.CKEditorBoardEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CKEditorBoardRepository extends JpaRepository<CKEditorBoardEntity, Long> {

    /**
     * 제목 검색기능
     */
    Optional<CKEditorBoardEntity> findByTitle(String title);

    /**
     * 내용 검색기능
     */
    Optional<CKEditorBoardEntity> findByContents(String contents);

    /**
     * 작성자 검색기능
     */
    Optional<CKEditorBoardEntity> findByRegId(String regId);


    /**
     * 게시글 조회
     */
    Optional<CKEditorBoardEntity> findByCategory(String category);

}
