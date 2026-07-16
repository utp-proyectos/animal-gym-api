package pe.edu.utp.animal_gym_api.domain.bill;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "bills")
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

	@Column(name = "partner_dni", length = 8)
	private String partnerDni;

	@Column(name = "partner_first_name", length = 100)
	private String partnerFirstName;

	@Column(name = "partner_last_name", length = 100)
	private String partnerLastName;

	@Column(name = "employee_dni", length = 8)
	private String employeeDni;

	@Column(name = "employee_first_name", length = 100)
	private String employeeFirstName;

	@Column(name = "employee_last_name", length = 100)
	private String employeeLastName;

	@Column(name = "membership_name", length = 100)
	private String membershipName;
}
