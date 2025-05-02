package Manager;

import Common.TrainingCourse;
import Common.FileUtil;

import java.io.*;
import java.util.ArrayList;

public class TrainingCourseManager {
    private ArrayList<TrainingCourse> courses;
    private final String filePath = "files/training_courses.txt";

    public TrainingCourseManager() {
        FileUtil.createFileIfNotExists(filePath);
        courses = new ArrayList<>();
        loadFromFile();
    }

    public void addCourse(TrainingCourse course) {
        courses.add(course);
        saveToFile();
    }

    public boolean removeCourseById(String courseId) {
        boolean removed = courses.removeIf(c -> c.getCourseId().equals(courseId));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public TrainingCourse searchById(String courseId) {
        for (TrainingCourse c : courses) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<TrainingCourse> getAllCourses() {
        return courses;
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (TrainingCourse c : courses) {
                pw.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            courses.clear();
            while ((line = br.readLine()) != null) {
                courses.add(TrainingCourse.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
