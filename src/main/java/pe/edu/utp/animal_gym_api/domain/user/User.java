package pe.edu.utp.animal_gym_api.domain.user;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.person.Person;

@Entity
@Data
@Table(name = "users")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "rol", nullable = false)
	private Role role;

	@OneToOne
	@JoinColumn(name = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person person;
}