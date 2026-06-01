package pe.edu.utp.animal_gym_api.domain.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {
	private String dni;
	private String name;
	private String lastName;
	private String phone;
	private String gender;
	private String email;
	private LocalDate birthDate;
	private LocalDate joinDate;
	private LocalDate expirationDate;
	private Boolean status;
	private Integer points;
	private Double weight;
	private Double height;
	private String image;
	private Long membershipId;
}
