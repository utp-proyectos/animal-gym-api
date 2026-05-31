package pe.edu.utp.animal_gym_api.domain.bill;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "issue_date", nullable = false)
	private LocalDate issueDate;

	@Column(name = "time", nullable = false)
	private LocalTime time;

	@Column(name = "sub_total", nullable = false)
	private Double subTotal;

	@Column(name = "total_price", nullable = false)
	private Double totalPrice;

	@Column(name = "igv", nullable = false)
	private Double igv;

	@Column(name = "status", nullable = false)
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Partner partner;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Employee employee;

}