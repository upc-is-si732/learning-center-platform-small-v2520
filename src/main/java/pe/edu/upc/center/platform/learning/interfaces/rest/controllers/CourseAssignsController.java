package pe.edu.upc.center.platform.learning.interfaces.rest.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllCourseAssignsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;
import pe.edu.upc.center.platform.learning.domain.services.CourseAssignCommandService;
import pe.edu.upc.center.platform.learning.domain.services.CourseAssignQueryService;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseAssignResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseAssignResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CourseAssignResourceFromEntityAssembler;
import pe.edu.upc.center.platform.learning.interfaces.rest.transform.CreateCourseAssignCommandFromResourceAssembler;

/**
 * REST controller for managing course assignments.
 */
@RestController
@RequestMapping(value = "/api/v1/course-assign", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseAssignsController {
  private final CourseAssignCommandService courseAssignCommandService;
  private final CourseAssignQueryService courseAssignQueryService;

  /**
   * Constructor for CourseAssignsController.
   *
   * @param courseAssignCommandService the service for handling course assignment commands
   * @param courseAssignQueryService   the service for querying course assignments
   */
  public CourseAssignsController(CourseAssignCommandService courseAssignCommandService,
                                 CourseAssignQueryService courseAssignQueryService) {
    this.courseAssignCommandService = courseAssignCommandService;
    this.courseAssignQueryService = courseAssignQueryService;
  }

  /**
   * Endpoint to create a new course assignment.
   *
   * @param resource the resource containing course assignment details
   * @return ResponseEntity with the created CourseAssignResource or an error status
   */
  @PostMapping
  public ResponseEntity<CourseAssignResource> createCourseAssign(
      @RequestBody CreateCourseAssignResource resource) {
    // Create course assign
    var createCourseAssignCommand
        = CreateCourseAssignCommandFromResourceAssembler.toCommand(resource);
    var courseAssignId = this.courseAssignCommandService.handle(createCourseAssignCommand);
    // Validate if course assign id is empty
    if (courseAssignId.equals(0L)) {
      return ResponseEntity.badRequest().build();
    }
    // Fetch course assign
    var getCourseAssignByIdQuery = new GetCourseAssignByIdQuery(courseAssignId);
    var optionalCourseAssign = this.courseAssignQueryService.handle(getCourseAssignByIdQuery);
    // Validate if course assign is empty
    if (optionalCourseAssign.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    // Fetch course assign resource
    var courseAssignResource
        = CourseAssignResourceFromEntityAssembler.toResource(optionalCourseAssign.get());
    return new ResponseEntity<>(courseAssignResource, HttpStatus.CREATED);
  }

  /**
   * Endpoint to retrieve all course assignments.
   *
   * @return ResponseEntity with a list of CourseAssignResource
   */
  @GetMapping
  public ResponseEntity<List<CourseAssignResource>> getAllCourseAssigns() {
    var getAllCourseAssignsQuery = new GetAllCourseAssignsQuery();
    var courseAssigns = this.courseAssignQueryService.handle(getAllCourseAssignsQuery);
    var courseAssignResources = courseAssigns.stream()
        .map(CourseAssignResourceFromEntityAssembler::toResource)
        .toList();
    return new ResponseEntity<>(courseAssignResources, HttpStatus.OK);
  }
}
