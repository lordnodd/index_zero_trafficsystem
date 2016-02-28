package kcl.teamIndexZero.traffic.simulator.mapSetup;

/**
 * Created by Es on 28/02/2016.
 * Exception for when a particular object already exists
 */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
