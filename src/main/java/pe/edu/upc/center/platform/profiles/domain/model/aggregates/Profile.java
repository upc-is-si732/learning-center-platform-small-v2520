package pe.edu.upc.center.platform.profiles.domain.model.aggregates;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents a user profile in the system.
 */
@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

  @Getter
  @NotNull
  @NotBlank
  @Column(name = "full_name", length = 50, nullable = false)
  private String fullName;

  @Getter
  @Min(0)
  @Max(100)
  @Column(name = "age", columnDefinition = "smallint", nullable = false)
  private int age;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "street",
          column = @Column(name = "address_street", length = 100, nullable = false))
  })
  private StreetAddress address;

  /**
   * Constructs a Profile instance from a CreateProfileCommand.
   *
   * @param command the CreateProfileCommand containing profile details
   */
  public Profile(CreateProfileCommand command) {
    this.fullName = command.fullName();
    this.age = command.age();
    this.address = new StreetAddress(command.street());
  }

  /**
   * Constructs a Profile instance with the specified details.
   *
   * @param fullName the full name of the profile
   * @param age      the age of the profile
   * @param street   the street address of the profile
   */
  public Profile(String fullName, int age, String street) {
    this.fullName = fullName;
    this.age = age;
    this.address = new StreetAddress(street);
  }

  /**
   * Default constructor for JPA.
   */
  public Profile() {
  }

  /** Updates the street address of the profile.
   *
   * @param street the new street address
   */
  public void updateStreetAddress(String street) {
    this.address = new StreetAddress(street);
  }

  public String getAddress() {
    return address.street();
  }



  /**
   * Updates the profile information.
   *
   * @param fullName the new full name
   * @param age      the new age
   * @param street   the new street address
   * @return the updated Profile instance
   */
  public Profile updateInformation(String fullName, int age, String street) {
    this.fullName = fullName;
    this.age = age;
    this.address = new StreetAddress(street);
    return this;
  }
}
