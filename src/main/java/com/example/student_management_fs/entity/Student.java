package com.example.student_management_fs.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder

public class Student {
    Long id;
    String firstName;
    String lastName;
    String email;
    String course;


}

