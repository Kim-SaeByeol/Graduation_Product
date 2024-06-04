package poly.graduation_products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repository.entity.SocialInfoEntity;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialInfoEntity, Long>{

}
