package tk.tommy.v2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class CreateItemName {
    public static void main(String[] args) throws IOException {
        final List<String> jsonList = Files.readAllLines(Paths.get("D:\\tommy\\learning\\bilibili\\bilibiliVidio\\src\\main\\java\\tk\\tommy\\v2\\1.json"));

        final String json = jsonList.stream().collect(Collectors.joining());

        System.out.println("[LOG] json = " + json);
        final String name = new JSONObject(new JSONObject(json).get("page_data").toString()).get("part").toString();
        System.out.println("[LOG] name = " + name);

    }
}
