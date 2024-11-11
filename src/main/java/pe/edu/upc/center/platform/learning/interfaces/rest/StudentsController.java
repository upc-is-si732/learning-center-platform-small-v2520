package pe.edu.upc.center.platform.learning.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;
import pe.edu.upc.center.platform.learning.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.learning.domain.services.StudentQueryService;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateStudentResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.UpdateStudentCommandFromResourceAssembler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student Management Endpoints")
public class StudentsController {

  private final StudentCommandService studentCommandService;
  private final StudentQueryService studentQueryService;
  private final ExternalProfileService externalProfileService;

  public StudentsController(StudentCommandService studentCommandService, StudentQueryService studentQueryService,
      ExternalProfileService externalProfileService) {
    this.studentCommandService = studentCommandService;
    this.studentQueryService = studentQueryService;
    this.externalProfileService = externalProfileService;
  }

  @PostMapping
  public ResponseEntity<StudentResource> createStudent(@RequestBody CreateStudentResource resource) {
    // Create student
    var createStudentCommand = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);
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
    var studentResource = this.externalProfileService.fetchStudentResourceFromProfileId(student.get()).get();
    return new ResponseEntity<>(studentResource, HttpStatus.CREATED);

  }

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

  @GetMapping("/{studentCode}")
  public ResponseEntity<StudentResource> getStudentById(@PathVariable String studentCode) {
    var getStudentByStudentCodeQuery = new GetStudentByStudentCodeQuery(new StudentCode(studentCode));
    var optionalStudent = this.studentQueryService.handle(getStudentByStudentCodeQuery);
    if (optionalStudent.isEmpty())
      return ResponseEntity.badRequest().build();
    var studentResource = this.externalProfileService.fetchStudentResourceFromProfileId(optionalStudent.get()).get();
    return ResponseEntity.ok(studentResource);
  }

  @PutMapping("/{studentCode}")
  public ResponseEntity<StudentResource> updateStudent(@PathVariable String studentCode, @RequestBody StudentResource resource) {
    var updateProfileCommand = UpdateStudentCommandFromResourceAssembler.toCommandFromResource(studentCode, resource);
    var optionalStudent = this.studentCommandService.handle(updateProfileCommand);

    if (optionalStudent.isEmpty())
      return ResponseEntity.badRequest().build();
    var studentResource = this.externalProfileService.fetchStudentResourceFromProfileId(optionalStudent.get()).get();
    return ResponseEntity.ok(studentResource);
  }

  @DeleteMapping("/{studentCode}")
  public ResponseEntity<?> deleteProfile(@PathVariable String studentCode) {
    var deleteStudentCommand = new DeleteStudentCommand(new StudentCode(studentCode));
    this.studentCommandService.handle(deleteStudentCommand);
    return ResponseEntity.noContent().build();
  }
}
