package pe.edu.utp.animal_gym_api.domain.membership.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponseDTO {
	private Long id;
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

	private Boolean active;
	private Boolean expired;
	private Long remainingDays;
	private Integer enrolledMembers;
}
