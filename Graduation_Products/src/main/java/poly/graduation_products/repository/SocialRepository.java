package poly.graduation_products.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.Provider;
import poly.graduation_products.repository.entity.SocialInfoEntity;

import java.util.Optional;

@Repository
public interface SocialRepository extends JpaRepository<SocialInfoEntity, Long> {

    // 이메일과 플랫폼을 통해 찾기
    Optional<SocialInfoEntity> findBySocialEmailAndProvider(String email, Provider provider);

}
