package com.example.kartik.tutorfinder;

import android.content.Context;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by kartik on 02-02-2018.
 */

public class Validation {
    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
   private static String pass,conPass;
    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";
    private static final String PASS_ERR = "length must be atleast 6";
    private static final String CON_PASS_ERR="Confirm password doesn't match";

   private static boolean passval=false;

      // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }
    // validating password with retype password
    public static boolean isPassword(EditText editText, boolean required)
    {
        if(isValidPass(editText,PASS_ERR,required))
        passval=isValidPass(editText,PASS_ERR,required);
        return passval;
    }

    public static boolean isConfirmPassword(CharSequence charSeq,EditText editText, EditText originalPass, boolean required)
    {
        return isMatchPassword(charSeq,editText,originalPass,CON_PASS_ERR,required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
        String text= editText.getText().toString().trim();
        editText.setError(null);
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
        // pattern doesn't match so returning false

        if (required &&(!Pattern.matches(regex, text) || text==null)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    public static boolean isValidPass(EditText editText,String errMsg, boolean required)
    {
        String pass = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ){ return false;}
        if (required && (pass==null || pass.length()<6))
        {
            editText.setError(errMsg);
            return false;
        }
        else {
            conPass=pass;

            return true;
        }
    }

  public static boolean isMatchPassword(CharSequence charSeq,EditText confirm,EditText original,String errMsg,boolean required) {
      String con = charSeq.toString().trim();
      String org = original.getText().toString().trim();
      confirm.setError(null);
      if ( required && !hasText(confirm) ){ return false;}
      if (isPassword(original, true)) {
          if (con.length() == org.length()) {
              if (con.equals(org)) {
                  return true;
              }
              else {
                  confirm.setError(CON_PASS_ERR);
                  return false;
              }
          }
          else {
              confirm.setError(CON_PASS_ERR);
              return false;

          }

      }
      else {
          original.setError(null);
          original.setError("Enter the password first.");
          return false;
      }
  }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0 || text==null) { editText.setError(REQUIRED_MSG);

            return false;
        }

        return true;
    }
}
