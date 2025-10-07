package pe.edu.upc.center.platform.profiles.domain.model.queries;

/**
 * Query to get a profile by full name.
 *
 * @param fullName the full name of the profile to retrieve
 */
public record GetProfileByFullNameQuery(String fullName) {
}
