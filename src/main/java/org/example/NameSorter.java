package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

public class NameSorter {
    private static final Logger logger = LogManager.getLogger(NameSorter.class);

    public static void main(String[] args) {
        if (args.length == 1) {
            String filePath = args[0];
            ContentManager contentManager = new ContentManager();
            try {
                logger.info("Reading file contents.");
                String content = contentManager.readFileContent(filePath);

                if (content != null) {
                    logger.info("Validating file contents.");
                    boolean isValid = contentManager.validateContent(content);
                    if (isValid) {
                        logger.info("File contents are valid.");
                        logger.info("Sorting file contents...");
                        String sortedContent = contentManager.sortNames(content);
                        System.out.println("=== SORTED NAMES ===");
                        System.out.println(sortedContent);
                        logger.info("Saving content to sorted-names-list.txt");
                        contentManager.saveResult(sortedContent, "sorted-names-list.txt");
                    } else {
                        logger.error("Invalid file content detected. Provided file should only container letters/spaces and new lines. Path: {}", filePath);
                    }
                } else {
                    logger.warn("No content found in file: {}", filePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("NameSorter must have a reference to an input file as the first and only argument.");
        }
    }
}
