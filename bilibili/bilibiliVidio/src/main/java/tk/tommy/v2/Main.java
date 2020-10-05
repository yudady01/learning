package tk.tommy.v2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import org.apache.commons.io.FileUtils;

public class Main {
    private static final String DEFAULT_OUT_FOLDER = "C:/Users/yu_da/OneDrive/Desktop/";
    private static final String VIDEO_FOLDER = "C:/Users/yu_da/Downloads/bilibiliVidio/";

    public static void main(String[] args) throws Exception {

        Files.list(Paths.get(VIDEO_FOLDER))
            .filter(path -> String.valueOf(path.getFileName()).matches("-?\\d+(\\.\\d+)?"))
            .map(path -> path.getFileName().toString())
            .forEach(video -> {
                createOneVideo(video);
                deleteOneVideo(video);
            });
    }

    private static void deleteOneVideo(String video) {
        try {
            FileUtils.deleteDirectory(Paths.get(VIDEO_FOLDER, video).toFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }


    private static void createOneVideo(String child) {
        final Path path = Paths.get(DEFAULT_OUT_FOLDER + CreateTitle.create(VIDEO_FOLDER, child));
        if (Files.exists(path)) {
            IntStream.range(0, 10).forEach(e -> System.out.println("[LOG] video exist !!! "));
            return;
        }

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        CreateItemInfo.create(VIDEO_FOLDER, child).forEach(item -> {
            String tmpMp4 = "C:/Users/yu_da/OneDrive/Desktop/video.mp4";
            FFmpegMediaUtil.videoConvert(item.inputs, tmpMp4);

            Path fileToMovePath = Paths.get(tmpMp4);
            String fileName = String.format("%s-%s.mp4", item.index, item.name);
            Path targetPath = Paths.get(DEFAULT_OUT_FOLDER + CreateTitle.create(VIDEO_FOLDER, child), "/", fileName);
            try {
                Files.move(fileToMovePath, targetPath);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

        });
    }


}
