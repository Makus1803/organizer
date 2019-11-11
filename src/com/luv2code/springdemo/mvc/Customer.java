package com.luv2code.springdemo.mvc;


import com.luv2code.springdemo.mvc.validation.CourseCode;

import javax.validation.constraints.*;

public class Customer {

    private String firstName;

    @NotNull(message="is required")
    @Size(min=1, message = " is required")
    private String lastName;

    @NotNull(message="is required")
    @Min(value = 0, message = "must be more or equal to 0")
    @Max(value = 10, message = "must be less or equal to 10")
    private Integer freePasses;

    @Pattern(regexp = "[0-9-]{6}", message = "only 6 digits and \"-\"")
    private String postCode;

    @CourseCode(value = "test", message = "test")
    private String courseCode;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String latName) {
        this.lastName = latName;
    }

    public Integer getFreePasses() {
        return freePasses;
    }

    public void setFreePasses(Integer freePasses) {
        this.freePasses = freePasses;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
