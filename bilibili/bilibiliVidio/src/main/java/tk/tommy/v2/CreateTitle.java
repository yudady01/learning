package tk.tommy.v2;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;


public class CreateTitle {

    public static void main(String[] args) throws Exception {
        String child = "667705991";
        String vidioFolder = "C:/Users/yu_da/Downloads/bilibiliVidio/";
        System.out.println("[LOG] title = " + create(vidioFolder, child));
    }


    public static String create(String vidioFolder, String child) {
        try {
            final File entryJsonFile = new File(vidioFolder + "/" + child + "/1/entry.json");
            final String source = FileUtils.readFileToString(entryJsonFile, "utf-8");
            return new JSONObject(source)
                .getString("title")
                .replaceAll("\\*", "")
                .replaceAll("\\|", "")
                .replaceAll("\\.", "");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
