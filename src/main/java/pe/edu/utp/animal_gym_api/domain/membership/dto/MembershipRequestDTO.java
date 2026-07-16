package pe.edu.utp.animal_gym_api.domain.membership.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MembershipRequestDTO {
	@NotBlank(message = "El nombre es requerido")
	@Size(max = 70, message = "El nombre no puede superar los 70 caracteres")
	private String name;

	@NotBlank(message = "La descripción es requerida")
	@Size(max = 70, message = "La descripción no puede superar los 70 caracteres")
	private String description;

	@NotNull(message = "La duración es requerida")
	@Positive(message = "La duración debe ser mayor a cero")
	private Integer duration;

	@NotNull(message = "El precio es requerido")
	@Positive(message = "El precio debe ser mayor a cero")
	private Double price;

	@Positive(message = "El precio de oferta debe ser mayor a cero")
	private Double discountPrice;
	private LocalDate offerStartDate;
	private LocalDate offerEndDate;
	private MultipartFile image;

	@NotNull(message = "El estado es requerido")
	private Boolean status;

	@NotNull(message = "El límite de cupos es requerido")
	@Positive(message = "El límite de cupos debe ser mayor a cero")
	private Integer capacityLimit;

	@AssertTrue(message = "El precio de oferta debe ser menor al precio normal")
	public boolean isDiscountPriceValid() {
		return discountPrice == null || price == null || discountPrice < price;
	}

	@AssertTrue(message = "La oferta requiere fecha de inicio y fecha de fin")
	public boolean isOfferDateComplete() {
		return discountPrice == null || (offerStartDate != null && offerEndDate != null);
	}

	@AssertTrue(message = "La fecha de fin de oferta debe ser posterior a la fecha de inicio")
	public boolean isOfferDateRangeValid() {
		return offerStartDate == null || offerEndDate == null || offerEndDate.isAfter(offerStartDate);
	}
}
