package pe.edu.utp.animal_gym_api.domain.membership;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "memberships")
public class Membership {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "membership_id")
	private Long membershipId;

	@Column(name = "name", nullable = false, length = 70)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "duration", nullable = false)
	private Integer duration;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "discount_price")
	private Double discountPrice;

	@Column(name = "offer_start_date")
	private LocalDate offerStartDate;

	@Column(name = "offer_end_date")
	private LocalDate offerEndDate;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "capacity_limit", nullable = false)
	private Integer capacityLimit;

	@Transient
	private Boolean active;

	@Transient
	private Boolean expired;

	@Transient
	private Long remainingDays;

	@Transient
	private Integer enrolledMembers;
}
