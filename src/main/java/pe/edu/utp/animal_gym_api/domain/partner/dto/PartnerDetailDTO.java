package pe.edu.utp.animal_gym_api.domain.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDetailDTO {

    private Long id;
    private String dni;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String email;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private LocalDate expirationDate;
    private Boolean status;
    private Integer points;
    private Double weight;
    private Double height;
    private String image;
    private Long membershipId;
    private String membershipName;
    private List<RoutineResponseDTO> routines;
}
