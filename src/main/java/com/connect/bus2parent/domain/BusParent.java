package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BusParent {

    @JsonProperty("bus_id")
    int busID;

    @JsonProperty("parent_email")
    String parentEmail;

    public BusParent() { }

    public BusParent(int bID, String pEmail) {
        busID = bID;
        parentEmail = pEmail;
    }

    public int busID() {
        return busID;
    }

    public BusParent setBusID(int busID) {
        this.busID = busID;
        return this;
    }

    public String parentEmail() {
        return parentEmail;
    }

    public BusParent setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusParent busParent = (BusParent) o;
        return new EqualsBuilder()
            .append(busID, busParent.busID)
            .append(parentEmail, busParent.parentEmail)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(busID).append(parentEmail).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("busID", busID)
            .append("parentEmail", parentEmail)
            .toString();
    }
}
