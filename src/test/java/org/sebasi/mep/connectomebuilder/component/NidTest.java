package org.sebasi.mep.connectomebuilder.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NidTest {

    @Test
    public void verifyAcceptableNumberOfBytes() {
        assertTrue(Nid.NUM_BYTES_IN_NID < Long.BYTES);
        assertTrue(Nid.NUM_BYTES_IN_NID >= 1);
    }

    @Test
    public void testWithStrings() {
        assertEquals("FE.00.12.34.56", new Nid("Fe.00.12.34.56").toString());
        assertEquals("FE.00.12.01.02", new Nid(
                (byte) 254,
                (byte) 0,
                (byte) 18,
                (byte) 1,
                (byte) 2).toString());
    }
}
