package common.json;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.exceptions.FileParseException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class FileManager {
    private String fileInput;
    private File tempFile;

    public FileManager() {
        setTempFile();
    }

    public void setTempFile() {
        this.tempFile = new File(".save.json");
    }

    public String getFileInput() {
        return fileInput;
    }

    public void setFileInput(String fileInput) {
        this.fileInput = fileInput;
    }

    public static LinkedHashMap<Long, StudyGroup> readFromJson(String fileInput) throws IOException {
        LinkedHashMap<Long, StudyGroup> groups = new LinkedHashMap<>();
        String fileContent = Files.readString(Paths.get(fileInput));
        JSONObject json = new JSONObject(fileContent);
        for (String key : json.keySet()) {
            JSONObject groupJson = json.getJSONObject(key);
            try {
                StudyGroup group = StudyGroupJsonReader.readStudyGroup(groupJson);
                groups.put(Long.parseLong(key), group);
            } catch (FileParseException e) {
                //System.out.println(e.getMessage());
            }
        }
        return groups;
    }


    public void saveToJson(StudyGroupCollectionManager sgc, String fileName) throws IOException {
        JSONObject json = new JSONObject();
        HashMap<Long, StudyGroup> collection = sgc.getMap();

        for (Map.Entry<Long, StudyGroup> entry : collection.entrySet()) {
            JSONObject groupJson = StudyGroupJsonWriter.toJson(entry.getValue());
            json.put(String.valueOf(entry.getKey()), groupJson);
        }

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.write(json.toString(2));
        }
    }

    public void deleteTempFile() {
        tempFile.delete();
    }

    public static boolean filesAreEqual(String fileName1, String fileName2) {
        try {
            LinkedHashMap<Long, StudyGroup> collectionAfterCommands = readFromJson(fileName2);
            LinkedHashMap<Long, StudyGroup> collectionBeforeCommands = readFromJson(fileName1);
            if (collectionAfterCommands.isEmpty()) {
                return false;
            }
            if (collectionAfterCommands.size() == collectionBeforeCommands.size()) {
                for (Long key : collectionBeforeCommands.keySet()) {
                    if (!collectionAfterCommands.containsKey(key) || !collectionBeforeCommands.get(key).equals(collectionAfterCommands.get(key))) {
                        return false;
                    }
                }
                return true;
            }
            return false;


        } catch (IOException e) {
            return true;
        }
    }

}
