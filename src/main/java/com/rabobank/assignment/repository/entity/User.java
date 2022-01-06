package com.rabobank.assignment.repository.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "issueCount")
    private int issueCount;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

}
