package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseEnroll;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents an Enrollment aggregate root in the learning platform domain.
 * An Enrollment is associated with a student, a specific period, and has a status.
 * It contains a collection of CourseEnrollItems representing the courses the student is
 *   enrolled in.
 */
@Getter
@Entity
@Table(name = "enrollments")
@ToString
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

  /**
   * Default constructor for JPA.
   */
  public Enrollment() {
    this.courseEnroll = new CourseEnroll();
  }

  /**
   * Constructs an Enrollment instance with specified student code and period.
   * The enrollment status is set to the default status.
   *
   * @param studentCode The student's code.
   * @param period The enrollment period.
   */
  public Enrollment(StudentCode studentCode, String period) {
    this();
    this.studentCode = studentCode;
    this.period = period;
    this.status = EnrollmentStatus.getDefaultEnrollmentStatus();
  }

  /**
   * Constructs an Enrollment instance with specified details.
   *
   * @param studentCode The student's code.
   * @param period The enrollment period.
   * @param status The enrollment status.
   */
  public Enrollment(StudentCode studentCode, String period, EnrollmentStatus status) {
    this();
    this.studentCode = studentCode;
    this.period = period;
    this.status = status;
  }

  /**
   * Constructs an Enrollment instance from a CreateEnrollmentCommand.
   *
   * @param command The command containing enrollment details.
   */
  public Enrollment(CreateEnrollmentCommand command) {
    this();
    this.studentCode = command.studentCode();
    this.period = command.period();
    this.status = EnrollmentStatus.toEnrollmentStatusFromName(command.status());
  }

  /**
   * Add a CourseEnrollItem to the Enrollment.
   *
   * @param courseAssign The CourseAssign to be added.
   * @param numberOfTimes Number of times the course is assigned.
   */
  public void addCourseEnrollItem(CourseAssign courseAssign, int numberOfTimes) {
    this.courseEnroll.addItem(this, courseAssign, numberOfTimes);
  }

  /**
   * Add multiple CourseEnrollItems to the Enrollment.
   *
   * @param courseEnrollItems List of CourseEnrollItem to be added.
   */
  public void addCourseEnrollItems(List<CourseEnrollItem> courseEnrollItems) {
    this.courseEnroll.addItems(courseEnrollItems);
  }

}
