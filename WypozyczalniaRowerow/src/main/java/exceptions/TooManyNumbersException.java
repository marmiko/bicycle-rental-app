package exceptions;

public class TooManyNumbersException extends Exception{
    public TooManyNumbersException() {
        super();
    }

    public TooManyNumbersException(String message) {
        super(message);
    }

    public TooManyNumbersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyNumbersException(Throwable cause) {
        super(cause);
    }

    protected TooManyNumbersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
