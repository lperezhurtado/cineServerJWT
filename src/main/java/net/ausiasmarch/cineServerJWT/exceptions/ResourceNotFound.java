package net.ausiasmarch.cineServerJWT.exceptions;

public class ResourceNotFound extends RuntimeException {
    
    public ResourceNotFound(String msg) {
        super("Error: " + msg);
    }
}
