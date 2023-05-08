package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    // Copied from given Json Demo
    JSONObject toJson();
}
