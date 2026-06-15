package pe.edu.utp.animal_gym_api.domain.person;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;

@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Partner.class, name = "partner"),
		@JsonSubTypes.Type(value = Employee.class, name = "employee") })
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "persons")

public abstract class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "dni", unique = true, nullable = false, length = 8)
	protected String dni;

	@Column(name = "first_name", nullable = false, length = 100)
	protected String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	protected String lastName;

	@Column(name = "phone_number", unique = true, nullable = false, length = 9)
	protected String phoneNumber;

	@Column(name = "gender", nullable = false, length = 20)
	protected String gender;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	protected String email;

	@Column(name = "birth_date", nullable = false)
	protected LocalDate birthDate;

	@Column(name = "hire_date", nullable = false)
	protected LocalDate hireDate;

	@Column(name = "avatar", nullable = false)
	protected String avatar;

	@Enumerated(EnumType.STRING)
	@Column(name = "rol", nullable = false)
	private Role role;
}
