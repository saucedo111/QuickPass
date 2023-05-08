package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    private HashSet<Password> passwords;



    @BeforeEach
    public void setUp() {
        user = new User("Joe", "123");
        EventLog.getInstance().clear();
        Iterator<Event> it = EventLog.getInstance().iterator();
        it.next();
        it.remove();
    }

    @Test
    public void testConstructor(){
        assertEquals("Joe", user.getId());
        assertEquals("123", user.getPassword());
        assertEquals(0, user.getPasswords().size());
    }

    @Test
    public void testCheckContains() {
        user.addPass(new Password("Joe", "123"));
        user.addPass(new Password("Joey", "1234"));
        ArrayList<Password> ps = new ArrayList<>(user.getPasswords());
        assertTrue(user.checkContain("Joe", "123", ps));
        assertFalse(user.checkContain("Je", "123", ps));
        assertFalse(user.checkContain("Joe", "12", ps));
        assertFalse(user.checkContain("Joe", "1234", ps));
        assertFalse(user.checkContain("Joey", "123", ps));
        assertTrue(user.checkContain("Joey", "1234", ps));
    }
    @Test
    public void testAddPass() {
        assertEquals(0, user.getPasswords().size());
        Password ps = new Password("Joe", "Joey");
        user.addPass(ps);
        assertEquals(1, user.getPasswords().size());
        passwords = user.getPasswords();
        assertTrue(passwords.contains(ps));
        assertEquals("Password " + "Joe" + " added.",EventLog.getInstance().iterator().next().getDescription());
        Password ps2 = new Password("Joey", "Joe");
        user.addPass(ps2);
        assertEquals(2, user.getPasswords().size());
        passwords = user.getPasswords();
        int count = 0;
        for (Event ev : EventLog.getInstance()) {
            count++;
        }
        assertEquals(2, count);
        assertTrue(passwords.contains(ps2));
        assertTrue(passwords.contains(ps));
    }

    @Test
    public void testRemovePass() {
        assertEquals(0, user.getPasswords().size());
        Password ps = new Password("Joe", "Joey");
        user.addPass(ps);
        Password ps2 = new Password("Joey", "Joe");
        user.addPass(ps2);
        user.removePass(ps);
        assertEquals(1, user.getPasswords().size());
        int count = 0;
        for (Event e : EventLog.getInstance()) {
            count++;
        }
        assertEquals(3, count);
        Iterator<Event> it = EventLog.getInstance().iterator();
        it.next();
        it.next();
        assertEquals("Password " + "Joe" + " removed.",it.next().getDescription());
        passwords = user.getPasswords();
        assertTrue(passwords.contains(ps2));
    }

    @Test
    public void testFindPass() {
        Password ps = new Password("Joe", "Joey");
        user.addPass(ps);
        Password ps2 = new Password("Joey", "Joe");
        user.addPass(ps2);
        assertEquals(ps2, user.findPass("Joey"));
        assertEquals(ps, user.findPass("Joe"));
        assertEquals(null, user.findPass("Jo"));

    }

    @Test
    public void testLog() {
        user.logEvent("Passwords displayed");
        user.logEvent("Passwords saved");
        Iterator<Event> it = EventLog.getInstance().iterator();
        assertEquals("Passwords displayed",it.next().getDescription());
        assertEquals("Passwords saved",it.next().getDescription());

    }





}