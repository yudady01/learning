package tk.tommy;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import tk.tommy.rename.Item;


public class BilibiliVidioRename {
    static String outPutFolderParent = "C:/Users/yu_da/OneDrive/Desktop";
    static String vidioFolder = "C:/Users/yu_da/Downloads/bilibiliVidio";

    public static void main(String[] args) throws Exception {
        String child = "925160311";
        createOne(child);
    }

    public static void createOne(String child) throws Exception {

        BilibiliVidioRename bilibiliVidioRename = new BilibiliVidioRename();
        bilibiliVidioRename.title = bilibiliVidioRename.createTitle(child);
        IntStream.range(1, 10).forEach(e -> System.out.println(bilibiliVidioRename.title));
        bilibiliVidioRename.targetFolder = bilibiliVidioRename.createTargetFolder();
        List<Item> items = bilibiliVidioRename.createInfo(child);
        items.forEach(System.out::println);

        items.stream()
            .map(item -> getItemBlvFolders(item, child))
            .forEach(item -> {
                List<Path> blvFolders = item.blvFolders;
                if (blvFolders.size() > 1) {
                    throw new RuntimeException("plz check me");
                }

                for (int i = 0; i < blvFolders.size(); i++) {
                    final Path path = blvFolders.get(i);
                    File sourceFile = new File(path.toString() + "/" + i + ".blv");
                    if (sourceFile.exists()) {
                        File targetFile = new File(bilibiliVidioRename.targetFolder, item.flvPrefixName + item.flvName + "." + i + ".flv");
                        try {
                            System.out.println("[LOG] item.targetName = " + item.flvPrefixName);
                            System.out.println("[LOG] targetFile = " + targetFile);
                            System.out.println("[LOG] sourceFile = " + sourceFile);
                            FileUtils.copyFile(sourceFile, targetFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        IntStream.range(1, 10).forEach(e -> System.out.println("[LOG]" + bilibiliVidioRename.title + " done"));


        final File file = Paths.get(vidioFolder, child).toFile();
        if (file.exists()) {
            System.out.println("[LOG]  " + file.toString());
        }

    }

    private static Item getItemBlvFolders(final Item item, final String child) {
        try {
            item.blvFolders = Files.list(Paths.get(vidioFolder + "/" + child, "" + item.index))
                .filter(pa -> pa.toFile().isDirectory())
                .collect(Collectors.toList());

            return item;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }


    String title;
    private File targetFolder;

    private List<Item> createInfo(final String child) throws IOException {
        return Files.list(Paths.get(vidioFolder, child))
            .map(Path::getFileName)
            .map(String::valueOf)
            .map(Integer::parseInt)
            .filter(n -> !Objects.equals(title, n.toString()))
            .sorted()
            .map(index -> createItem(index, child))
            .collect(Collectors.toList());
    }

    private Item createItem(Integer index, final String child) {
        try {
            final String targetName = StringUtils.leftPad("" + index, 3, "0") + "-";
            String eachFolder = outPutFolderParent + "/" + child + "/" + index;
            Stream<Path> list1 = Files.list(Paths.get(vidioFolder + "/" + child, "" + index));
            String flvName = createFlvName(list1);
            return new Item(targetName, eachFolder, flvName, index);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String createFlvName(final Stream<Path> list1) {
        return list1
            .filter(pa -> "entry.json".equals(pa.getFileName().toString()))
            .map(this::getJsonStr)
            .map(str -> {
                JSONObject jsonObject = new JSONObject(str);
                JSONObject pageData = jsonObject.getJSONObject("page_data");
                return pageData.getString("part");
            }).collect(Collectors.toList()).get(0);
    }

    private String getJsonStr(Path path) {
        try {
            return FileUtils.readFileToString(path.toFile(), "utf-8");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    private File createTargetFolder() {
        File targetFolder = new File(Paths.get(outPutFolderParent).toString(), title);
        targetFolder.mkdir();
        return targetFolder;
    }

    private String createTitle(final String child) {
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
