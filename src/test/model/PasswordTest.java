package model;

import org.junit.jupiter.api.Test;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.HashSet;


import static model.Password.generatePass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*
https://stackoverflow.com
/questions/40336374/how-do-i-check-if-a-java-string-contains-at-least-one-capital-letter-lowercase
https://www.javatpoint.com/java-regex
Sources for figuring out regex
 */
public class PasswordTest {
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()_+-=\\[\\]|,./?><]).*$";



    @Test
    public void testConstructor(){
        Password password = new Password("Sauce", "Saucer");
        assertEquals("Sauce", password.getTitle());
        assertEquals("Saucer", password.getPassword());
        password.setPassword("Sauce");
        assertEquals("Sauce", password.getPassword());
        password.setTitle("Saucer");
        assertEquals("Saucer", password.getTitle());
    }

    //Non-exhaustive test, goes up to 1000 to test for duplicates using cast to hashset
    //Also tests to make sure length is correct for each one
    //Uses regex to make sure generated password has all criteria fulfilled
    //reasoning that at least half should contain all the categories for a sound generator so assert > 500 matching
    //First test is general, all other ones test each of the parameters
    @Test
    public void testGenPass() {
        ArrayList<String> str = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        int matching = 0;
        for(int i = 0; i < 1000; i++){
            String s = generatePass(true, true, true, true, 7);
            assertEquals(7,s.length());
            str.add(s);
        }
        for(String s : str){
            Matcher m = p.matcher(s);
            if(m.matches()){
                matching ++;
            }
        }
        HashSet<String> str2 = new HashSet<>(str);
        assertEquals(1000,str2.size());
        assertTrue(matching >= 500);
    }

    @Test
    public void testCapGen(){
        ArrayList<String> str = new ArrayList<>();
        boolean flag = true;
        for(int i = 0; i < 100; i++){
            String s = generatePass(true, false, false, false, 5);
            str.add(s);
        }
        for(String s: str){
            for(int i=0; i<s.length(); i++)
                if(!Character.isUpperCase(s.charAt(i))){
                    flag = false;
                    break;
                }

        }
        assertTrue(flag);
    }

    @Test
    public void testLowGen(){
        ArrayList<String> str = new ArrayList<>();
        boolean flag = true;
        for(int i = 0; i < 100; i++){
            String s = generatePass(false, true, false, false, 5);
            str.add(s);
        }
        for(String s: str){
            for(int i=0; i<s.length(); i++)
                if(!Character.isLowerCase(s.charAt(i))){
                    flag = false;
                    break;
                }

        }
        assertTrue(flag);
    }

    @Test
    public void testNumGen(){
        ArrayList<String> str = new ArrayList<>();
        boolean flag = true;
        for(int i = 0; i < 100; i++){
            String s = generatePass(false, false, true, false, 5);
            str.add(s);
        }
        for(String s: str){
            for(int i=0; i<s.length(); i++)
                if(!Character.isDigit(s.charAt(i))){
                    flag = false;
                    break;
                }

        }
        assertTrue(flag);
    }

    @Test
    public void testSymGen(){
        ArrayList<String> str = new ArrayList<>();
        Pattern p = Pattern.compile("[!@#$%&*()_+-=\\[\\]|,./?><]+");
        boolean flag = true;
        for(int i = 0; i < 100; i++){
            String s = generatePass(false, false, false, true, 5);
            str.add(s);
        }
        for(String s: str){
                if(!(p.matcher(s).matches())){
                    flag = false;
                    break;
                }

        }
        assertTrue(flag);
    }
}
