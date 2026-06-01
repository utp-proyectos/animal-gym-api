package pe.edu.utp.animal_gym_api.domain.partner;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.animal_gym_api.domain.person.Person;

@Entity
@Table(name = "partners")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Partner extends Person {

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "points")
	private int points;

	@Column(name = "weight")
	private double weight;

	@Column(name = "height")
	private double height;

	@Column(name = "image")
	private String image;

	@ManyToOne
	@JoinColumn(name = "membership_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Membership membership;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "partner_id")
	private List<Routine> routines = new ArrayList<>();
}
