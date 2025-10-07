package pe.edu.upc.center.platform.learning.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.shared.domain.model.entities.AuditableModel;

/**
 * Entity representing an item in a course enrollment.
 */
@Getter
@Entity
@Table(name = "course_enroll_items")
public class CourseEnrollItem extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "enrollment_id") // foreign key
  private Enrollment enrollment;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "course_assign_id")
  private CourseAssign courseAssign;

  @Min(1)
  @Max(4)
  @Column(name = "number_of_times", nullable = false)
  private int numberOfTimes;

  /**
   * Default constructor for JPA.
   */
  public CourseEnrollItem() {
  }

  /**
   * Constructs a CourseEnrollItem instance.
   *
   * @param enrollment    the associated Enrollment
   * @param courseAssign  the associated CourseAssign
   * @param numberOfTimes the number of times the course is enrolled
   */
  public CourseEnrollItem(Enrollment enrollment, CourseAssign courseAssign, int numberOfTimes) {
    this.enrollment = enrollment;
    this.courseAssign = courseAssign;
    this.numberOfTimes = numberOfTimes;
  }

}
