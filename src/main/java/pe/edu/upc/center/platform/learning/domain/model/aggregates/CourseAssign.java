package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.*;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ClassroomId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfessorId;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "course_assigns")
public class CourseAssign extends AuditableAbstractAggregateRoot<CourseAssign> {

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "courseId", column = @Column(name = "course_id", length = 5, nullable = false))
  })
  private CourseId courseId;

  @Column(name = "section", length = 4, nullable = false)
  private String section;

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "professorId", column = @Column(name = "professor_id", nullable = false))
  })
  private ProfessorId professorId;

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "classroomId", column = @Column(name = "classroom_id", length = 5, nullable = false))
  })
  private ClassroomId classroomId;

}
