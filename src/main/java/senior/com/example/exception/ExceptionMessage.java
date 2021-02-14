package senior.com.example.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionMessage {
    private Date currentDate;
    private String message;
    private HttpStatus status;

    public ExceptionMessage() {
    }

    public ExceptionMessage(Date currentDate, String message, HttpStatus status) {
        this.currentDate = currentDate;
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
