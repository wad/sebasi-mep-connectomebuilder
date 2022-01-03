package org.sebasi.mep.connectomebuilder.component;

/*
Neuron ID. See design document for more information.
This class includes methods for the three components of NIDs: RIDs CIDs and LIDs.

Note that a NID, RIC, CID, and LID aren't actually objects anywhere, they are just a way to understand
some raw bytes, which are stored in byte arrays. This is for efficient memory usage and performance.

This addressing scheme allows for about 1 trillion neurons to be in communication with each other.
Since the human brain has about 128 billion neurons, our maximum neuron count is less than one order
of magnitude more than that, which seems pretty good. If we need more, we can add another byte for regions,
and bring the maximum up to about 256 trillion neurons.
*/
public class NidUtil {

    // Region ID. 0xRR--------
    static final int NUM_BYTES_IN_RID = 1;

    // Cluster ID. Fits in a `short`. 0x--CCCC----
    static final int NUM_BYTES_IN_CID = 2;

    // Local ID. Fits in a `short`. 0x------LLLL
    static final int NUM_BYTES_IN_LID = 2;

    // The full NID is all of these together: 0xRRCCCCLLLL
    // Fits in a `long` but leaves 3 extra unused bytes.
    static final int NUM_BYTES_IN_NID = NUM_BYTES_IN_RID + NUM_BYTES_IN_CID + NUM_BYTES_IN_LID;

    // Each string must be the correct length, 5 bytes long, ten characters, with hexadecimal values.
    public static byte[] makeNidsFromStringsOfHexNids(String... hexStrings) {
        byte[] nids = new byte[hexStrings.length * NUM_BYTES_IN_NID];
        int indexIntoNids = 0;
        for (String hexString : hexStrings) {
            if (hexString.length() != (NUM_BYTES_IN_NID * 2)) {
                throw new RuntimeException("Invalid NID hex string length found: " + hexString.length() + " '" + hexString + "'");
            }
            for (int i = 0; i < NUM_BYTES_IN_NID; i++) {
                nids[indexIntoNids++] = stringToByte(hexString.substring(i << 1, (i << 1) + 2));
            }
        }
        return nids;
    }

    // Each string must be the correct length, 5 bytes long, ten characters, with hexadecimal values.
    public static byte[] makeCidsFromStringsOfHexCids(String... hexStrings) {
        byte[] cids = new byte[hexStrings.length * NUM_BYTES_IN_CID];
        int indexIntoCids = 0;
        for (String hexString : hexStrings) {
            if (hexString.length() != (NUM_BYTES_IN_CID * 2)) {
                throw new RuntimeException("Invalid CID hex string length found: " + hexString.length() + " '" + hexString + "'");
            }
            for (int i = 0; i < NUM_BYTES_IN_CID; i++) {
                cids[indexIntoCids++] = stringToByte(hexString.substring(i << 1, (i << 1) + 2));
            }
        }
        return cids;
    }

    // Each string must be the correct length, 5 bytes long, ten characters, with hexadecimal values.
    public static byte[] makeLidsFromStringsOfHexLids(String... hexStrings) {
        byte[] lids = new byte[hexStrings.length * NUM_BYTES_IN_LID];
        int indexIntoLids = 0;
        for (String hexString : hexStrings) {
            if (hexString.length() != (NUM_BYTES_IN_LID * 2)) {
                throw new RuntimeException("Invalid LID hex string length found: " + hexString.length() + " '" + hexString + "'");
            }
            for (int i = 0; i < NUM_BYTES_IN_LID; i++) {
                lids[indexIntoLids++] = stringToByte(hexString.substring(i << 1, (i << 1) + 2));
            }
        }
        return lids;
    }

    public static String getNidHexFromNids(
            byte[] bytesHoldingNids,
            int indexOfNidNotByte) {
        int byteIndex = getByteIndexOfNidWithValidation(bytesHoldingNids, indexOfNidNotByte);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < NUM_BYTES_IN_NID; i++) {
            builder.append(byteToString(bytesHoldingNids[byteIndex++]));
        }
        return builder.toString();
    }

    public static String getCidHexFromCids(
            byte[] bytesHoldingCids,
            int indexOfCidNotByte) {
        int byteIndex = getByteIndexOfCidWithValidation(bytesHoldingCids, indexOfCidNotByte);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < NUM_BYTES_IN_CID; i++) {
            builder.append(byteToString(bytesHoldingCids[byteIndex++]));
        }
        return builder.toString();
    }

    public static String getLidHexFromLids(
            byte[] bytesHoldingLids,
            int indexOfLidNotByte) {
        int byteIndex = getByteIndexOfLidWithValidation(bytesHoldingLids, indexOfLidNotByte);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < NUM_BYTES_IN_LID; i++) {
            builder.append(byteToString(bytesHoldingLids[byteIndex++]));
        }
        return builder.toString();
    }

    public static byte getRidFromNids(
            byte[] bytesHoldingNids,
            int indexOfNidNotByte) {
        int byteIndex = getByteIndexOfNidWithValidation(bytesHoldingNids, indexOfNidNotByte);
        return bytesHoldingNids[byteIndex];
    }

    public static short getCidFromNids(
            byte[] bytesHoldingNids,
            int indexOfNidNotByte) {
        int byteIndex = getByteIndexOfNidWithValidation(bytesHoldingNids, indexOfNidNotByte);
        return (short) ((bytesHoldingNids[byteIndex + 1] << 8) + bytesHoldingNids[byteIndex + 2]);
    }

    public static short getLidFromNids(
            byte[] bytesHoldingNids,
            int indexOfNidNotByte) {
        int byteIndex = getByteIndexOfNidWithValidation(bytesHoldingNids, indexOfNidNotByte);
        return (short) ((bytesHoldingNids[byteIndex + 3] << 8) + bytesHoldingNids[byteIndex + 4]);
    }

    public static byte getRidFromHexString(String ridString) {
        if (ridString.length() != NUM_BYTES_IN_RID * 2) {
            throw new RuntimeException("Invalid RID specified: '" + ridString + "'");
        }
        return stringToByte(ridString);
    }

    public static short getCidFromHexString(String cidString) {
        if (cidString.length() != NUM_BYTES_IN_CID * 2) {
            throw new RuntimeException("Invalid CID specified: '" + cidString + "'");
        }
        return (short)((stringToByte(cidString.substring(0, 2)) << 8) + stringToByte(cidString.substring(2, 4)));
    }

    public static short getLidFromHexString(String lidString) {
        if (lidString.length() != NUM_BYTES_IN_LID * 2) {
            throw new RuntimeException("Invalid LID specified: '" + lidString + "'");
        }
        return (short)((stringToByte(lidString.substring(0, 2)) << 8) + stringToByte(lidString.substring(2, 4)));
    }

    public static String convertRidToHexString(byte rid) {
        return byteToString(rid);
    }

    public static String convertCidToHexString(short cid) {
        return byteToString((byte)(cid >> 8)) + byteToString((byte)(cid & 0xFF));
    }

    public static String convertLidToHexString(short lid) {
        return byteToString((byte)(lid >> 8)) + byteToString((byte)(lid & 0xFF));
    }

    static int getByteIndexOfNidWithValidation(
            byte[] bytesHoldingNids,
            int indexOfNidNotByte) {
        if (bytesHoldingNids.length % NUM_BYTES_IN_NID != 0) {
            throw new RuntimeException("Invalid length of NID byte array: " + bytesHoldingNids.length);
        }
        int byteIndex = indexOfNidNotByte * NUM_BYTES_IN_NID;
        if (byteIndex >= bytesHoldingNids.length) {
            throw new RuntimeException("Specified NID would be outside of byte array. (Note: use index of NID, not index of BYTE.) NidIndex=" + indexOfNidNotByte);
        }
        return byteIndex;
    }

    static int getByteIndexOfCidWithValidation(
            byte[] bytesHoldingCids,
            int indexOfCidNotByte) {
        if (bytesHoldingCids.length % NUM_BYTES_IN_CID != 0) {
            throw new RuntimeException("Invalid length of CID byte array: " + bytesHoldingCids.length);
        }
        int byteIndex = indexOfCidNotByte * NUM_BYTES_IN_CID;
        if (byteIndex >= bytesHoldingCids.length) {
            throw new RuntimeException("Specified CID would be outside of byte array. (Note: use index of CID, not index of BYTE.) CidIndex=" + indexOfCidNotByte);
        }
        return byteIndex;
    }

    static int getByteIndexOfLidWithValidation(
            byte[] bytesHoldingLids,
            int indexOfLidNotByte) {
        if (bytesHoldingLids.length % NUM_BYTES_IN_LID != 0) {
            throw new RuntimeException("Invalid length of LID byte array: " + bytesHoldingLids.length);
        }
        int byteIndex = indexOfLidNotByte * NUM_BYTES_IN_LID;
        if (byteIndex >= bytesHoldingLids.length) {
            throw new RuntimeException("Specified LID would be outside of byte array. (Note: use index of LID, not index of BYTE.) LidIndex=" + indexOfLidNotByte);
        }
        return byteIndex;
    }

    static String byteToString(byte b) {
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
}
