package entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Worker {
    private String name;
    private List<String> skills;

    private Worker(String name, List<String> skills) {
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public int getNumSkills() {
        return skills.size();
    }

    public static Worker parseWorker(String workerLine) {
        String[] workerLineParts = workerLine.split(" ");
        return new Worker(workerLineParts[0], Arrays.stream(workerLineParts[1].split(",")).collect(Collectors.toList()));
    }
}
