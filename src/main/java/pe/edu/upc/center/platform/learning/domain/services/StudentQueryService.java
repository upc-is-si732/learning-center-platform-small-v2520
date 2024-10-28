package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;

import java.util.List;
import java.util.Optional;

public interface StudentQueryService {
  List<Student> handle(GetAllStudentsQuery query);
  Optional<Student> handle(GetStudentByIdQuery query);
  Optional<Student> handle(GetStudentByStudentCodeQuery query);
  Optional<Student> handle(GetStudentByProfileIdQuery query);
}
