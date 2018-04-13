package tp1.g3.tdp2.hoycomo.Helpers;


import android.util.Patterns;

public class Validator
{
    public boolean isNameValid(String name)
    {
        char[] chars = name.toCharArray();
        for (char c : chars)
        {
            if(!Character.isLetter(c))
            {
                return false;
            }
        }
        if (name.length() > 5){
            return false;
        }
        return true;
    }

    public boolean isEmailValid(String email)
    {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isPasswordValid(String password)
    {
        return (password.length() > 4);
    }
}