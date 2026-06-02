package pe.edu.utp.animal_gym_api.domain.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionParticipantDTO {
	private Long id;
	private String dni;
	private String firstName;
	private String lastName;
}
