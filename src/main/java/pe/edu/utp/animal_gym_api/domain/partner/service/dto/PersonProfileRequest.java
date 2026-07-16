package pe.edu.utp.animal_gym_api.domain.partner.service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonProfileRequest {

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNumber;

	private String gender;

	private LocalDate birthDate;

}
