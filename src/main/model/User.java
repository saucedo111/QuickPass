package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashSet;

/*
Class to represent user data, contains user log-on info(to be used when persistence matters),
in addition to passwords belong to the user, methods that modify THE LIST of passwords go under here
 */

public class User implements Writable {
    private static String id;
    private String password;
    private HashSet<Password> passwords;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        passwords = new HashSet<Password>();
    }



    // REQUIRES: Password is not duplicate to prevent hashmap error
    // MODIFIES: this
    // EFFECTS: adds password to user passwords with given parameter(helps with testing)
    public void addPass(Password ps) {
        passwords.add(ps);
        EventLog.getInstance().logEvent(new Event("Password " + ps.getTitle() + " added."));
    }


    // MODIFIES: this
    // EFFECTS: removes password from user passwords
    public void removePass(Password p) {
        passwords.remove(p);
        EventLog.getInstance().logEvent(new Event("Password " + p.getTitle() + " removed."));
    }


    // EFFECTS: finds and returns first password with given name, null if none found
    public Password findPass(String name) {
        for (Password p : this.passwords) {
            if (p.getTitle().equals(name)) {
                return p;
            }

        }
        return null;
    }

    //EFFECTS: return this as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("password", password);
        json.put("passwords", passwordsToJson());
        return json;
    }

    // EFFECTS: returns passwords for this user as a JSON array
    private JSONArray passwordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Password p : passwords) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns true if given arraylist contains password with given title and password, false if not found
    public boolean checkContain(String title, String password, ArrayList<Password> ps) {
        for (Password p: ps) {
            if ((p.getTitle().equals(title) && p.getPassword().equals(password))) {
                return true;
            }
        }
        return false;
    }


    public static String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public HashSet<Password> getPasswords() {
        return passwords;
    }

    // MODIFIES: theLog
    // EFFECTS: abstract function to add event to log
    public void logEvent(String s) {
        EventLog.getInstance().logEvent(new Event(s));
    }

}
