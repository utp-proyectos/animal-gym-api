package pe.edu.utp.animal_gym_api.domain.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCardDTO {
	private Long id;
	private String name;
	private String description;
	private String goal;
	private Integer capacity;
	private String intensity;
	private EmployeeResponseDTO employee;
	private String date;
	private String startTime;
	private String endTime;
	private String image;
	private String status;
	private Boolean enrolled;
}
