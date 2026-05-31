package pe.edu.utp.animal_gym_api.domain.person;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Inheritance(strategy = InheritanceType.JOINED)
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
// @JsonSubTypes({
// 		@JsonSubTypes.Type(value = Socio.class, name = "socio"),
// 		@JsonSubTypes.Type(value = Empleado.class, name = "empleado") })
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "persons")

public abstract class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "persona_id")
	protected Integer personaId;

	@Column(name = "dni", unique = true, nullable = false, length = 8)
	protected String dni;

	@Column(name = "nombre", nullable = false, length = 20)
	protected String nombre;

	@Column(name = "apellido", nullable = false, length = 30)
	protected String apellido;

	@Column(name = "telefono", unique = true, nullable = false, length = 9)
	protected String telefono;

	@Column(name = "genero", nullable = false, length = 20)
	protected String genero;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	protected String email;

	@Column(name = "fecha_nacimiento", nullable = false)
	protected LocalDate fechaNacimiento;

	@Column(name = "fecha_ingreso", nullable = false)
	protected LocalDate fechaIngreso;
}
