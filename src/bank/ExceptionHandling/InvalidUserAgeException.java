package bank.ExceptionHandling;

public class InvalidUserAgeException extends Exception{
    public InvalidUserAgeException(String msg){
        super(msg);
    }
}
