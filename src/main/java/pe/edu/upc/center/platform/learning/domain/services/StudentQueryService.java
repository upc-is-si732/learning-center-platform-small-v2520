package pe.edu.upc.center.platform.learning.domain.services;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;

/**
 * Service interface for handling student-related queries.
 */
public interface StudentQueryService {

  /**
   * Handle the query to get all students.
   *
   * @param query the query to get all students
   * @return a list of all students
   */
  List<Student> handle(GetAllStudentsQuery query);

  /**
   * Handle the query to get a student by their ID.
   *
   * @param query the query containing the student ID
   * @return an Optional containing the Student if found, or empty if not found
   */
  Optional<Student> handle(GetStudentByIdQuery query);

  /**
   * Handle the query to get a student by their student code.
   *
   * @param query the query containing the student code
   * @return an Optional containing the Student if found, or empty if not found
   */
  Optional<Student> handle(GetStudentByStudentCodeQuery query);

  /**
   * Handle the query to get a student by their profile ID.
   *
   * @param query the query containing the profile ID
   * @return an Optional containing the Student if found, or empty if not found
   */
  Optional<Student> handle(GetStudentByProfileIdQuery query);
}
