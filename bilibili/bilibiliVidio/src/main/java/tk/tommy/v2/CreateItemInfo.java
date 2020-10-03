package tk.tommy.v2;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONObject;


public class CreateItemInfo {

    public static void main(String[] args) throws Exception {
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
                .map(folder -> {
                    ItemInfo into = new ItemInfo();
                    into.index = Strings.padStart(folder.getFileName().toString(), 3, '0');
                    into.name = getName(Paths.get(folder.toFile().toString(), "/entry.json"));
                    String mp4Path = folder.toFile() + "/" + magicNumber + "/";
                    List<String> inputs = new ArrayList();
                    inputs.add(mp4Path + "video.m4s");
                    inputs.add(mp4Path + "audio.m4s");
                    into.inputs = inputs;
                    return into;
                })
                .sorted(Comparator.comparing(info -> Integer.parseInt(info.index)))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String getName(Path itemNamePath) {
        try {

            final String json = String.join("", Files.readAllLines(itemNamePath));
            return new JSONObject(new JSONObject(json).get("page_data").toString()).get("part").toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
