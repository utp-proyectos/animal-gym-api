package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

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
	private String image;
	private Boolean status;
	private Integer capacityLimit;
}
