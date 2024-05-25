package poly.graduation_products.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.graduation_products.repositoty.entity.GoogleLogin;

import java.util.Optional;

@Repository
public interface GoogleRepository extends JpaRepository<GoogleLogin, Long>{

    Optional<GoogleLogin> findByEmail(String email);
}
