package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;
import pe.edu.upc.center.platform.learning.domain.services.CourseAssignCommandService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;

@Service
public class CourseAssignCommandServiceImpl implements CourseAssignCommandService {

    private final CourseAssignRepository courseAssignRepository;

    public CourseAssignCommandServiceImpl(CourseAssignRepository courseAssignRepository) {
        this.courseAssignRepository = courseAssignRepository;
    }

    @Override
    public Long handle(CreateCourseAssignCommand command) {
        // Validate Constraints: Course and Section unique
        if (this.courseAssignRepository.existsByCourseIdAndSection(new CourseId(command.courseId()), command.section())) {
            throw new IllegalArgumentException("Course with id " + command.courseId() + " and section " + command.section() + " already exists");
        }
        // Create CourseAssign
        var courseAssign = new CourseAssign(command);
        try {
            this.courseAssignRepository.save(courseAssign);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving course assign: " + e.getMessage());
        }
        // return CourseAssign Id
        return courseAssign.getId();
    }
}
