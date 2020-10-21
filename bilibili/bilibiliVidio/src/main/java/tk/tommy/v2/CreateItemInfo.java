package tk.tommy.v2;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class CreateItemInfo {

    public static void main(String[] args) {
        String vidioFolder = "C:/Users/yu_da/Downloads/bilibiliVidio/";
        String child = "584139198";
        create(vidioFolder, child).forEach(System.out::println);
    }

    public static List<ItemInfo> create(String vidioFolder, String child) {
        try {
            return Files.list(Paths.get(vidioFolder + child))
                .map(CreateItemInfo::createItemInfo)
                .sorted(Comparator.comparing(info -> Integer.parseInt(info.index)))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static ItemInfo createItemInfo(Path folder) {
        ItemInfo into = new ItemInfo();
        into.index = Strings.padStart(folder.getFileName().toString(), 3, '0');
        into.name = getName(Paths.get(folder.toFile().toString(), "/entry.json"));
        final File directory = Arrays.stream(Objects.requireNonNull(folder.toFile().listFiles()))
            .filter(File::isDirectory)
            .findFirst()
            .orElseThrow(() -> new FileSystemNotFoundException("createItemInfo FileNotFoundException" + folder.toFile().getPath()));
        String mp4Path = folder.toFile() + "/" + directory.getName() + "/";
        into.inputs = List.of(mp4Path + "video.m4s", mp4Path + "audio.m4s");

        into.inputs.forEach(videoPath -> {
            if (!new File(videoPath).exists()) {
                System.out.println("[LOG] videoPath not found = " + videoPath);
                throw new FileSystemNotFoundException("videoPath FileNotFoundException" + videoPath);
            }
        });

        return into;
    }

    private static String getName(Path itemNamePath) {
        try {
            final String json = String.join("", Files.readAllLines(itemNamePath));
            System.out.println("[LOG] json = " + json);
            final String page_data = new JSONObject(json).get("page_data").toString();
            System.out.println("[LOG] page_data = " + page_data);
            return new JSONObject(page_data).get("part").toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
