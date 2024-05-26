package poly.graduation_products.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repositoty.entity.SocialLoginEntity;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLoginEntity, Long>{

    Optional<SocialLoginEntity> findByEmail(String email);
}
