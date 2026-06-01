package pe.edu.utp.animal_gym_api.domain.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
