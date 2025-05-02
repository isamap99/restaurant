package Common;

public class TrainingCourse {
    private String courseId;
    private String title;
    private String description;
    private String date; // مثلا "2025-05-02"
    private String instructor;

    public TrainingCourse(String courseId, String title, String description, String date, String instructor) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.instructor = instructor;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getInstructor() {
        return instructor;
    }

    @Override
    public String toString() {
        return courseId + "," + title + "," + description + "," + date + "," + instructor;
    }

    public static TrainingCourse fromString(String line) {
        String[] parts = line.split(",", -1);
        return new TrainingCourse(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}
