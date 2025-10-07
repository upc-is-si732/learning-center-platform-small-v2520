package pe.edu.upc.center.platform.learning.domain.model.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

/**
 * Represents the enrollment status of a student in a course.
 */
@Entity
@Table(name = "enrollment_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@With
public class EnrollmentStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", length = 22)
  private EnrollmentStatuses name;

  /**
   * Constructs an EnrollmentStatus instance with the specified name.
   *
   * @param name the name of the enrollment status
   */
  public EnrollmentStatus(EnrollmentStatuses name) {
    this.name = name;
  }

  public String getStringName() {
    return name.name();
  }

  public static EnrollmentStatus getDefaultEnrollmentStatus() {
    return new EnrollmentStatus(EnrollmentStatuses.REGULAR);
  }

  /**
   * Converts a string name to an EnrollmentStatus instance.
   *
   * @param name the name of the enrollment status
   * @return the corresponding EnrollmentStatus instance
   * @throws IllegalArgumentException if the name does not match any EnrollmentStatuses
   */
  public static EnrollmentStatus toEnrollmentStatusFromName(String name) {
    return new EnrollmentStatus(EnrollmentStatuses.valueOf(name));
  }

}
