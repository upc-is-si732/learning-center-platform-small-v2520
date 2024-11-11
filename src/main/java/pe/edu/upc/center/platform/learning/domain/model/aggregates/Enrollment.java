package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseEnroll;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "enrollments")
public class Enrollment extends AuditableAbstractAggregateRoot<Enrollment> {

  @Embedded
  private StudentCode studentCode;

  @Column(name = "period", length = 6, nullable = false)
  private String period;

  @ManyToOne(fetch = FetchType.EAGER) // Foreign Key
  @JoinColumn(name = "status_id", nullable = false)
  private EnrollmentStatus status;

  @Embedded
  private final CourseEnroll courseEnroll;

  public Enrollment() {
    this.courseEnroll = new CourseEnroll();
  }

  public Enrollment(StudentCode studentCode, String period) {
    this();
    this.studentCode = studentCode;
    this.period = period;
    this.status = EnrollmentStatus.getDefaultEnrollmentStatus();
  }
  public Enrollment(StudentCode studentCode, String period, EnrollmentStatus status) {
    this();
    this.studentCode = studentCode;
    this.period = period;
    this.status = status;
  }

}
