package tk.tommy.v2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String VIDIO_FOLDER = "C:/Users/yu_da/Downloads/bilibiliVidio/";

    public static void main(String[] args) throws Exception {


        String child = "667705991";
        createOneViedo(child);
    }


    private static void createOneViedo(String child) {
        String title = CreateTitle.create(VIDIO_FOLDER, child);
        String videoOutputFolder = "C:/Users/yu_da/OneDrive/Desktop/" + title;
        Paths.get(videoOutputFolder).toFile().mkdir();

        CreateItemInfo.create(VIDIO_FOLDER, child).forEach(item -> {
            String tmpMp4 = "C:/Users/yu_da/OneDrive/Desktop/viedo.mp4";
            FFmpegMediaUtil.videoConvert(item.inputs, tmpMp4);

            Path fileToMovePath = Paths.get(tmpMp4);
            String fileName = item.index + "-" + item.name + ".mp4";
            Path targetPath = Paths.get(videoOutputFolder + "/" + fileName);
            try {
                Files.move(fileToMovePath, targetPath);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

        });
    }


}
