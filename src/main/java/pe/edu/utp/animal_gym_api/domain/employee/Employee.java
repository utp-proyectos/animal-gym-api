package pe.edu.utp.animal_gym_api.domain.employee;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.animal_gym_api.domain.person.Person;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "employees")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Employee extends Person {

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "salary", nullable = false)
	private Double salary;

	@Column(name = "contract_type", nullable = false, length = 50)
	private String contractType;

	@Column(name = "specialty", length = 60)
	private String specialty;

}
