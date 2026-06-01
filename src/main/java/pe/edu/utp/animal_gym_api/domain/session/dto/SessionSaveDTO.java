package pe.edu.utp.animal_gym_api.domain.session.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionSaveDTO {
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
	private Long employeeId;
}
