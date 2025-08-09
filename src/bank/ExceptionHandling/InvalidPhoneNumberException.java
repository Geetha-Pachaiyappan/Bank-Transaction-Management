package bank.ExceptionHandling;

public class InvalidPhoneNumberException extends Exception{
    public InvalidPhoneNumberException(String msg){
        super(msg);
    }
}
