/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprediction;

/**
 *
 * @author SHUVO
 */
class StudentDetails {
    private String courseCode;
    private String courseTitle;
    private String credits;
    private String facultyInitials;
    private String predictGrade;
    private String grade;

    public StudentDetails(String courseCode, String courseTitle, String credits, String facultyInitials, String predictGrade, String grade) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.credits = credits;
        this.facultyInitials = facultyInitials;
        this.predictGrade = predictGrade;
        this.grade = grade;
    }

    public StudentDetails() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCredits() {
        return credits;
    }

    public String getFacultyInitials() {
        return facultyInitials;
    }

    public String getPredictGrade() {
        return predictGrade;
    }

    public String getGrade() {
        return grade;
    }
    
}
