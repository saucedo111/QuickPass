package persistence;


import model.Password;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("My work room", user.getId());
            assertEquals(0, user.getPasswords().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("My work room", user.getId());
            ArrayList<Password> ps = new ArrayList<>(user.getPasswords());
            assertEquals(2, ps.size());
            // Hashset doesn't guarantee return order can only check size and both contains
            assertTrue(user.checkContain("needle", "STITCHING", ps));
            assertTrue(user.checkContain("saw", "METALWORK", ps));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}