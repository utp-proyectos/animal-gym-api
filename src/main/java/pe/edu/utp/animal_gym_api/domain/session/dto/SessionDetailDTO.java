package pe.edu.utp.animal_gym_api.domain.session.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailDTO {
	private Long id;
	private String name;
	private String description;
	private Integer capacity;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private Integer duration;
	private String status;
	private String goal;
	private String intensity;
	private String image;
	private EmployeeResponseDTO employee;
	private Integer currentBookings;
	private Boolean enrolled;
	private List<SessionParticipantDTO> participants;
}
