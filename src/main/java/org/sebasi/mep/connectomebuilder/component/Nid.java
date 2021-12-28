package org.sebasi.mep.connectomebuilder.component;

// This is a neuron ID.
public class Nid extends AbstractComponent {

    // Why 5 bytes?
    // Four bytes gives us a max value of about 4 billion.
    // The human brain has about 128 billion neurons.
    // Five bytes gives us a max value of about 1 trillion, should be plenty.
    static int NUM_BYTES_IN_NID = 5;
    static int STRING_VALUE_LENGTH = (NUM_BYTES_IN_NID * 2) + (NUM_BYTES_IN_NID - 1);

    static {
        //noinspection ConstantConditions
        if (NUM_BYTES_IN_NID <= 1) {
            throw new RuntimeException("Need at least one byte in Nid.");
        }
    }

    private byte[] bytes = new byte[NUM_BYTES_IN_NID];

    // In format "aa.bb.cc.dd.ee"
    public Nid(String value) {

        if (value == null) {
            throw new RuntimeException("Not accepting null.");
        }

        if (value.length() != STRING_VALUE_LENGTH) {
            throw new RuntimeException("Invalid nid value length: '" + value + "'");
        }

        String[] split = value.split("\\.");
        if (split.length != NUM_BYTES_IN_NID) {
            throw new RuntimeException("Invalid nid value: '" + value + "'");
        }

        for (int i = 0; i < NUM_BYTES_IN_NID; i++) {
            bytes[i] = stringToByte(split[i]);
        }
    }

    // bytes in order of most significant, to least significant
    public Nid(byte... segments) {

        if (segments == null) {
            throw new RuntimeException("Not accepting null.");
        }

        if (segments.length != NUM_BYTES_IN_NID) {
            throw new RuntimeException("Invalid nid value length: " + segments.length);
        }

        System.arraycopy(segments, 0, bytes, 0, NUM_BYTES_IN_NID);
    }

    // 0 is the most significant (leftmost) byte.
    public byte getSegmentValue(int segmentIndex) {
        if (segmentIndex < 0 || segmentIndex >= NUM_BYTES_IN_NID) {
            throw new RuntimeException("Invalid segment index: " + segmentIndex);
        }

        return bytes[segmentIndex];
    }

    @Override
    public String toString() {
        StringBuilder asString = new StringBuilder();
        for (int i = 0; i < NUM_BYTES_IN_NID; i++) {
            asString.append(byteToString(bytes[i]));
            if (i < (NUM_BYTES_IN_NID - 1)) {
                asString.append(".");
            }
        }
        return asString.toString();
    }

    public static String byteToString(byte b) {
        return String.format("%02X", b);
    }

    public static byte stringToByte(String s) {
        if (s == null || s.length() != 2) {
            throw new RuntimeException("Invalid byte received: '" + s + "'");
        }

        return (byte) ((getBitsForHexDigit(s.charAt(0)) << 4) + getBitsForHexDigit(s.charAt(1)));
    }

    static byte getBitsForHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        }
        if (c >= 'A' && c <= 'F') {
            return (byte) (c - 'A' + 10);
        }
        if (c >= 'a' && c <= 'f') {
            return (byte) (c - 'a' + 10);
        }
        throw new RuntimeException("Illegal hexadecimal character: '" + c + "'");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nid nid = (Nid) o;
        for (int i = 0; i < NUM_BYTES_IN_NID; i++) {
            if (bytes[i] != nid.bytes[i]) {
                return false;
            }
        }
        return true;
    }

    // For the hash code, we'll most likely not be comparing stuff with the most significant byte different.
    @Override
    public int hashCode() {
        switch (NUM_BYTES_IN_NID) {
            case 1:
                return bytes[0];
            case 2:
                return bytes[1] + (bytes[0] << 8);
            case 3:
                return bytes[2] + (bytes[1] << 8) + (bytes[2] << 16);
            default:
                return bytes[3] + (bytes[2] << 8) + (bytes[1] << 16) + (bytes[0] << 24);
        }
    }
}
