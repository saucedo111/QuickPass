package persistence;

import model.Password;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("My work room", "0");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("My work room", "0");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals("My work room", user.getId());
            assertEquals("0", user.getPassword());
            assertEquals(0, user.getPasswords().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("My work room", "0");
            user.addPass(new Password("saw","METALWORK"));
            user.addPass(new Password("needle", "STITCHING"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("My work room", user.getId());
            assertEquals("0", user.getPassword());
            ArrayList<Password> ps = new ArrayList<>(user.getPasswords());
            assertEquals(2, ps.size());
            // Hashset doesn't guarantee return order can only check size and both contains
            assertTrue(user.checkContain("needle", "STITCHING", ps));
            assertTrue(user.checkContain("saw", "METALWORK", ps));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}