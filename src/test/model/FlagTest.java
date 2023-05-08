package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlagTest {
    @Test
    public void testConstructor() {
        Flag flag = new Flag();
        assertTrue(flag.getFlag());
        flag.setFlag(false);
        assertFalse(flag.getFlag());
    }
}
