package tk.tommy.v2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> videoInputPaths = new ArrayList<>();
        videoInputPaths.add("C:\\Users\\yu_da\\Downloads\\bilibiliVidio\\667705991\\1\\80\\audio.m4s");
        videoInputPaths.add("C:\\Users\\yu_da\\Downloads\\bilibiliVidio\\667705991\\1\\80\\video.m4s");
        String videoOutputPath = "C:\\Users\\yu_da\\OneDrive\\Desktop\\123\\newVideo.mp4";
        FFmpegMediaUtil.videoConvert(videoInputPaths, videoOutputPath);
    }
}
