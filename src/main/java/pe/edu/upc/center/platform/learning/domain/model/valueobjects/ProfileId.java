package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a Profile ID.
 *
 * @param profileId the unique identifier for the profile
 */
@Embeddable
public record ProfileId(Long profileId) {
  /**
   * Constructor for ProfileId with validation.
   *
   * @param profileId the unique identifier for the profile
   * @throws IllegalArgumentException if profileId is negative
   */
  public ProfileId {
    if (profileId < 0) {
      throw new IllegalArgumentException("Profile profileId cannot be negative");
    }
  }

  /**
   * Default constructor for ProfileId with a default value of 0.
   */
  public ProfileId() {
    this(0L);
  }
}
