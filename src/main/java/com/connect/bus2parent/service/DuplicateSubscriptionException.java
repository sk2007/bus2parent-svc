package com.connect.bus2parent.service;

public class DuplicateSubscriptionException extends RuntimeException {
    public DuplicateSubscriptionException(int busNumber, String parentEmail) {
        super("Parent " + parentEmail + " is already subscribed to bus " + busNumber);
    }
}
