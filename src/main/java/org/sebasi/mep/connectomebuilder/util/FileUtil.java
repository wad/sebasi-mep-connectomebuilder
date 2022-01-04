package org.sebasi.mep.connectomebuilder.util;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.generator.RegionSpec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static void createDirectoryStructure(
            String einDirectory,
            ConnectomeGenSpec cgs) {

        File einFile = new File(einDirectory);
        if (einFile.exists()) {
            throw new RuntimeException("I can't create the directory structure there, it already exists: " + einDirectory);
        }
        if (!einFile.mkdir()) {
            throw new RuntimeException("Was not able to create directory for some reason: " + einDirectory);
        }

        String controlDirectoryPath = einDirectory + "/" + DIRNAME_CONTROL;
        File controlDirectory = new File(controlDirectoryPath);
        if (!controlDirectory.mkdir()) {
            throw new RuntimeException("Failed to make control directory: " + controlDirectoryPath);
        }

        String roomDirectoryPath = einDirectory + "/" + DIRNAME_ROOM;
        File roomDirectory = new File(roomDirectoryPath);
        if (!roomDirectory.mkdir()) {
            throw new RuntimeException("Failed to make room directory: " + roomDirectoryPath);
        }

        String bodyDirectoryPath = einDirectory + "/" + DIRNAME_BODY;
        File bodyDirectory = new File(bodyDirectoryPath);
        if (!bodyDirectory.mkdir()) {
            throw new RuntimeException("Failed to make body directory: " + bodyDirectoryPath);
        }

        String brainDirectoryPath = einDirectory + "/" + DIRNAME_BRAIN;
        File brainDirectory = new File(brainDirectoryPath);
        if (!brainDirectory.mkdir()) {
            throw new RuntimeException("Failed to make brain directory: " + brainDirectoryPath);
        }

        int regionIndex = 0;
        List<RegionSpec> regionSpecList = cgs.getRegionSpecList();
        for (RegionSpec regionSpec : regionSpecList) {

            String regionDirectoryPath = brainDirectoryPath + "/r" + NidUtil.convertRidToHexString((byte) regionIndex);
            File regionDirectory = new File(regionDirectoryPath);
            if (!regionDirectory.mkdir()) {
                throw new RuntimeException("Failed to make region directory: " + regionDirectoryPath);
            }

            int numClusters = regionSpec.getClusterSpecList().size();
            for (int clusterIndex = 0; clusterIndex < numClusters; clusterIndex++) {
                String clusterDirectoryPath = regionDirectoryPath + "/c" + NidUtil.convertCidToHexString((short) clusterIndex);
                File clusterDirectory = new File(clusterDirectoryPath);
                if (!clusterDirectory.mkdir()) {
                    throw new RuntimeException("Failed to make cluster directory: " + clusterDirectoryPath);
                }
            }
        }
    }

    public static void writeCgsFile(
            String einDirectoryPath,
            String cgsContents) {
        writeStringToFile(
                einDirectoryPath,
                FILENAME_CGS,
                cgsContents);
    }

    public static String readFileIntoString(String pathAndFilename) {
        try {
            return new String(Files.readAllBytes(Paths.get(pathAndFilename)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file '" + pathAndFilename + "' into string: " + e.getMessage(), e);
        }
    }

    public static String getCgsPathAndFilename(String einDirectory) {
        return einDirectory + "/" + FILENAME_CGS;
    }

    public static void writeRoom(
            String einDirectory,
            String roomJson) {
        writeStringToFile(
                einDirectory + "/" + DIRNAME_ROOM,
                FILENAME_ROOM,
                roomJson);
    }

    public static void writeBody(
            String einDirectory,
            String bodyJson) {
        writeStringToFile(
                einDirectory + "/" + DIRNAME_BODY,
                FILENAME_BODY,
                bodyJson);
    }

    public static void writeBrain(
            String einDirectory,
            String brainJson) {
        writeStringToFile(
                einDirectory + "/" + DIRNAME_BRAIN,
                FILENAME_BRAIN,
                brainJson);
    }

    public static void writeRegion(
            String einDirectory,
            String regionJson,
            byte rid) {
        writeStringToFile(
                einDirectory + "/r" + NidUtil.convertRidToHexString(rid),
                FILENAME_REGION,
                regionJson);
    }

    public static void writeCluster(
            String einDirectory,
            String regionJson,
            byte rid,
            short cid) {
        writeStringToFile(
                einDirectory + "/r" + NidUtil.convertRidToHexString(rid) + "/c" + NidUtil.convertCidToHexString(cid),
                FILENAME_REGION,
                regionJson);
    }

    static void writeStringToFile(
            String path,
            String filename,
            String fileContents) {
        String pathAndFilename = path + "/" + filename;
        try (
                FileWriter fileWriter = new FileWriter(pathAndFilename);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(fileContents);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + pathAndFilename, e);
        }
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
