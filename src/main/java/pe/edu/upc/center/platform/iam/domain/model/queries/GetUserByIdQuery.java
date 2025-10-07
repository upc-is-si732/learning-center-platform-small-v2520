package pe.edu.upc.center.platform.iam.domain.model.queries;

/**
 * Query to get a user by their unique identifier.
 *
 * @param userId the unique identifier of the user
 */
public record GetUserByIdQuery(Long userId) {
}
