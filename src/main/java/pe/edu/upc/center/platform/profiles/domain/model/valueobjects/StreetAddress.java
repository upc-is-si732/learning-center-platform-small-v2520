package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a street address.
 */
@Embeddable
public record StreetAddress(String street) {

  /**
   * Default constructor for JPA.
   */
  public StreetAddress() {
    this(null);
  }

  /**
   * Constructor with validation.
   *
   * @param street the street address
   * @throws IllegalArgumentException if the street is null or blank
   */
  public StreetAddress {
    if (street == null || street.isBlank()) {
      throw new IllegalArgumentException("Address cannot be null or blank");
    }
  }
}
