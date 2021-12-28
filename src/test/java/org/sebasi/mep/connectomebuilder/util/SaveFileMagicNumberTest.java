package org.sebasi.mep.connectomebuilder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveFileMagicNumberTest {
    @Test
    public void testMagicNumberBytes() {
        byte[] bytes = SaveFileMagicNumber.initial_1.generateMagicNumber();
        assertEquals(4, bytes.length);
        assertEquals('S', bytes[0]);
        assertEquals('E', bytes[1]);
        assertEquals('B', bytes[2]);
        assertEquals('1', bytes[3]);
    }
}
