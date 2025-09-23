package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ClassroomId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfessorId;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * CourseAssign aggregate root.
 * This class is used to represent a CourseAssign.
 */
@Entity
@Table(name = "course_assigns")
public class CourseAssign extends AuditableAbstractAggregateRoot<CourseAssign> {

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "courseId",
          column = @Column(name = "course_id", length = 5, nullable = false))
  })
  private CourseId courseId;

  @Getter
  @Column(name = "section", length = 4, nullable = false)
  private String section;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "professorId",
          column = @Column(name = "professor_id", nullable = false))
  })
  private ProfessorId professorId;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "classroomId", column
          = @Column(name = "classroom_id", length = 5, nullable = false))
  })
  private ClassroomId classroomId;

  /**
   * Default constructor for JPA.
   */
  public CourseAssign() {
  }

  /**
   * Constructor for CourseAssign.
   *
   * @param courseId    the courseId
   * @param section     the section
   * @param professorId the professorId
   * @param classroomId the classroomId
   */
  public CourseAssign(String courseId, String section, Long professorId, String classroomId) {
    this.courseId = new CourseId(courseId);
    this.section = section;
    this.professorId = new ProfessorId(professorId);
    this.classroomId = new ClassroomId(classroomId);
  }

  /**
   * Constructor for CourseAssign.
   *
   * @param command the CreateCourseAssignCommand
   */
  public CourseAssign(CreateCourseAssignCommand command) {
    this.courseId = new CourseId(command.courseId());
    this.section = command.section();
    this.professorId = new ProfessorId(command.professorId());
    this.classroomId = new ClassroomId(command.classroomId());
  }

  public String getCourseId() {
    return courseId.courseId();
  }

  public Long getProfessorId() {
    return professorId.professorId();
  }

  public String getClassroomId() {
    return classroomId.classroomId();
  }

}
