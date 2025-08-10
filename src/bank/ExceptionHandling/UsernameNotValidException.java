package bank.ExceptionHandling;

public class UsernameNotValidException extends Exception{
    public UsernameNotValidException(String msg){
        super(msg);
    }
}
