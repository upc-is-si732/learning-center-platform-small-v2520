package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentStatusesQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentStatusByNameQuery;

import java.util.List;
import java.util.Optional;

public interface EnrollmentStatusQueryService {
  List<EnrollmentStatus> handle(GetAllEnrollmentStatusesQuery query);
  Optional<EnrollmentStatus> handle(GetEnrollmentStatusByNameQuery query);
}
