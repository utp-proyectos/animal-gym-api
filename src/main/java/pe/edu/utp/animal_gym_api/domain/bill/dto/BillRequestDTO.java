package pe.edu.utp.animal_gym_api.domain.bill.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BillRequestDTO {
	private LocalDate issueDate;
	private LocalTime time;
	private Double subTotal;
	private Double totalPrice;
	private Double igv;
	private boolean status;
	private Long partnerId;
	private Long employeeId;
}
