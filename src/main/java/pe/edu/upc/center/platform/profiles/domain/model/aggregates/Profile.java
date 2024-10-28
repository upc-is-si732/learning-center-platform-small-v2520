package pe.edu.upc.center.platform.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

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
  @AttributeOverrides( {
      @AttributeOverride(name = "street", column = @Column(name = "address_street", length = 100, nullable = false))
  })
  private StreetAddress address;

  //---------------------------------------------------
  public Profile(String fullName, int age, String street) {
    this.fullName = fullName;
    this.age = age;
    this.address = new StreetAddress(street);
  }

  public Profile() {
  }

  public void updateStreetAddress(String street) {
    this.address = new StreetAddress(street);
  }

  public String getAddress() {
    return address.street();
  }
  //---------------------------------------------------
  public Profile(CreateProfileCommand command) {
    this.fullName = command.fullName();
    this.age = command.age();
    this.address = new StreetAddress(command.street());
  }

  public Profile updateInformation(String fullName, int age, String street) {
    this.fullName = fullName;
    this.age = age;
    this.address = new StreetAddress(street);
    return this;
  }
}
