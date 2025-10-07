package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;

/**
 * Service interface for handling course assignment commands.
 */
public interface CourseAssignCommandService {
  /**
   * Handles the creation of a course assignment.
   *
   * @param command the command containing course assignment details
   * @return the ID of the created course assignment
   */
  Long handle(CreateCourseAssignCommand command);
}
