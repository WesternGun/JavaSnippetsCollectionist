package io.westerngun.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reader and Writer of log file to trace logging content, for integration tests.
 * The file path is "log/test.tmp". After every test, the logs are output to the
 * same file, overwriting that of last test.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogTracer {
    private static final String TEST_LOG_FILE_PATH = String.join(File.separator, System.getProperty("user.dir"), "logs");
    private static final String TEST_LOG_FILE_NAME = "test.tmp";
    private static final int[] ALL_FLAGS = new int[] {
            // ignore case
            Pattern.CASE_INSENSITIVE,
            // match "^" not to the start of whole content, but to line start;
            // and "$" match line end, and input end, instead of only input end.
            Pattern.MULTILINE,
            // tell compiler to match "." with any character, including line terminator
            Pattern.DOTALL};


    @Getter
    private String testLogFile;
    @Getter
    private static String fileContent;

    public String readLogFile() {
        if (fileContent == null || fileContent.isEmpty()) {
            assert testLogFile != null : "Cannot find test log file! ";
            return ResourceReader.readFileAsString(testLogFile);
        } else {
            return fileContent;
        }
    }

    public void writeLogFile(ByteArrayOutputStream loggingOut) throws IOException {
        File logDir = new File(TEST_LOG_FILE_PATH);
        boolean dirCreated = logDir.mkdirs();
        if (!logDir.exists() && !dirCreated) {
            log.error("Creating test log dir failed. ");
            return;
        }
        File logFile = new File(logDir + File.separator + TEST_LOG_FILE_NAME);
        if (!logFile.exists()) {
            boolean created = logFile.createNewFile();
            if (!created) {
                log.debug("Test log file exists. Overwriting. ");
            }
        }

        testLogFile = logDir + File.separator + TEST_LOG_FILE_NAME;
        try (FileWriter fw = new FileWriter(logFile)) {
            fw.write(loggingOut.toString());
            fw.flush();
        } catch (IOException e) {
            log.error("File writing error. ", e);
        }

    }

    /**
     * Delete log file if needed; it should be called if "application.yml" contains
     * the corresponding flag to clean up after tests.
     * See integration tests of PreAuth for more details.
     *
     * @return <code>true</code> if the file is deleted; <code>false</code> if cannot delete because
     * the file is absent, or some exception is caught.
     */
    public boolean deleteLogFile() {
        File logFile = new File(testLogFile);
        try {
            return Files.deleteIfExists(logFile.toPath()); // exception when the file is held by another process
        } catch (DirectoryNotEmptyException e1) {
            log.error("Is directory and is not empty; cannot delete. ", e1);
        } catch (SecurityException e2) {
            log.error("Do not have delete permission; cannot delete. ", e2);
        } catch (IOException e3) {
            log.error("I/O error; cannot delete. ", e3);
        }
        return false;
    }

    public boolean logFileExists() {
        return new File(testLogFile).exists();
    }

    /**
     * Find a String in the log file with regex.
     * We use {@link Matcher#find()} instead of {@link Matcher#matches()} because the latter
     * tries to use the whole string to match, while the former finds in substring.
     *
     * By default the flags to use is configured here with {@link #ALL_FLAGS}.
     * @param stringToFind the string to look for
     * @return if log contains the string or not
     */
    public boolean findInLog(String stringToFind) {
        return PatternChecker.findWithFlags(stringToFind, readLogFile(), ALL_FLAGS);
    }

    /**
     * Check if the log contains no content, or contains only non-pritable characters.
     * {@link String#trim()} eliminates non-printable, control characters, like line breaks.
     * @return if the log is empty
     */
    public boolean emptyLog() {
        return readLogFile().trim().isEmpty();
    }

    /**
     * Find a String at the start of the log file with regex.
     * We use {@link Matcher#lookingAt()} instead of {@link Matcher#matches()} because the latter
     * tries to use the whole string to match, while the former finds in substring.
     *
     * By default the flags to use is configured here with {@link #ALL_FLAGS}.
     *
     * @param stringToFind the string to look for
     * @return if log contains the string or not
     */
    public boolean findInLogStartsWith(String stringToFind) {
        return PatternChecker.startsWithFlags(stringToFind, readLogFile(), ALL_FLAGS);
    }


    public boolean matchInLogWithRegex(String regex) {
        return PatternChecker.matchWithFlags(regex, readLogFile(), ALL_FLAGS);
    }

}
