package pe.edu.utp.animal_gym_api.domain.sessionBooking;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.session.Session;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "session_bookings")
public class SessionBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Partner partner;

	@ManyToOne
	@JoinColumn(name = "session_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ToString.Exclude
	@JsonIgnore
	private Session session;
}
