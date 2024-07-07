package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bus {
    @JsonProperty("bus_plate")
    private String busPlate;
    @JsonProperty("bus_number")
    private int busNumber;

    public String busPlate() {
        return busPlate;
    }

    public Bus setBusPlate(String busPlate) {
        this.busPlate = busPlate;
        return this;
    }

    public int busNumber() {
        return busNumber;
    }

    public Bus setBusNumber(int busNumber) {
        this.busNumber = busNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Bus bus = (Bus) o;

        return new EqualsBuilder().append(busNumber, bus.busNumber).append(busPlate, bus.busPlate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(busPlate).append(busNumber).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("busPlate", busPlate)
                .append("busNumber", busNumber)
                .toString();
    }
}
