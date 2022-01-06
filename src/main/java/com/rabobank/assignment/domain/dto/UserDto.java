package com.rabobank.assignment.domain.dto;

import com.rabobank.assignment.repository.entity.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;

    private String firstName;

    private String lastName;

    private int issueCount;

    private String dateOfBirth;

    private int age;

    public UserDto(User user, int age) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.issueCount = user.getIssueCount();
        this.dateOfBirth = user.getDateOfBirth();
        this.age = age;
    }
}
