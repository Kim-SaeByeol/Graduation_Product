package poly.graduation_products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.UserInfoEntity;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
        // 아이디 중복체크
        Optional<UserInfoEntity> findByUserId(String userId);

        // 이메일 찾기 (중복체크)
        Optional<UserInfoEntity> findByEmail(String userEmail);

        //별명 찾기 (중복체크)
        Optional<UserInfoEntity> findByNickname(String nickName);

        // 로그인 (아이디, 비밀번호)
        Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String password);

        // 아이디 찾기(별명, 이메일)
        Optional<UserInfoEntity> findByNicknameAndEmail(String nickName, String userEmail);

        // 비밀번호 찾기(아이디, 별명, 이메일)
        Optional<UserInfoEntity> findByUserIdAndNicknameAndEmail(String userId, String nickName, String email);

}
