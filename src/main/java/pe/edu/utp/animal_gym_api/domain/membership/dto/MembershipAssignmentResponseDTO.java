package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipAssignmentResponseDTO {
	private Long billId;
	private String operationType;
	private Long partnerId;
	private String partnerDni;
	private String partnerName;
	private Long membershipId;
	private String membershipName;
	private LocalDate previousExpirationDate;
	private LocalDate newExpirationDate;
	private Double originalPrice;
	private Double selectedPrice;
	private Long remainingDays;
	private Double remainingCredit;
	private Double totalPrice;
	private Double refundAmount;
	private Double subTotal;
	private Double igv;
	private Boolean discountApplied;
	private String paymentMethod;
	private String paymentStatus;
}
