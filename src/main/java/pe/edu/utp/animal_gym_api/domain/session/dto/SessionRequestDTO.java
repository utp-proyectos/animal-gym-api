package pe.edu.utp.animal_gym_api.domain.session.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDTO {
	private String name;
	private String description;
	private int capacity;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int duration;
	private String status;
	private String goal;
	private String intensity;
	private MultipartFile image;
	private Long employeeId;
}
