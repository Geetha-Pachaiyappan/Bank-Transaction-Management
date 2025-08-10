package bank;

import bank.ExceptionHandling.InvalidAadharException;
import bank.ExceptionHandling.InvalidPhoneNumberException;
import bank.ExceptionHandling.InvalidUserAgeException;
import bank.ExceptionHandling.UsernameNotValidException;

public class Validations {

    static void isUsernameValid(String name) throws UsernameNotValidException {
        if( name == null || name.isEmpty() ||
                !name.matches("[a-zA-Z]+") )
            throw new UsernameNotValidException("Invalid UserName, Username contains only Characters not digits!");
    }

    static void isAadharValid(String aadhar) throws InvalidAadharException {
        if (aadhar == null || aadhar.isEmpty() ||
                aadhar.contains(" ") || !aadhar.matches("\\d{12}")
                || aadhar.matches("[a-zA-Z]+"))
            throw new InvalidAadharException("Invalid Aadhar Number! Please Check");
    }

    static void isPhoneNumberValid(String phoneNo) throws InvalidPhoneNumberException {
        if( phoneNo == null || phoneNo.isEmpty() ||
                phoneNo.contains(" ") || !phoneNo.matches("\\d{10}"))
            throw new InvalidPhoneNumberException("Invalid Phone Number! Please Check");
    }

    static void isUserAgeValid(int age) throws InvalidUserAgeException {
        String strAge =  Integer.toString(age);
        if(age == 0 || age < 0 || !strAge.matches("\\d+"))
            throw new InvalidUserAgeException("Invalid Age, Please Check!");

    }

}
