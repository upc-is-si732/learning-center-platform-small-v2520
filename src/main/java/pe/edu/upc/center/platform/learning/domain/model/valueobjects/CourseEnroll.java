package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class CourseEnroll {

  @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
  private List<CourseEnrollItem> courseEnrollItems;

  public CourseEnroll() {
    this.courseEnrollItems = new ArrayList<>();
  }

}
