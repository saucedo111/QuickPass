package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
Class to represent passwords, contains parameters to set passwords,
methods that modify THE PASSWORD itself go under here
 */

public class Password implements Writable {
    private String title;
    private String password;
    private static final String capLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowLetters = "abcdefghijklmnopqrstuvwxyz";
    private static final String numbers = "0123456789";
    private static final String symbols = "!@#$%&*()_+-=[]|,./?><";


    public Password(String name, String pass) {
        this.title = name;
        this.password = pass;
    }

    //note that this can not guarantee that every parameter is satisfied
    //only that it can at least achieve them
    /*
    https://stackoverflow.com/questions/19743124/java-password-generator
    Sources for inspiration of implementation of password generation
    */
    //REQUIRES: At least one of the category is selected
    //EFFECTS: generates passwords with set parameters
    public static String generatePass(Boolean cap, Boolean low, Boolean num, Boolean sym, int length) {
        List<String> available = new ArrayList<>();
        String pass = "";
        Random random = new Random();
        if (cap) {
            available.add(capLetters);
        }
        if (low) {
            available.add(lowLetters);
        }
        if (num) {
            available.add(numbers);
        }
        if (sym) {
            available.add(symbols);
        }
        for (int i = 0; i < length; i++) {
            int randomCat = random.nextInt(available.size());
            int randomLetter = random.nextInt(available.get(randomCat).length());
            pass = pass + (available.get(randomCat).charAt(randomLetter));
        }
        return pass;
    }


    //EFFECTS: return this as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("password", password);
        return json;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
