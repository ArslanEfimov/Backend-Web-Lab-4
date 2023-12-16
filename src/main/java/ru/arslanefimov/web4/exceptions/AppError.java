package ru.arslanefimov.web4.exceptions;

import java.util.Date;

public class AppError {
    private int status;
    private String message;


    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
