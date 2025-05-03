package com.teatro.app.exception;

public class ClienteNoEncontradoException extends RuntimeException{
    public ClienteNoEncontradoException(String dni) {
        super("No se encontró el cliente cuyo numero de DNI: " + dni);
    }
}
