package pe.edu.upc.center.platform.learning.interfaces.rest.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseEnrollItemByIdQuery;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentCommandService;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentQueryService;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollItemResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CourseEnrollItemResourceFromEntityAssembler;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CreateCourseEnrollItemCommandFromResourceAssembler;

/**
 * REST controller for managing course enroll items within enrollments.
 */
@RestController
@RequestMapping(value = "/api/v1/enrollments/{enrollmentId}/course-enroll-items",
    produces = APPLICATION_JSON_VALUE)
@Tag(name = "Enrollments", description = "Course Enroll Items Management Endpoints")
public class EnrollmentCourseEnrollsController {

  private final EnrollmentQueryService enrollmentQueryService;
  private final EnrollmentCommandService enrollmentCommandService;

  /**
   * Constructor for EnrollmentCourseEnrollsController.
   *
   * @param enrollmentQueryService   the service for handling enrollment queries
   * @param enrollmentCommandService the service for handling enrollment commands
   */
  public EnrollmentCourseEnrollsController(EnrollmentQueryService enrollmentQueryService,
                                           EnrollmentCommandService enrollmentCommandService) {
    this.enrollmentQueryService = enrollmentQueryService;
    this.enrollmentCommandService = enrollmentCommandService;
  }

  /**
   * Endpoint to create a new course enroll item for a specific enrollment.
   *
   * @param enrollmentId the ID of the enrollment
   * @param resource     the resource containing details for the new course enroll item
   * @return ResponseEntity with the created CourseEnrollItemResource or an error status
   */
  @Operation (summary = "Add a new course enroll item",
      description = "Add a new course enroll item to an enrollment",
      operationId = "createCourseEnrollItem",
      responses = {
        @ApiResponse (responseCode = "201", description = "Successful operation",
          content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CourseEnrollItemResource.class))),
        @ApiResponse (responseCode = "400", description = "Bad Request",
          content = @Content (
            mediaType = "application/json",
            schema = @Schema(implementation = RuntimeException.class)))
      }
  )
  @PostMapping
  public ResponseEntity<CourseEnrollItemResource> createCourseEnrollItem(
      @PathVariable Long enrollmentId, @RequestBody CreateCourseEnrollResource resource) {

    var createCourseEnrollItemCommand = CreateCourseEnrollItemCommandFromResourceAssembler
        .toCommand(enrollmentId, resource);
    var courseEnrollItemId = this.enrollmentCommandService.handle(createCourseEnrollItemCommand);

    if (courseEnrollItemId.equals(0L)) {
      return ResponseEntity.badRequest().build();
    }

    var getCourseEnrollItemByIdQuery = new GetCourseEnrollItemByIdQuery(courseEnrollItemId);
    var optionalCourseEnrollItem = this.enrollmentQueryService.handle(getCourseEnrollItemByIdQuery);

    if (optionalCourseEnrollItem.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var courseEnrollItemResource
        = CourseEnrollItemResourceFromEntityAssembler.toResource(optionalCourseEnrollItem.get());

    return ResponseEntity.status(201).body(courseEnrollItemResource);
  }
}
