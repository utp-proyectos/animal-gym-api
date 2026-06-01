package pe.edu.utp.animal_gym_api.domain.routine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.routineDetail.RoutineDetail;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "routines")
public class Routine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "goal", nullable = false)
	private String goal;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "routine_id")
	private List<RoutineDetail> routineDetails = new ArrayList<>();

}
