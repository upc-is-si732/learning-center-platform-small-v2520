package pe.edu.upc.center.platform.profiles.domain.model.queries;

/**
 * Query to get profiles by age.
 *
 * @param age the age to filter profiles
 */
public record GetProfileByAgeQuery(int age) {
}
