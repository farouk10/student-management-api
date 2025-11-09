package hightech.edu.inscription.dto;

import lombok.Data;

@Data
public class EtudiantResponse {

    private Long id;
    private String nomComplet;
    private String email;
    private Integer age;

}
