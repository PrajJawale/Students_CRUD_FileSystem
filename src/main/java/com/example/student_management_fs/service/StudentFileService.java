package com.example.student_management_fs.service;


import com.example.student_management_fs.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentFileService {

    private final String FILE_PATH = "students.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Student> getAllStudents() throws IOException {
        return readStudentsFromFile();
    }

    public Student getStudentById(Long id) throws IOException {
        List<Student> students = readStudentsFromFile();
        return students.stream().filter(student -> student.getId().equals(id)).findFirst().orElse(null);
    }

    public Student createStudent(Student student) throws IOException {
        List<Student> students = readStudentsFromFile();
        student.setFirstName(student.getFirstName());
        student.setLastName(student.getLastName());
        student.setCourse(student.getCourse());
        student.setEmail(student.getEmail());
        student.setId(generateId(students));
        students.add(student);
        writeStudentsToFile(students);
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails) throws IOException {
        List<Student> students = readStudentsFromFile();
        Optional<Student> studentOptional = students.stream().filter(student -> student.getId().equals(id)).findFirst();
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            student.setCourse(studentDetails.getCourse());
            writeStudentsToFile(students);
            return student;
        }
        return null;
    }

    public void deleteStudent(Long id) throws IOException {
        List<Student> students = readStudentsFromFile();
        students.removeIf(student -> student.getId().equals(id));
        writeStudentsToFile(students);
    }

    private List<Student> readStudentsFromFile() throws IOException {

        File file = new File(FILE_PATH);
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        }
        return new ArrayList<>();
    }

    private void writeStudentsToFile(List<Student> students) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), students);
    }

    private Long generateId(List<Student> students) {
        return students.stream().mapToLong(Student::getId).max().orElse(0L) + 1;
    }
}
