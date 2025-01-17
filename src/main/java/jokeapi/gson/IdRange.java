package jokeapi.gson;

import java.util.List;
import java.util.Map;

public class IdRange {
    private Map<String, List<Integer>> ranges;

    public Map<String, List<Integer>> getRanges() {
        return ranges;
    }

    public void setRanges(Map<String, List<Integer>> ranges) {
        this.ranges = ranges;
    }
}