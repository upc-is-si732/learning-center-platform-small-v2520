package pe.edu.upc.center.platform.learning.domain.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

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
  @Column(length = 22)
  private EnrollmentStatuses name;

  public EnrollmentStatus(EnrollmentStatuses name) {
    this.name = name;
  }

  public String getStringName() {
    return name.name();
  }

  public static EnrollmentStatus getDefaultEnrollmentStatus() {
    return new EnrollmentStatus(EnrollmentStatuses.REGULAR);
  }

  public static EnrollmentStatus toEnrollmentStatusFromName(String name) {
    return new EnrollmentStatus(EnrollmentStatuses.valueOf(name));
  }

}
