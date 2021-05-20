package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Pubudu
 * <p>
 * Manages and validates contents in given input file
 */
public class ContentManager {
    private static final Logger logger = LogManager.getLogger(ContentManager.class);

    /**
     * Read file content
     *
     * @param filePath File path to read content
     * @return File content as a string. Returns null if no content found.
     */
    public String readFileContent(String filePath) throws FileNotFoundException {
        // Initialize scanner
        Scanner sc = new Scanner(new FileInputStream(filePath));
        // Scan though file and build content string
        StringBuilder stringBuilder = new StringBuilder();
        long lines = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.length() > 0) // Remove empty lines
                stringBuilder.append(line).append("\n");
            lines++;
        }
        sc.close();
        // Return content if string builder is not empty
        if (stringBuilder.length() > 0) {
            logger.info("Completed reading {} lines,", lines);
            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * Validate file content (Content should only contain letters/spaces and new lines)
     *
     * @param content File content string
     * @return Returns true if the file is valid, Else returns false
     */
    public boolean validateContent(String content) {
        // Replace all space/newline characters
        String sampleA = content.replaceAll("\\s|\\n", "");
        // Replace all characters except letters
        String sampleB = content.replaceAll("\\W|\\d|\\n", "");
        return sampleA.equals(sampleB);
    }

    /**
     * Sort name list in file
     *
     * @param content File content string
     * @return Names in file content sorted first by last name then by first names
     */
    public String sortNames(String content) {
        return Arrays.asList(content.split("\\r?\\n")).parallelStream().map(fullName -> {
            // Extract first name and last name
            String givenNames = fullName.replaceAll("\\s\\w+$", "");
            String lastName = fullName.replaceAll(givenNames + "\\s", "");
            // Assemble name in reverse order
            String reversedName = lastName + " " + givenNames;
            // Return full name and reversed name combination
            return new NameCombination(fullName, reversedName);
        }).sorted(Comparator.comparing(NameCombination::getReversedName)) // Sort list on reversed name (This sorts the list by last name first and then by given names)
                .map(NameCombination::getFullName) // Extract only full name
                .collect(Collectors.joining("\n")); // Collect result as a string by joining with new line delimiter
    }

    /**
     * Save content to given file overwriting the content if exits. Also tries to create the file if it does not exists.
     *
     * @param content  Content to write
     * @param filePath File path to write content
     * @throws FileNotFoundException Thrown on failure to create new file
     */
    public void saveResult(String content, String filePath) throws FileNotFoundException {
        boolean parentExists = ensureParents(filePath);
        if (parentExists) {
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, false));
            pw.write(content);
            pw.flush();
            pw.close();
            logger.info("Results saved to {}", filePath);
        } else {
            logger.error("Failed to save result. Unable to create file to save contents. file: {}", filePath);
            System.exit(1);
        }
    }

    /**
     * Checks for existence of parents of a given file and create if does not exists
     *
     * @param filePath File path
     * @return Returns true if parent exists or successfully created, Else returns false
     */
    private boolean ensureParents(String filePath) {
        File file = new File(filePath);
        boolean fileExists = file.exists();
        if (!fileExists) {
            logger.warn("Specified file does not exists. Attempting to create file...");
            String parentPath = file.getParent();
            if (parentPath != null) {
                File parent = new File(file.getParent());
                boolean parentExists = parent.exists();
                if (!parentExists) {
                    return parent.mkdirs();
                }
            }
        }
        return true;
    }
}
