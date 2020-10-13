package tk.tommy.v2;

import com.google.common.base.Strings;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class CreateItemInfo {

    public static void main(String[] args) {
        String vidioFolder = "C:/Users/yu_da/Downloads/bilibiliVidio/";
        String child = "584139198";
        create(vidioFolder, child).forEach(System.out::println);
    }

    private static String getMagicNumber(String vidioFolder, String child) {
        try {
            return Files.list(Paths.get(vidioFolder + "/" + child + "/1"))
                .filter(path -> path.toFile().isDirectory())
                .map(path -> String.valueOf(path.getFileName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("magic  number fail"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    public static List<ItemInfo> create(String vidioFolder, String child) {
        final String magicNumber = getMagicNumber(vidioFolder, child);
        try {
            return Files.list(Paths.get(vidioFolder + child))
                .map(folder -> createItemInfo(folder, magicNumber))
                .sorted(Comparator.comparing(info -> Integer.parseInt(info.index)))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static ItemInfo createItemInfo(Path folder, String magicNumber) {
        ItemInfo into = new ItemInfo();
        into.index = Strings.padStart(folder.getFileName().toString(), 3, '0');
        into.name = getName(Paths.get(folder.toFile().toString(), "/entry.json"));
        String mp4Path = folder.toFile() + "/" + magicNumber + "/";
        into.inputs = List.of(mp4Path + "video.m4s", mp4Path + "audio.m4s");
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
