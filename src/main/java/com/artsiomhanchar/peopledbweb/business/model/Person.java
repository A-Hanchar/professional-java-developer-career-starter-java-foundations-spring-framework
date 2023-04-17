package com.artsiomhanchar.peopledbweb.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private BigDecimal salary;



//    public String getFormattedDOB() {
//        return DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(dob);
//    }
}
