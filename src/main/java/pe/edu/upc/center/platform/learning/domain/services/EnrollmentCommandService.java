package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;

/**
 * Servicio para manejar comandos relacionados con inscripciones en la plataforma de aprendizaje.
 */
public interface EnrollmentCommandService {

  /**
   * Crea una nueva inscripción en la plataforma de aprendizaje.
   *
   * @param command Comando que contiene los detalles de la inscripción a crear.
   * @return El ID de la inscripción creada.
   */
  Long handle(CreateEnrollmentCommand command);

  /**
   * Crea un ítem de inscripción para un curso específico dentro de una inscripción existente.
   *
   * @param command Comando que contiene los detalles del ítem de inscripción a crear.
   * @return El ID del ítem de inscripción creado.
   */
  Long handle(CreateCourseEnrollItemCommand command);
}
