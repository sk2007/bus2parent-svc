package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Parent {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email_address")
    private String emailAddress;

    public String emailAddress() {
        return emailAddress;
    }

    public Parent setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String lastName() {
        return lastName;
    }

    public Parent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String firstName() {
        return firstName;
    }

    public Parent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Parent parent = (Parent) o;

        return new EqualsBuilder().append(firstName, parent.firstName).append(lastName, parent.lastName).append(emailAddress, parent.emailAddress).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(firstName).append(lastName).append(emailAddress).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", emailAddress)
                .toString();
    }
}
