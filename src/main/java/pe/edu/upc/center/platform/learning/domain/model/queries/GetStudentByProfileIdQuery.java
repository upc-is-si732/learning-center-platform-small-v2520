package pe.edu.upc.center.platform.learning.domain.model.queries;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;

/**
 * Query to get a student by their profile ID.
 *
 * @param profileId The profile ID of the student to retrieve.
 */
public record GetStudentByProfileIdQuery(ProfileId profileId) {
}
