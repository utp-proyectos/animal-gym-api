package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MembershipRequestDTO {
	private String name;
	private String description;
	private Integer duration;
	private Double price;
	private Double discountPrice;
	private LocalDate offerStartDate;
	private LocalDate offerEndDate;
	private MultipartFile image;
	private Boolean status;
	private Integer capacityLimit;
}
