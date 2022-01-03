package org.sebasi.mep.connectomebuilder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NidUtilTest {

    static final long SIGN_REMOVAL_MASK_1_BYTE = 0x00000000000000FFL;
    static final long SIGN_REMOVAL_MASK_2_BYTE = 0x000000000000FFFFL;

    @Test
    public void testMaximums() {
        assertEquals(256, NidUtil.MAX_NUM_REGIONS);
        assertEquals(65536, NidUtil.MAX_NUM_CLUSTERS_PER_REGION);
        assertEquals(65536, NidUtil.MAX_NUM_NEURONS_PER_CLUSTER);
    }

    @Test
    public void testMakeNidsFromHexStrings() {
        byte[] nids = NidUtil.makeNidsFromStringsOfHexNids(
                "Fe00123456",
                "1234345656",
                "ee12345678");
        assertEquals("FE00123456", NidUtil.getNidHexFromNids(nids, 0));
        assertEquals("1234345656", NidUtil.getNidHexFromNids(nids, 1));
        assertEquals("EE12345678", NidUtil.getNidHexFromNids(nids, 2));
    }

    @Test
    public void testGetNidHexFromArray() {
        assertEquals("FE00120102", NidUtil.getNidHexFromNids(
                new byte[]{
                        (byte) 254,
                        (byte) 0,
                        (byte) 18,
                        (byte) 1,
                        (byte) 2},
                0));
    }

    @Test
    public void testMakeCidsFromHexStrings() {
        byte[] cids = NidUtil.makeCidsFromStringsOfHexCids(
                "Fe00",
                "1234",
                "ee12");
        assertEquals("FE00", NidUtil.getCidHexFromCids(cids, 0));
        assertEquals("1234", NidUtil.getCidHexFromCids(cids, 1));
        assertEquals("EE12", NidUtil.getCidHexFromCids(cids, 2));
    }

    @Test
    public void testMakeLidsFromHexStrings() {
        byte[] cids = NidUtil.makeLidsFromStringsOfHexLids(
                "Fe00",
                "1234",
                "ee12");
        assertEquals("FE00", NidUtil.getLidHexFromLids(cids, 0));
        assertEquals("1234", NidUtil.getLidHexFromLids(cids, 1));
        assertEquals("EE12", NidUtil.getLidHexFromLids(cids, 2));
    }

    @Test
    public void testGetRidFromNids() {
        byte[] nids = NidUtil.makeNidsFromStringsOfHexNids(
                "Fe00123456",
                "1234345656",
                "ee12345678");
        assertEquals((byte) 0xFE, NidUtil.getRidFromNids(nids, 0));
        assertEquals((byte) 0x12, NidUtil.getRidFromNids(nids, 1));
        assertEquals((byte) 0xEE, NidUtil.getRidFromNids(nids, 2));
    }

    @Test
    public void testGetCidFromNids() {
        byte[] nids = NidUtil.makeNidsFromStringsOfHexNids(
                "Fe00123456",
                "1234345656",
                "ee12345678");
        assertEquals((short) 0x0012, NidUtil.getCidFromNids(nids, 0));
        assertEquals((short) 0x3434, NidUtil.getCidFromNids(nids, 1));
        assertEquals((short) 0x1234, NidUtil.getCidFromNids(nids, 2));
    }

    @Test
    public void testGetLidFromNids() {
        byte[] nids = NidUtil.makeNidsFromStringsOfHexNids(
                "Fe00123456",
                "1234345656",
                "ee12345678");
        assertEquals((short) 0x3456, NidUtil.getLidFromNids(nids, 0));
        assertEquals((short) 0x5656, NidUtil.getLidFromNids(nids, 1));
        assertEquals((short) 0x5678, NidUtil.getLidFromNids(nids, 2));
    }

    @Test
    public void testGetRidFromHexString() {
        assertEquals(4, NidUtil.getRidFromHexString("04"));
        assertEquals(-2, NidUtil.getRidFromHexString("fe"));
        assertEquals(4L, NidUtil.getRidFromHexString("04") & SIGN_REMOVAL_MASK_1_BYTE);
        assertEquals(254L, NidUtil.getRidFromHexString("fe") & SIGN_REMOVAL_MASK_1_BYTE);
    }

    @Test
    public void testGetCidFromHexString() {
        assertEquals(4, NidUtil.getCidFromHexString("0004"));
        assertEquals(-2, NidUtil.getCidFromHexString("fffe"));
        assertEquals(4L, NidUtil.getCidFromHexString("0004") & SIGN_REMOVAL_MASK_2_BYTE);
        assertEquals(65534L, NidUtil.getCidFromHexString("fffe") & SIGN_REMOVAL_MASK_2_BYTE);
    }

    @Test
    public void testGetLidFromHexString() {
        assertEquals(4, NidUtil.getLidFromHexString("0004"));
        assertEquals(4L, NidUtil.getLidFromHexString("0004") & SIGN_REMOVAL_MASK_2_BYTE);
        assertEquals(-2, NidUtil.getLidFromHexString("fffe"));
        assertEquals(65534L, NidUtil.getLidFromHexString("fffe") & SIGN_REMOVAL_MASK_2_BYTE);
    }
}
