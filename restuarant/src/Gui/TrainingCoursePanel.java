package Gui;

import Manager.TrainingCourseManager;
import Common.TrainingCourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TrainingCoursePanel extends JPanel {
    private TrainingCourseManager courseManager;
    private JTextField courseIdField, titleField, descriptionField, dateField, instructorField;
    private JTextArea displayArea;

    public TrainingCoursePanel() {
        courseManager = new TrainingCourseManager();
        setLayout(new BorderLayout());

        // بخش ورودی
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Course ID:"));
        courseIdField = new JTextField();
        inputPanel.add(courseIdField);

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Instructor:"));
        instructorField = new JTextField();
        inputPanel.add(instructorField);

        // بخش دکمه‌ها
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Course");
        JButton removeButton = new JButton("Remove Course");
        JButton searchButton = new JButton("Search Course");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);

        // نمایش دوره‌ها
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // اضافه کردن بخش‌ها به پنل اصلی
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // رویداد دکمه‌ها
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseId = courseIdField.getText();
                String title = titleField.getText();
                String description = descriptionField.getText();
                String date = dateField.getText();
                String instructor = instructorField.getText();

                if (!courseId.isEmpty() && !title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !instructor.isEmpty()) {
                    TrainingCourse course = new TrainingCourse(courseId, title, description, date, instructor);
                    courseManager.addCourse(course);
                    displayCourses(courseManager.getAllCourses());
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseId = courseIdField.getText();
                if (!courseId.isEmpty()) {
                    boolean success = courseManager.removeCourseById(courseId);
                    if (success) {
                        displayCourses(courseManager.getAllCourses());
                    } else {
                        JOptionPane.showMessageDialog(null, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseId = courseIdField.getText();
                if (!courseId.isEmpty()) {
                    TrainingCourse course = courseManager.searchById(courseId);
                    if (course != null) {
                        displayArea.setText(course.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void displayCourses(List<TrainingCourse> courses) {
        displayArea.setText("");  // Clear the display area
        for (TrainingCourse course : courses) {
            displayArea.append(course.toString() + "\n");
        }
    }
}
