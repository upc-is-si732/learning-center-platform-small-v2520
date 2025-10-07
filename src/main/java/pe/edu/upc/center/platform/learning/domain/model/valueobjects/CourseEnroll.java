package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;

/**
 * Represents a course enrollment containing multiple course enrollment items.
 *
 * @author Open Source Application Development Team
 */
@ToString
@Embeddable
public class CourseEnroll {

  @Getter
  @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
  private List<CourseEnrollItem> courseEnrollItems;

  /**
   * Default constructor initializing the list of CourseEnrollItems.
   */
  public CourseEnroll() {
    this.courseEnrollItems = new ArrayList<>();
  }

  /**
   * Adds a CourseEnrollItem to the enrollment.
   *
   * @param enrollment     the enrollment associated with the item
   * @param courseAssign   the course assignment associated with the item
   * @param numberOfTimes  the number of times the course is enrolled
   */
  public void addItem(Enrollment enrollment, CourseAssign courseAssign, int numberOfTimes) {
    this.courseEnrollItems.add(new CourseEnrollItem(enrollment, courseAssign, numberOfTimes));
  }

  /**
   * Adds multiple CourseEnrollItems to the enrollment.
   *
   * @param courseEnrollItems the list of CourseEnrollItems to add
   */
  public void addItems(List<CourseEnrollItem> courseEnrollItems) {
    this.courseEnrollItems.addAll(courseEnrollItems);
  }

}
