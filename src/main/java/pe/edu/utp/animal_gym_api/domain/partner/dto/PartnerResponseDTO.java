package pe.edu.utp.animal_gym_api.domain.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponseDTO {
    private Long id;
    private String dni;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String image;
    private Boolean status;
    private LocalDate hireDate;
    private Long membershipId;
    private String membershipName;
}
