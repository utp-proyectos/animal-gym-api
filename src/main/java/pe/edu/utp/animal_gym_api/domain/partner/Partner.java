package pe.edu.utp.animal_gym_api.domain.partner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.person.Person;
import pe.edu.utp.animal_gym_api.domain.routine.Routine;

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
