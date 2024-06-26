package poly.graduation_products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.NoticeEntity;
import poly.graduation_products.repository.entity.UserInfoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    /**
     * 공지사항 리스트
     */
    List<NoticeEntity> findAllByOrderByNoticeSeqDesc();

    /**
     * 공지사항 리스트
     *
     * @param noticeSeq 공지사항 PK
     */
    NoticeEntity findByNoticeSeq(Long noticeSeq);

    /**
     * 공지사항 상세 보기할 때, 조회수 증가하기
     *
     * @param noticeSeq 공지사항 PK
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE NOTICE A SET A.READ_CNT = IFNULL(A.READ_CNT, 0) + 1 WHERE A.NOTICE_SEQ = ?1",
            nativeQuery = true)
    int updateReadCnt(Long noticeSeq);



}
