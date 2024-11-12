package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentCommandService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseEnrollItemRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {

  private final EnrollmentRepository enrollmentRepository;
  private final CourseAssignRepository courseAssignRepository;
  private final EnrollmentStatusRepository enrollmentStatusRepository;
  private final CourseEnrollItemRepository courseEnrollItemRepository;

  public EnrollmentCommandServiceImpl(EnrollmentRepository enrollmentRepository, CourseAssignRepository courseAssignRepository, EnrollmentStatusRepository enrollmentStatusRepository, CourseEnrollItemRepository courseEnrollItemRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.courseAssignRepository = courseAssignRepository;
    this.enrollmentStatusRepository = enrollmentStatusRepository;
    this.courseEnrollItemRepository = courseEnrollItemRepository;
  }

  @Override
  public Long handle(CreateEnrollmentCommand command) {
    // Validate if enrollment already exists
    if (this.enrollmentRepository.existsByStudentCodeAndAndPeriod(command.studentCode(), command.period())) {
      throw new IllegalArgumentException("Enrollment with student code " + command.studentCode()
          + " and period " + command.period() + " already exists");
    }

    //  Validate if enrollment status exists
    var enrollmentStatus = this.enrollmentStatusRepository.findByName(EnrollmentStatuses.valueOf(command.status()))
        .orElseThrow(() -> new IllegalArgumentException("Enrollment status with name " + command.status() + " not found"));

    // Create enrollment
    var enrollment = new Enrollment(command.studentCode(), command.period(), enrollmentStatus);
    // Add course enroll items
    var courseEnrollItems = command.courseEnrolls().stream()
        .map(courseEnroll -> {
            var courseAssign = this.courseAssignRepository.findById(courseEnroll.courseAssignId())
                .orElseThrow(() -> new IllegalArgumentException("Course assign with id " + courseEnroll.courseAssignId() + " not found"));
            return new CourseEnrollItem(enrollment, courseAssign, courseEnroll.numberOfTimes());
          })
        .toList();
    enrollment.addCourseEnrollItems(courseEnrollItems);

    try {
      this.enrollmentRepository.save(enrollment);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while saving enrollment: " + e.getMessage());
    }

    return enrollment.getId();
  }

  @Override
  public Long handle(CreateCourseEnrollItemCommand command) {
    // Validate if enrollment exists
    var enrollment = this.enrollmentRepository.findById(command.enrollmentId())
        .orElseThrow(() -> new IllegalArgumentException("Enrollment with id " + command.enrollmentId() + " not found"));

    // Validate if course assign exists
    var courseAssign = this.courseAssignRepository.findById(command.courseAssignId())
        .orElseThrow(() -> new IllegalArgumentException("Course assign with id " + command.courseAssignId() + " not found"));

    // validate if course enroll item already exists
    if (this.courseEnrollItemRepository.existsByEnrollmentAndCourseAssign(enrollment, courseAssign)) {
      throw new IllegalArgumentException("Course enroll item with enrollment id " + command.enrollmentId()
          + " and course assign id " + command.courseAssignId() + " already exists");
    }

    // create course enroll item
    var courseEnrollItem = new CourseEnrollItem(enrollment, courseAssign, command.numberOfTimes());

    try {
      this.courseEnrollItemRepository.save(courseEnrollItem);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while saving course enroll item: " + e.getMessage());
    }

    return courseEnrollItem.getId();
  }
}
