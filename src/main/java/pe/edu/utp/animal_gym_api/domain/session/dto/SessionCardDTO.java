package pe.edu.utp.animal_gym_api.domain.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCardDTO {
	private Long id;
	private String name;
	private String image;
	private String status;
	private Integer capacity;
	private Boolean enrolled;
}
