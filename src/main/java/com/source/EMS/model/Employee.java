package com.source.EMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Schema(name = "Employee", description = "Employee record used by the EMS API")
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique employee identifier", example = "1")
    private long id;

    @Column(name = "first_name")
    @Schema(description = "Employee first name", example = "John")
    private String firstName;

    @Column(name = "last_name")
    @Schema(description = "Employee last name", example = "Doe")
    private String lastName;

    @Column(name = "email_id")
    @Schema(description = "Employee email address", example = "john.doe@company.com")
    private String emailId;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }
}
