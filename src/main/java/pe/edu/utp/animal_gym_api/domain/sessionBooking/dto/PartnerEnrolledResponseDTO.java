package pe.edu.utp.animal_gym_api.domain.sessionBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerEnrolledResponseDTO {
	private Long bookingId;
	private Long partnerId;
	private String dni;
	private String firstName;
	private String lastName;
	private LocalDate enrollmentDate;
}
