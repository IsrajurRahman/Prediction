/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprediction;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author SHUVO
 */
public class FXMLDocumentController implements Initializable {

    

    ObservableList<Number>semesterList;
    ObservableList<Student>studentList;
    ObservableList<Student>searchList;
    ObservableList<StudentDetails>studentDetailsList;
    @FXML
    private ListView<Student> listViewField;
    @FXML
    private TableView<StudentDetails> tableViewField;
    @FXML
    private TableColumn<StudentDetails, String> courseCodeView;
    @FXML
    private TableColumn<StudentDetails, String> courseTitleView;
    @FXML
    private TableColumn<StudentDetails, String> creditView;
    @FXML
    private TableColumn<StudentDetails, String> facultyView;
    @FXML
    private TableColumn<StudentDetails, String> actualGradeView;
    @FXML
    private TableColumn<StudentDetails, String> predictGradeView;
  
    @FXML
    private JFXTextField searchTextField;
    @FXML
    private JFXComboBox<Number> semesterField;
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private int semesterSelect;
    @FXML
    private TextField disabledNameTextField;
    @FXML
    private TextField disabledIdTextField;
    
    
    

    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    semesterList=FXCollections.observableArrayList();
        semesterField.setItems(semesterList);
        try {
           
        final String HOSTNAME = "127.0.0.1";
        final String DBNAME = "predictdb";
        final String USERNAME = "root";
        final String PASSWORD = "123789";
        final String DBURL = "jdbc:mysql://" + HOSTNAME + "/" + DBNAME;
            
            connection=DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
            statement=connection.createStatement();
            String query="SELECT DISTINCT semesterId FROM registration";
            result=statement.executeQuery(query);
            while(result.next())
            {
                int semesterId=result.getInt("semesterId");
                semesterList.add(semesterId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
      

    @FXML
    private void handleListViewAction(MouseEvent event) {
    studentDetailsList=FXCollections.observableArrayList();
        Student s=listViewField.getSelectionModel().getSelectedItem();
        disabledIdTextField.setText(s.getStudentId());
        disabledNameTextField.setText(s.getStudentName());
        String query="SELECT G.courseCode,C.courseTitle,C.credits,G.facultyInitials,G.grade FROM grades AS G INNER JOIN course as C ON G.courseCode=C.courseCode WHERE G.studentId='"+s.getStudentId()+"' AND G.semesterId='"+semesterSelect+"'";
        try {
            result=statement.executeQuery(query);
            while(result.next())
            {
                String courseCode=result.getString("courseCode");
                String courseTitle=result.getString("courseTitle");
                String credits=result.getString("credits");
                String facultyInitials=result.getString("facultyInitials");
                String predictGrade="A";
                String grade=result.getString("grade");
                StudentDetails sd=new StudentDetails(courseCode, courseTitle, credits, facultyInitials, predictGrade, grade);
                studentDetailsList.add(sd);
                tableViewField.setItems(studentDetailsList);
            }
            courseCodeView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getCourseCode()));
            courseTitleView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getCourseTitle()));
            creditView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getCredits()));
            facultyView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getFacultyInitials()));
            predictGradeView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getPredictGrade()));
            actualGradeView.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getGrade()));
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        searchList=FXCollections.observableArrayList();
        String studentId=searchTextField.getText();
        for(Student s:studentList)
        {
            if(s.getStudentId().equals(studentId))
            {
                searchList.add(s);
            }
        }
        
            listViewField.setItems(searchList);
       
    
    }

    @FXML
    private void semesterField(ActionEvent event) {
        searchTextField.setText("");
        studentList=FXCollections.observableArrayList();
        listViewField.setItems(studentList);
        semesterSelect=(int) semesterField.getSelectionModel().getSelectedItem();
        String query="SELECT R.studentId,S.studentName FROM registration as R INNER JOIN student AS S ON R.studentId=S.studentId WHERE semesterId='"+semesterSelect+"'";
        try {
            result=statement.executeQuery(query);
            while(result.next())
            {
                String studentId=result.getString("studentId");
                String studentName=result.getString("studentName");
                Student student =new Student(studentId, studentName);
                studentList.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}