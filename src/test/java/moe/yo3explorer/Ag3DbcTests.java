package moe.yo3explorer;

import moe.yo3explorer.azusa.js3db.boundary.Js3DbRevivalResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ag3DbcTests {
    @Test
    public void unobfuscationTest()
    {
        String obfuscated = "CED1CFCED1CD";
        String actual = new Js3DbRevivalResource().ag3dbcUnobfuscate(obfuscated);
        String expected = "1.01.2";
        Assertions.assertEquals(expected,actual);
    }
}
