package me.eldodebug.soar.utils.file;

import lombok.extern.slf4j.Slf4j;
import me.eldodebug.soar.utils.file.filter.PngFileFilter;
import me.eldodebug.soar.utils.file.filter.SoundFileFilter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class FileUtils {

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }

            return directory.delete();
        }
        return false;
    }

    public static long getDirectorySize(@NotNull File directory) {

        long size = 0;

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else if (file.isDirectory()) {
                        size += getDirectorySize(file);
                    }
                }
            }
        } else if (directory.isFile()) {
            size = directory.length();
        }

        return size;
    }

    public static void unzip(final File file, final File dest) {
        try {
            ZipInputStream zis = new ZipInputStream(Files.newInputStream(file.toPath()));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                final File f = new File(dest, ze.getName());
                if (ze.isDirectory()) {
                    f.mkdirs();
                } else {
                    final FileOutputStream fos = new FileOutputStream(f);
                    final byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zis.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static @Nullable File selectImageFile() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new PngFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    public static @Nullable File selectSoundFile() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new SoundFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {

        try (FileInputStream input = new FileInputStream(sourceFile); FileOutputStream output = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }

    public static void downloadFile(String url, File output) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (InputStream inputStream = entity.getContent();
                         OutputStream outputStream = Files.newOutputStream(output.toPath())) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
            httpClient.close();
        } catch (Exception e) {
            log.error("Failed to download file: {}", url, e);
        }
    }

    public static @NotNull String getBaseName(String fileName) {

        if (fileName == null) {
            return "null";
        }

        int point = fileName.lastIndexOf(".");

        if (point != -1) {
            return fileName.substring(0, point);
        }

        return fileName;
    }

    public static @NotNull String getBaseName(@NotNull File file) {
        return getBaseName(file.getName());
    }

    public static String getExtension(String fileName) {

        if (fileName == null) {
            return null;
        }

        int lastIndexOf = fileName.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "null";
        }

        return fileName.substring(lastIndexOf + 1);
    }

    public static String getExtension(@NotNull File file) {
        return getExtension(file.getName());
    }

    public static boolean isAudioFile(String fileName) {

        if (fileName == null) {
            return false;
        }

        String ext = getExtension(fileName);

        return ext.equals("mp3") || ext.equals("wav") || ext.equals("ogg");
    }

    public static boolean isAudioFile(@NotNull File file) {
        return isAudioFile(file.getName());
    }

    public static boolean isImageFile(String fileName) {

        if (fileName == null) {
            return false;
        }

        String ext = getExtension(fileName);

        return ext.equals("jpeg") || ext.equals("png") || ext.equals("jpg");
    }

    public static boolean isImageFile(@NotNull File file) {
        return isImageFile(file.getName());
    }
}
