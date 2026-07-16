package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipSelfResponseDTO {
	private Long partnerId;
	private Long membershipId;
	private String membershipName;
	private LocalDate expirationDate;
	private Boolean active;
	private Long daysRemaining;
}
