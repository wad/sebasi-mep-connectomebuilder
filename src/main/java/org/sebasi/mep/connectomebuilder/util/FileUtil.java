package org.sebasi.mep.connectomebuilder.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class FileUtil {

    private static final String FILENAME_CGS = "cgs.json";
    private static final String DIRNAME_CONTROL = "control";
    private static final int FILENAME_READY_LENGTH = "rXX.ready".length();
    private static final int FILENAME_SHUTDOWN_LENGTH = "rXX.shutdown".length();
    private static final String DIRNAME_ROOM = "room";
    private static final String FILENAME_ROOM = "room.json";
    private static final String DIRNAME_BODY = "body";
    private static final String FILENAME_BODY = "body.json";
    private static final String DIRNAME_BRAIN = "brain";
    private static final String FILENAME_BRAIN = "brain.json";
    private static final int DIRNAME_REGION_LENGTH = "rXX".length();
    private static final String FILENAME_REGION = "region.json";
    private static final int DIRNAME_CLUSTER_LENGTH = "cXXXX".length();
    private static final int FILENAME_NEURON_LENGTH = "XXXX.n".length();
    private static final int FILENAME_DENDRITIC_TREE_LENGTH = "XXXX.dt".length();
    private static final int FILENAME_DENDRITIC_TREE_REMOTE_LENGTH = "XX_XXXX_XXXX.dtr".length();
    private static final int FILENAME_PLASTIC_LENGTH = "XXXX.plastic".length();

    // Note: To send messages from the driving computer to the region computers (or body and room computers)
    // we can just drop json files in the control directory, and configure the other computers to check
    // to see if one has arrived. Then it can open it, parse the contents, and take the appropriate action,
    // such as emitting a report.

    public static boolean isShutdownFilePresent(
            String pathToControlDirectory,
            byte rid) {
        String shutdownFilenameWithPath = pathToControlDirectory + "/" + generateShutdownFilename(rid);
        File file = new File(shutdownFilenameWithPath);
        return file.exists();
    }

    public static boolean isReadyFilePresent(
            String pathToControlDirectory,
            byte rid) {
        String readyFilenameWithPath = pathToControlDirectory + "/" + generateReadyFilename(rid);
        File file = new File(readyFilenameWithPath);
        return file.exists();
    }

    public static void createReadyFile(
            String pathToControlDirectory,
            byte rid) {
        String readyFilenameWithPath = pathToControlDirectory + "/" + generateReadyFilename(rid);
        File file = new File(readyFilenameWithPath);
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException("Failed to create ready file, it already exists: '"
                        + readyFilenameWithPath + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create ready file: '"
                    + readyFilenameWithPath + "' with error: " + e.getMessage(), e);
        }
    }

    public static void deleteAllReadyFiles(String pathToControlDirectory) {
        //noinspection ResultOfMethodCallIgnored
        listRidsFromReadyFiles(pathToControlDirectory)
                .stream()
                .map(rid -> pathToControlDirectory + "/" + generateReadyFilename(rid))
                .map(File::new)
                .forEach(File::delete);
    }

    public static void deleteShutdownFile(
            String pathToControlDirectory,
            byte rid) {
        String fileWithPath = pathToControlDirectory + "/" + generateShutdownFilename(rid);
        File file = new File(fileWithPath);
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    public static void shutdownRegions(
            String pathToControlDirectory,
            List<Byte> rids) {
        //noinspection ResultOfMethodCallIgnored
        rids.stream()
                .map(rid -> pathToControlDirectory + "/" + generateShutdownFilename(rid))
                .map(File::new)
                .forEach(File::delete);
    }

    static List<Byte> listRidsFromReadyFiles(String pathToControlDirectory) {
        File controlDirectory = new File(pathToControlDirectory);
        String[] list = controlDirectory.list();
        if (list == null) {
            throw new RuntimeException("Control directory specified isn't a directory: '" + pathToControlDirectory + "'");
        }

        return Arrays.stream(list)
                .map(FileUtil::getRidFromReadyFilename)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    static Byte getRidFromReadyFilename(String filename) {
        if (filename.length() != FILENAME_READY_LENGTH
                || filename.charAt(0) != 'r'
                || !filename.substring(3).equals(".ready")) {
            return null;
        }
        return NidUtil.stringToByte(filename.substring(1, 3));
    }

    static String generateReadyFilename(byte rid) {
        return "r" + NidUtil.convertRidToHexString(rid) + ".ready";
    }

    static String generateShutdownFilename(byte rid) {
        return "r" + NidUtil.convertRidToHexString(rid) + ".shutdown";
    }
}
