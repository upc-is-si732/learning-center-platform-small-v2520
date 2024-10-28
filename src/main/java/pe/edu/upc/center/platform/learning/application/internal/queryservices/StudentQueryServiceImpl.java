package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;
import pe.edu.upc.center.platform.learning.domain.services.StudentQueryService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentQueryServiceImpl implements StudentQueryService {

  private final StudentRepository studentRepository;

  public StudentQueryServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<Student> handle(GetAllStudentsQuery query) {
    return this.studentRepository.findAll();
  }

  @Override
  public Optional<Student> handle(GetStudentByIdQuery query) {
    return this.studentRepository.findById(query.studentId());
  }

  @Override
  public Optional<Student> handle(GetStudentByStudentCodeQuery query) {
    return this.studentRepository.findByStudentCode(query.studentCode());
  }

  @Override
  public Optional<Student> handle(GetStudentByProfileIdQuery query) {
    return this.studentRepository.findByProfileId(query.profileId());
  }
}
