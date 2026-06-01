package pe.edu.utp.animal_gym_api.domain.routine;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "routine_details")
public class RoutineDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "day_of_week", length = 20, nullable = false)
	private String dayOfWeek;

	@Column(name = "sets", nullable = false)
	private Integer sets;

	@Column(name = "reps", nullable = false)
	private Integer reps;

	@Column(name = "weight", nullable = false)
	private double weight;

	@Column(name = "calories", nullable = false)
	private Integer calories;

	@Column(name = "rest_time", nullable = false)
	private Integer restTime;

	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise;

}
