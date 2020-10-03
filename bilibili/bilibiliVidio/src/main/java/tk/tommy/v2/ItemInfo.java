package tk.tommy.v2;

import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

public class ItemInfo {
    public String index;
    public String name;
    public List<String> inputs;

    @Override
    public String toString() {
        return new StringJoiner(", ", ItemInfo.class.getSimpleName() + "[", "]")
            .add("index='" + index + "'")
            .add("name='" + name + "'")
            .add("inputs=" + inputs)
            .toString();
    }
}
