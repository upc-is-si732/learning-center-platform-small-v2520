package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.ToString;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;

import java.util.ArrayList;
import java.util.List;

@ToString
@Embeddable
public class CourseEnroll {

  @Getter
  @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
  private List<CourseEnrollItem> courseEnrollItems;

  public CourseEnroll() {
    this.courseEnrollItems = new ArrayList<>();
  }

  public void addItem(Enrollment enrollment, CourseAssign courseAssign, int numberOfTimes) {
    this.courseEnrollItems.add(new CourseEnrollItem(enrollment, courseAssign, numberOfTimes));
  }

  public void addItems(List<CourseEnrollItem> courseEnrollItems) {
    this.courseEnrollItems.addAll(courseEnrollItems);
  }

}
