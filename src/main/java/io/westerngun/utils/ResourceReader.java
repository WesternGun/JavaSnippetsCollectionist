package io.westerngun.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ResourceReader {

    private ResourceReader(){

    }

    /**
     * Reading a JSON resource with its path and returns its String representation.
     * The file is supposed to be in UTF-8 encoding.
     *
     * @param path the path of JSON file, relative to classpath
     * @return the String representation of JSON. If any exception, return null.
     */
    public static String readResourceAsString(String path) {
        try (InputStream in = ResourceReader.class.getResourceAsStream(path.startsWith("/") ? path : "/" + path)) {
            if (in == null) {
                log.error("Cannot find JSON resource with path {}", path);
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int i;
            // inputstream reads into buffer, i is the number of bytes read
            while ((i = in.read(buffer)) != -1) {
                // write not till the length of buffer, but the number of bytes read (i),
                // specially for the last time reading
                out.write(buffer, 0, i);
            }
            return out.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("Cannot find JSON resource. ", e);
        }
        return null;
    }

    /**
     * Reading a file with absolute path, and returns its String representation.
     * The file is supposed to be in UTF-8 encoding.
     *
     * @param path the absolute path of JSON file
     * @return the String representation of JSON. If any exception, return null.
     */
    public static String readFileAsString(String path) {
        File file = new File(path);
        if (!file.exists()) {
            log.error("File does not exist. {}", path);
            return null;
        }

        try (FileInputStream in = new FileInputStream(file)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int i;
            // inputstream reads into buffer, i is the number of bytes read
            while ((i = in.read(buffer)) != -1) {
                // write not till the length of buffer, but the number of bytes read (i),
                // specially for the last time reading
                out.write(buffer, 0, i);
            }
            in.close();
            return out.toString(StandardCharsets.UTF_8.name());
        } catch (FileNotFoundException e) {
            log.error("Cannot find file with path {}. ", path, e);
        } catch (IOException e1) {
            log.error("IO Error. ", e1);
        }
        return null;
    }

    public static List<String> readFolderFilesContent(String resourceFolderPath) {
        File fixturesDir = getResourceFile(resourceFolderPath);
        if (!fixturesDir.isDirectory()) {
            throw new IllegalStateException(String.format("Directory '%s' not found!", resourceFolderPath));
        }
        return Arrays.stream(fixturesDir.listFiles((dir, name) -> name.endsWith(".json")))
                .map(file -> ResourceReader.readFileAsString(file.getPath()))
                .collect(Collectors.toList());
    }

    private static File getResourceFile(String resourcePath) {
        try {
            URI dirPath = ResourceReader.class.getResource(resourcePath).toURI();
            return Paths.get(dirPath).toFile();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Resource file not found.", e);
        }
    }


}
