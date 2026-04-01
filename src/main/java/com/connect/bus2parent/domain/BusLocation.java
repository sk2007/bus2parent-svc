package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BusLocation {

    @JsonProperty("bus_number")
    private int busNumber;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public BusLocation() { }

    public BusLocation(int busNumber, double latitude, double longitude) {
        this.busNumber = busNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int busNumber() { return busNumber; }

    public BusLocation setBusNumber(int busNumber) {
        this.busNumber = busNumber;
        return this;
    }

    public double latitude() { return latitude; }

    public BusLocation setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double longitude() { return longitude; }

    public BusLocation setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusLocation that = (BusLocation) o;
        return new EqualsBuilder()
            .append(busNumber, that.busNumber)
            .append(latitude, that.latitude)
            .append(longitude, that.longitude)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(busNumber).append(latitude).append(longitude).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("busNumber", busNumber)
            .append("latitude", latitude)
            .append("longitude", longitude)
            .toString();
    }
}
