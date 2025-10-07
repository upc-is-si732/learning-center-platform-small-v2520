package pe.edu.upc.center.platform.learning.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.learning.domain.services.StudentQueryService;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateStudentResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.UpdateStudentCommandFromResourceAssembler;

/**
 * REST controller for managing student profiles.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student Management Endpoints")
public class StudentsController {

  private final StudentCommandService studentCommandService;
  private final StudentQueryService studentQueryService;
  private final ExternalProfileService externalProfileService;

  /**
   * Constructor for StudentsController.
   *
   * @param studentCommandService the service to handle student commands
   * @param studentQueryService   the service to handle student queries
   * @param externalProfileService the service to fetch external profile data
   */
  public StudentsController(StudentCommandService studentCommandService,
      StudentQueryService studentQueryService, ExternalProfileService externalProfileService) {
    this.studentCommandService = studentCommandService;
    this.studentQueryService = studentQueryService;
    this.externalProfileService = externalProfileService;
  }

  /**
   * Create a new student profile.
   *
   * @param resource the student resource to be created
   * @return a ResponseEntity containing the created StudentResource or a bad request status
   *     if creation fails
   */
  @PostMapping
  public ResponseEntity<StudentResource> createStudent(
      @RequestBody CreateStudentResource resource) {
    // Create student
    var createStudentCommand = CreateStudentCommandFromResourceAssembler
        .toCommandFromResource(resource);
    var studentCode = this.studentCommandService.handle(createStudentCommand);
    // Validate if student code is empty
    if (studentCode.studentCode().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    // Fetch student
    var getStudentByStudentCodeQuery = new GetStudentByStudentCodeQuery(studentCode);
    var student = this.studentQueryService.handle(getStudentByStudentCodeQuery);
    if (student.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    // Fetch student resource
    var studentResource = this.externalProfileService
        .fetchStudentResourceFromProfileId(student.get()).get();
    return new ResponseEntity<>(studentResource, HttpStatus.CREATED);

  }

  /**
   * Get all student profiles.
   *
   * @return a ResponseEntity containing a list of StudentResource
   */
  @GetMapping
  public ResponseEntity<List<StudentResource>> getAllStudents() {
    var getAllStudentsQuery = new GetAllStudentsQuery();
    var students = this.studentQueryService.handle(getAllStudentsQuery);
    var profileResources = students.stream()
        .map(this.externalProfileService::fetchStudentResourceFromProfileId)
        .map(Optional::get)
        .collect(Collectors.toList());
    return ResponseEntity.ok(profileResources);
  }

  /**
   * Get a student profile by student code.
   *
   * @param studentCode the student code of the profile to be retrieved
   * @return a ResponseEntity containing the StudentResource or a bad request status if not found
   */
  @GetMapping("/{studentCode}")
  public ResponseEntity<StudentResource> getStudentById(@PathVariable String studentCode) {
    var getStudentByStudentCodeQuery = new GetStudentByStudentCodeQuery(
        new StudentCode(studentCode));
    var optionalStudent = this.studentQueryService.handle(getStudentByStudentCodeQuery);
    if (optionalStudent.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var studentResource = this.externalProfileService
        .fetchStudentResourceFromProfileId(optionalStudent.get()).get();
    return ResponseEntity.ok(studentResource);
  }

  /**
   * Update a student profile by student code.
   *
   * @param studentCode the student code of the profile to be updated
   * @param resource    the updated student resource
   * @return a ResponseEntity containing the updated StudentResource or a bad request status
   *     if the update fails
   */
  @PutMapping("/{studentCode}")
  public ResponseEntity<StudentResource> updateStudent(@PathVariable String studentCode,
                                                       @RequestBody StudentResource resource) {
    var updateProfileCommand = UpdateStudentCommandFromResourceAssembler
        .toCommandFromResource(studentCode, resource);
    var optionalStudent = this.studentCommandService.handle(updateProfileCommand);

    if (optionalStudent.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var studentResource = this.externalProfileService
        .fetchStudentResourceFromProfileId(optionalStudent.get()).get();
    return ResponseEntity.ok(studentResource);
  }

  /**
   * Delete a student profile by student code.
   *
   * @param studentCode the student code of the profile to be deleted
   * @return a ResponseEntity with no content if deletion is successful
   */
  @DeleteMapping("/{studentCode}")
  public ResponseEntity<?> deleteProfile(@PathVariable String studentCode) {
    var deleteStudentCommand = new DeleteStudentCommand(new StudentCode(studentCode));
    this.studentCommandService.handle(deleteStudentCommand);
    return ResponseEntity.noContent().build();
  }
}
