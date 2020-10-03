package tk.tommy.v2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String vidioFolder = "C:/Users/yu_da/Downloads/bilibiliVidio/";
        String child = "667705991";
        String title = CreateTitle.create(vidioFolder, child);
        String videoOutputFolder = "C:/Users/yu_da/OneDrive/Desktop/" + title;
        Paths.get(videoOutputFolder).toFile().mkdir();

        CreateItemInfo.create(vidioFolder, child).forEach(item -> {
            String tmpMp4 = "C:/Users/yu_da/OneDrive/Desktop/viedo.mp4";
            FFmpegMediaUtil.videoConvert(item.inputs, tmpMp4);

            Path fileToMovePath = Paths.get(tmpMp4);
            Path targetPath = Paths.get(videoOutputFolder + "/" + item.index + item.name + ".mp4");
            try {
                Files.move(fileToMovePath, targetPath);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

        });
    }


    private void tt() {
        List<String> videoInputPaths = new ArrayList<>();
        videoInputPaths.add("C:\\Users\\yu_da\\Downloads\\bilibiliVidio\\667705991\\1\\80\\audio.m4s");
        videoInputPaths.add("C:\\Users\\yu_da\\Downloads\\bilibiliVidio\\667705991\\1\\80\\video.m4s");
        String videoOutputPath = "C:\\Users\\yu_da\\OneDrive\\Desktop\\123\\newVideo.mp4";
        FFmpegMediaUtil.videoConvert(videoInputPaths, videoOutputPath);
    }

}
