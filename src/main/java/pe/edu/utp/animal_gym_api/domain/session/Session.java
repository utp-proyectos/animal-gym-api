package pe.edu.utp.animal_gym_api.domain.session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "sessions")
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "capacity", nullable = false)
	private Integer capacity;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "duration", nullable = false)
	private Integer duration;

	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@Column(name = "goal")
	private String goal;

	@Column(name = "intensity", nullable = false, length = 20)
	private String intensity;

	@Column(name = "image")
	private String image;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Employee employee;

	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SessionBooking> bookings = new ArrayList<>();

	@Transient
	private Boolean enrolled;

}
