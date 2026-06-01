package pe.edu.utp.animal_gym_api.domain.bill.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BillResponseDTO {
	private Long id;
	private LocalDate issueDate;
	private LocalTime time;
	private Double subTotal;
	private Double totalPrice;
	private Double igv;
	private boolean status;
	private String employeeFirstName;
	private String employeeLastName;
	private String partnerFirstName;
	private String partnerLastName;
	private String membershipName;
}
