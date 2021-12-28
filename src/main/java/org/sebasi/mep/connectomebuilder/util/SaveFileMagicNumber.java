package org.sebasi.mep.connectomebuilder.util;

public enum SaveFileMagicNumber {

    // Don't go back and change versions, unless you're okay with breaking backwards compatibility on previous save files.
    initial_1('1');

    public static final SaveFileMagicNumber DEFAULT_VERSION = initial_1;

    private final byte versionByte;

    private static final String MAGIC_NUMBER = "SEB";
    public static final int NUM_BYTES_IN_MAGIC_NUMBER = MAGIC_NUMBER.length() + 1;

    SaveFileMagicNumber(char versionByte) {
        this.versionByte = (byte)versionByte;
    }

    public byte[] generateMagicNumber() {
        byte[] magicNumberAsBytes = new byte[NUM_BYTES_IN_MAGIC_NUMBER];
        for (int i = 0; i < MAGIC_NUMBER.length(); i++) {
            magicNumberAsBytes[i] = (byte)(MAGIC_NUMBER.charAt(i));
        }
        magicNumberAsBytes[NUM_BYTES_IN_MAGIC_NUMBER - 1] = this.versionByte;
        return magicNumberAsBytes;
    }

    public static SaveFileMagicNumber determine(byte[] bytes) {
        if (bytes.length != NUM_BYTES_IN_MAGIC_NUMBER) {
            throw new RuntimeException("Invalid number of bytes in magic number." +
                    " Got " + bytes.length + " but expected " + NUM_BYTES_IN_MAGIC_NUMBER + ".");
        }

        for (int i = 0; i < MAGIC_NUMBER.length(); i++) {
            if ((byte)(MAGIC_NUMBER.charAt(i)) != bytes[i]) {
                throw new RuntimeException("Invalid magic number detected in save file.");
            }
        }

        byte versionByteFound = bytes[MAGIC_NUMBER.length()];
        for (SaveFileMagicNumber value : values()) {
            if (value.versionByte == versionByteFound) {
                return value;
            }
        }

        // This could be a non-printable char, but whatever.
        throw new RuntimeException("Unsupported version detected in save file: '" + (char)(versionByteFound) + "'.");
    }
}
