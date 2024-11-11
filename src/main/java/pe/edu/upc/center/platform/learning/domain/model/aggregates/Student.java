package pe.edu.upc.center.platform.learning.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProgramId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "students")
public class Student extends AuditableAbstractAggregateRoot<Student> {

  @Getter
  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "studentCode", column = @Column(name = "code", length = 36, nullable = false))
  })
  private final StudentCode studentCode;

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "profileId", column = @Column(name = "profile_id", nullable = false))
  })
  private ProfileId profileId;

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "programId", column = @Column(name = "program_id", nullable = false))
  })
  private ProgramId programId;

  @Getter
  @Column(name = "start_period", length = 6, nullable = false)
  private String startPeriod;


  //---------------------------------------------------
  public Student() {
    this.studentCode = new StudentCode();
  }

  public Student(Long profileId) {
    this();
    this.profileId = new ProfileId(profileId);
  }

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
