package org.sebasi.mep.connectomebuilder.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.sebasi.mep.connectomebuilder.util.SaveFileMagicNumber.NUM_BYTES_IN_MAGIC_NUMBER;

public class SaveFile {

    private enum WhatAreWeDoingWithThisFile {nothing, saving, loading}

    private WhatAreWeDoingWithThisFile whatAreWeDoingWithThisFile = WhatAreWeDoingWithThisFile.nothing;

    private SaveFileMagicNumber saveFileVersion = SaveFileMagicNumber.DEFAULT_VERSION;
    private final String filenameWithPath;
    private FileOutputStream fileOutputStream = null;
    private FileInputStream fileInputStream = null;

    public SaveFile(String filenameWithPath) {
        if (StringUtils.isEmpty(filenameWithPath)) {
            throw new RuntimeException("Cannot accept an empty filename for the save file.");
        }
        this.filenameWithPath = filenameWithPath;
    }

    public void setSaveFileVersion(SaveFileMagicNumber saveFileVersion) {
        this.saveFileVersion = saveFileVersion;
    }

    public void prepareForSaving() {

        if (whatAreWeDoingWithThisFile != WhatAreWeDoingWithThisFile.nothing) {
            throw new RuntimeException("Invalid state. You have a bug!");
        }
        whatAreWeDoingWithThisFile = WhatAreWeDoingWithThisFile.saving;

        try {
            File file = new File(filenameWithPath);
            if (!file.createNewFile()) {
                throw new RuntimeException("File " + filenameWithPath + " already exists, not overwriting it.");
            }

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(saveFileVersion.generateMagicNumber());

        } catch (IOException e) {
            closeEverything();
            throw new RuntimeException("Failed to create a new file for saving: " + filenameWithPath, e);
        }
    }

    public SaveFileMagicNumber prepareForLoading() {

        if (whatAreWeDoingWithThisFile != WhatAreWeDoingWithThisFile.nothing) {
            throw new RuntimeException("Invalid state. You have a bug.");
        }
        whatAreWeDoingWithThisFile = WhatAreWeDoingWithThisFile.loading;

        try {
            File file = new File(filenameWithPath);

            if (!file.exists() || file.isDirectory() || !file.canRead()) {
                throw new RuntimeException("Problem loading file '" + filenameWithPath
                        + "', it doesn't exist, is a directory, or I can't read it.");
            }

            fileInputStream = new FileInputStream(file);
            return SaveFileMagicNumber.determine(fileInputStream.readNBytes(NUM_BYTES_IN_MAGIC_NUMBER));

        } catch (IOException e) {
            closeEverything();
            throw new RuntimeException("Failed to open a file for loading: " + filenameWithPath, e);
        }
    }

    public void closeEverything() {
        try {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable t) {
            System.out.println("Eating exception while closing everything: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
