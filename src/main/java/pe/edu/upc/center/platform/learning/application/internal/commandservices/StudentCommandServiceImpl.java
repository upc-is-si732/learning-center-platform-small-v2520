package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;

/**
 * Implementation of the StudentCommandService interface for handling student-related commands.
 *
 * <p>This service provides methods to create, update, and delete student entities, interacting
 * with both the StudentRepository and an external profile service.</p>
 */
@Service
public class StudentCommandServiceImpl implements StudentCommandService {

  private final StudentRepository studentRepository;
  private final ExternalProfileService externalProfileService;

  /**
   * Constructs a StudentCommandServiceImpl with the specified dependencies.
   *
   * @param studentRepository the repository for managing Student entities
   * @param externalProfileService the external service for managing profiles
   */
  public StudentCommandServiceImpl(StudentRepository studentRepository,
                                   ExternalProfileService externalProfileService) {
    this.studentRepository = studentRepository;
    this.externalProfileService = externalProfileService;
  }

  @Override
  public StudentCode handle(CreateStudentCommand command) {
    // Validate if profile already exists with the same name
    var optionalProfileId
        = this.externalProfileService.fetchProfileIdByFullName(command.fullName());
    if (optionalProfileId.isPresent()) {
      this.studentRepository.findByProfileId(optionalProfileId.get())
          .ifPresent(student -> {
            throw new IllegalArgumentException("Student already exists");
          });
    }

    // Create profile
    optionalProfileId = this.externalProfileService.createProfile(command.fullName(),
        command.age(), command.street());
    if (optionalProfileId.isEmpty()) {
      throw new IllegalArgumentException("Unable to create profile");
    }

    // Create student
    var student
        = new Student(optionalProfileId.get(), command.programId(), command.startPeriod());
    this.studentRepository.save(student);
    return student.getStudentCode();
  }

  @Override
  public Optional<Student> handle(UpdateStudentCommand command) {
    var optionalStudent = this.studentRepository.findByStudentCode(command.studentCode());
    if (optionalStudent.isEmpty()) {
      throw new IllegalArgumentException("Student not found");
    }

    if (this.externalProfileService.existsProfileByFullNameAndIdIsNot(
        command.fullName(), optionalStudent.get().getProfileId())) {
      throw new IllegalArgumentException("Student with name " + command.fullName()
          + " already exists");
    }

    var profileId = this.externalProfileService.updateProfile(
        optionalStudent.get().getProfileId(), command.fullName(), command.age(),
        command.street());

    if (profileId.isEmpty()) {
      throw new IllegalArgumentException("Unable to update profile");
    }

    // Update student
    // this.studentRepository.save(optionalStudent.get());
    return optionalStudent;
  }

  @Override
  public void handle(DeleteStudentCommand command) {
    if (!this.studentRepository.existsByStudentCode(command.studentCode())) {
      throw new IllegalArgumentException("Student not found");
    }
    var optionalStudent = this.studentRepository.findByStudentCode(command.studentCode());
    this.externalProfileService.deleteProfile(optionalStudent.get().getProfileId());
    this.studentRepository.deleteById(optionalStudent.get().getId());
  }
}
