package poly.graduation_products.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repositoty.entity.UserInfoEntity;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
        // 아이디 중복체크
        Optional<UserInfoEntity> findByUserId(String userId);

        // 이메일 찾기 (중복체크)
        Optional<UserInfoEntity> findByEmail(String email);

        //별명 찾기 (중복체크)
        Optional<UserInfoEntity> findByNickname(String nickName);

        // 아이디와 비밀번호 찾기 => 로그인
        Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String password);

        // 아이디 찾기
        Optional<UserInfoEntity> findByUserNameAndEmail(String email, String userName);

        // 비밀번호 찾기
        Optional<UserInfoEntity> findByUserIdAndUserNameAndEmail(String userId, String userName, String email);


        void deleteByUserId(String userId);


}
