package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetStudentByStudentCodeQuery;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentQueryServiceImplTest {

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private StudentQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllStudentsQuery) should return list from repository (AAA)")
  void handle_GetAll_ShouldReturnList() {
    // Arrange
    Student a = mock(Student.class);
    Student b = mock(Student.class);
    when(studentRepository.findAll()).thenReturn(List.of(a, b));
    var query = new GetAllStudentsQuery();

    // Act
    var actual = service.handle(query);

    // Assert
    assertEquals(2, actual.size());
    assertSame(a, actual.get(0));
    assertSame(b, actual.get(1));
    verify(studentRepository).findAll();
    verifyNoMoreInteractions(studentRepository);
  }

  @Test
  @DisplayName("handle(GetStudentByIdQuery) should return Optional from repository (AAA)")
  void handle_GetById_ShouldReturnOptional() {
    // Arrange
    var profileId = new ProfileId(10L);
    Student expected = new Student(profileId, 3310160L, "202520");
    //Student expected = mock(Student.class);
    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(expected));
    var query = new GetStudentByIdQuery(anyLong());

    // Act
    var actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    assertEquals(3310160L, expected.getProgramId());
    assertEquals("202520", expected.getStartPeriod());
    verify(studentRepository).findById(anyLong());
    verifyNoMoreInteractions(studentRepository);
  }

  @Test
  @DisplayName("handle(GetStudentByStudentCodeQuery) should delegate to repository (AAA)")
  void handle_GetByCode_ShouldDelegate() {
    // Arrange
    var code = new StudentCode(UUID.randomUUID().toString());
    Student expected = mock(Student.class);
    when(studentRepository.findByStudentCode(code)).thenReturn(Optional.of(expected));
    var query = new GetStudentByStudentCodeQuery(code);

    // Act
    var actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(studentRepository).findByStudentCode(code);
    verifyNoMoreInteractions(studentRepository);
  }

  @Test
  @DisplayName("handle(GetStudentByProfileIdQuery) should delegate to repository (AAA)")
  void handle_GetByProfileId_ShouldDelegate() {
    // Arrange
    var profileId = new ProfileId(99L);
    Student expected = mock(Student.class);
    when(studentRepository.findByProfileId(profileId)).thenReturn(Optional.of(expected));
    var query = new GetStudentByProfileIdQuery(profileId);

    // Act
    var actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(studentRepository).findByProfileId(profileId);
    verifyNoMoreInteractions(studentRepository);
  }
}
