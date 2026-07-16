package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPurchaseResponseDTO {
	private Long billId;
	private String operationType;
	private Long partnerId;
	private String partnerName;
	private Long membershipId;
	private String membershipName;
	private LocalDate previousExpirationDate;
	private LocalDate newExpirationDate;
	private Double selectedPrice;
	private Double remainingCredit;
	private Double totalPrice;
	private Double refundAmount;
	private Double subTotal;
	private Double igv;
	private Boolean discountApplied;
	private String paymentMethod;
	private String paymentStatus;
}
