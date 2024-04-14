package repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositoty.entity.UserInfoEntity;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
// JpaRepository<UserInfoEntity, Long> 에서 왜 Long ?
// @Id 인 키 값이 Long 이기 때문.

    /*
     아이디 중복 체크
      SELECT *
      FROM USER_INFO
      WHERE USER_ID = '입력값'
     */
    Optional<UserInfoEntity> findByUserId(String userId);

    /*
    이메일 인증 및 이메일 중복 체크
     SELECT *
     FROM USER_INFO
     WHERE USER_EMAIL = '입력값'
   */
    Optional<UserInfoEntity> findByUserEmail(String email);

    /*
     별명 중복체크
     SELECT *
     FROM USER_INFO
     WHERE USER_NICKNAME = '입력값'
     */
    Optional<UserInfoEntity> findByUserNickname(String nickname);


    /*
    로그인
      SELECT *
      FROM USER_INFO
      WHERE USER_ID = '입력값' AND USER_PASSWORD = '입력값'
     */
    Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String pwd);


    /*
     아이디 찾기
     SELECT *
     FROM USER_INFO
     WHERE USER_NAME = '입력값'
     아이디 찾기는 이름, 이메일 인증값이 필요함
     */
    Optional<UserInfoEntity> findByUserName(String name);

    /*
     비밀번호 찾기
     SELECT *
     FROM USER_INFO
     WHERE USER_ID = '입력값' AND '' USER_NAME = '입력값'
     비밀번호 찾기는 이름, 아이디, 이메일 인증값이 필요함
     */
    Optional<UserInfoEntity> findByUserIdAndUserName(String userId, String name);


}
