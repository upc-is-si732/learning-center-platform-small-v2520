package pe.edu.upc.center.platform.learning.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
@Table(name = "course_enroll_items")
public class CourseEnrollItem extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "enrollment_id")
  private Enrollment enrollment;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "course_assign_id")
  private CourseAssign courseAssign;

  @Min(1)
  @Max(4)
  @Column(name = "number_of_times", nullable = false)
  private int numberOfTimes;

  public CourseEnrollItem() {
  }

  public CourseEnrollItem(Enrollment enrollment, CourseAssign courseAssign, int numberOfTimes) {
    this.enrollment = enrollment;
    this.courseAssign = courseAssign;
    this.numberOfTimes = numberOfTimes;
  }

}
