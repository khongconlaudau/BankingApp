package com.BankApp.demo.Checker;

import com.BankApp.demo.Controller.Register.RegisterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Lazy
public class CheckNewAccount {
//    @Autowired
//    private  UserRepo userRepo;


    private RegisterController registerController;
  //  private final RegisterController registerController;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String rePassword;
    private String chequing;
    private String phoneNumber;
    private String pin;
    private String gmail;

    @Autowired
    public CheckNewAccount(RegisterController registerController) {
        this.registerController = registerController;
    }

    public boolean checkFirstName(){
        firstName = registerController.getFirstName();
        //     messageLabel.setText("Please enter a valid first name");
        return !firstName.isEmpty() && !firstName.contains(" ");
    }

    public boolean checkLastName (){
        lastName = registerController.getLastName();
        // messageLabel.setText("Please enter a valid last name");
        return !lastName.isEmpty() && !lastName.contains(" ");
    }

    public boolean checkUserName(){
        userName = registerController.getUsername();
        //      boolean meetAll = true;
//        if (userRepo.findAllUserName() !=null){
//            userNames = userRepo.findAllUserName();
//            for (String i : userNames){
//                if (userName.equals(i)){
//                    meetAll = false;
//                    break;
//                }
//            }
//        }
//        if(userRepo.existsByUserName(userName)){
//            meetAll = false;
//        }
        // messageLabel.setText("Please enter a valid username and username's length >= 10");
        // return !userName.isEmpty() && userName.contains(" ") && userName.length()>=10 && meetAll;
        return !userName.isEmpty() & !userName.contains(" ") && userName.length()>=10;
    }

    public boolean checkPassword(){
        password = registerController.getPassword();
        // messageLabel.setText("Please enter a valid password and password's length >= 12");
        return !password.isEmpty() && !password.contains(" ") && password.length()>=12;
    }

    public boolean checkRePassword(){
        rePassword = registerController.getRePassword();
        return rePassword.equals(password);
    }

    public boolean checkChequing(){
        chequing = registerController.getChequing();
       //      boolean meetAll = true;
        // check if info exist in db
//        if (userRepo.findAllChequingNumber() != null){
//            chequingNumbers = userRepo.findAllChequingNumber();
//            for(String i :chequingNumbers){
//                if (chequing.equals(i)){
//                    meetAll = false;
//                }
//            }
//        }
//        if (userRepo.existsByChequingNumber(chequing)){
//            meetAll = false;
//        }

        // return !chequing.isEmpty() && meetAll && chequing.length() ==7 && chequing.substring(0,1).equals(userName.substring(0,1));
        return chequing.length() == 7 && chequing.substring(0, 1).equals(userName.substring(0, 1));
    }

    public boolean checkPhoneNumber(){
        phoneNumber = registerController.getPhoneNumber();
 //             boolean meetAll = true;
        boolean notContainAlphabet = !(Pattern.compile("[a-zA-Z]").matcher(phoneNumber).find());
        // check if info exist in db
//        if (userRepo.findAllCardNumber() != null){
//            cardNumbers = userRepo.findAllCardNumber();
//            for(String i : cardNumbers){
//                if (cardNumber.equals(i)){
//                    meetAll = false;
//                }
//            }
//        }
//        if (userRepo.existsByCardNumber(cardNumber)){
//            meetAll = false;
//        }
        //      return cardNumber.length() ==16 && notContainAlphabet && meetAll;
        return (phoneNumber.length() >=8 && phoneNumber.length() <=16) && notContainAlphabet;
    }

    public boolean checkPin(){

            pin = registerController.getPin();
  //               boolean meetAll = true;
            boolean notContainAlphabet = !(Pattern.compile("[a-zA-z]").matcher(pin).find());
            // check if info exist in db
//            if (userRepo.findAllSavingAccount() !=null){
//                savingAccounts = userRepo.findAllSavingAccount();
//                for(String i : savingAccounts){
//                    if (savingAccount.equals(i)){
//                        meetAll = false;
//                    }
//                }
//            }
//            if (userRepo.existsBySavingAccount(savingAccount)){
//                meetAll = false;
//            }
            //    return savingAccount.length() == 16 && notContainAlphabet && meetAll;
            return pin.length() ==4 && notContainAlphabet;
    }

    public boolean checkGmail(){
        gmail = registerController.getGmail();
        return !gmail.isEmpty() && gmail.contains("@gmail.com") && gmail.length()>=16;
    }

    // check if all required information is provided
    public boolean meetAll(){
        return checkFirstName() && checkLastName()
                && checkUserName() && checkPassword()
                && checkRePassword() && checkChequing()
                && checkPhoneNumber() && checkPin()
                && checkGmail();
    }

}
