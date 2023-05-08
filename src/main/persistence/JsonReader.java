package persistence;


import model.Password;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads user from JSON data stored in file
// Copied mostly from given Json Demo, reworked to fit with own needs
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("id");
        String passWord = jsonObject.getString("password");
        User user = new User(name, passWord);
        addPasswords(user, jsonObject);
        return user;
    }

    // MODIFIES: User
    // EFFECTS: parses passwords from JSON object and adds them to user
    private void addPasswords(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("passwords");
        for (Object json : jsonArray) {
            JSONObject nextPass = (JSONObject) json;
            addPass(user, nextPass);
        }
    }

    // MODIFIES: User
    // EFFECTS: parses password from JSON object and adds it to user passwords
    private void addPass(User user, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String password = jsonObject.getString("password");
        Password pass = new Password(title, password);
        user.addPass(pass);
    }
}
