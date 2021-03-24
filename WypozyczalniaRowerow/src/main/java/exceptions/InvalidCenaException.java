package exceptions;

public class InvalidCenaException extends Exception{

    public InvalidCenaException() {
        super();
    }

    public InvalidCenaException(String message) {
        super(message);
    }

    public InvalidCenaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCenaException(Throwable cause) {
        super(cause);
    }

    protected InvalidCenaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
