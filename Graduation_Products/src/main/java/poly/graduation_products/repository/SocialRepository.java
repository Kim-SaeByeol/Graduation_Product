package poly.graduation_products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.SocialInfoEntity;
import poly.graduation_products.repository.entity.UserInfoEntity;

import java.util.Optional;

@Repository
public interface SocialRepository extends JpaRepository<SocialInfoEntity, Long>{

    //accessToken 을 통해 찾기
    Optional<SocialInfoEntity> findByEmailAndProvider(String email, String provider);

}
