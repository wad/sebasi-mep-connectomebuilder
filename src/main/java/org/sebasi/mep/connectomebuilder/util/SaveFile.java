package org.sebasi.mep.connectomebuilder.util;

public class SaveFile {

    enum SaveFileVersion {
        initial
    }

    private SaveFileVersion saveFileVersion = SaveFileVersion.initial;
    private String filenameWithPath;

    public SaveFile(String filenameWithPath) {
        this.filenameWithPath = filenameWithPath;
    }

    public void prepareForSaving(boolean isNewFile) {
        // todo
    }

    public void prepareForLoading() {
        // todo
    }
}
