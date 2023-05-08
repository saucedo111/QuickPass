package persistence;


import model.Password;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkPass(String title, String pass, Password p) {
        assertEquals(title, p.getTitle());
        assertEquals(pass, p.getPassword());
    }
}
