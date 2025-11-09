package hightech.edu.inscription.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String roles; // optional: "ROLE_USER" or "ROLE_USER,ROLE_ADMIN"
}
