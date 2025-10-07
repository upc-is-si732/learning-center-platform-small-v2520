package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProgramId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents a Student entity in the learning platform.
 */
@Entity
@Table(name = "students")
public class Student extends AuditableAbstractAggregateRoot<Student> {

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "studentCode",
          column = @Column(name = "code", length = 36, nullable = false))
  })
  private final StudentCode studentCode;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "profileId",
          column = @Column(name = "profile_id", nullable = false))
  })
  private ProfileId profileId;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "programId",
          column = @Column(name = "program_id", nullable = false))
  })
  private ProgramId programId;

  @Getter
  @Column(name = "start_period", length = 6, nullable = false)
  private String startPeriod;

  /**
   * Default constructor for JPA.
   */
  public Student() {
    this.studentCode = new StudentCode();
  }

  /**
   * Constructs a Student instance with a specified profile ID.
   *
   * @param profileId The profile ID associated with the student.
   */
  public Student(Long profileId) {
    this();
    this.profileId = new ProfileId(profileId);
  }

  /**
   * Constructs a Student instance with specified profile ID, program ID, and start period.
   *
   * @param profileId The profile ID associated with the student.
   * @param programId The program ID the student is enrolled in.
   * @param startPeriod The period when the student started.
   */
  public Student(ProfileId profileId, Long programId, String startPeriod) {
    this();
    this.profileId = profileId;
    this.programId = new ProgramId(programId);
    this.startPeriod = startPeriod;
  }

  public Long getProfileId() {
    return this.profileId.profileId();
  }

  public Long getProgramId() {
    return this.programId.programId();
  }
}
