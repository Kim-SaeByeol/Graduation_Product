package poly.graduation_products.dto;

import lombok.Getter;
import poly.graduation_products.repositoty.entity.GoogleLogin;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(GoogleLogin user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
