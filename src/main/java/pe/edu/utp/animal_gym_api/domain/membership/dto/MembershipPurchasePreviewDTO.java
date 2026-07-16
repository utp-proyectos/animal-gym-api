package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPurchasePreviewDTO {
	private String operationType;
	private Boolean allowed;
	private String message;
	private Long currentMembershipId;
	private String currentMembershipName;
	private LocalDate currentExpirationDate;
	private Long remainingDays;
	private Double remainingCredit;
	private Long selectedMembershipId;
	private String selectedMembershipName;
	private Integer selectedDuration;
	private Double originalPrice;
	private Double selectedPrice;
	private Boolean discountApplied;
	private Double amountToPay;
	private Double refundAmount;
	private LocalDate newExpirationDate;
}
