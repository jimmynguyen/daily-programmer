import entity.Scheduler;
import entity.Worker;
import util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing test case file path");
        }
        List<String> lines = FileUtil.readAllLines(args[0]);
        int numSkills = Integer.parseInt(lines.get(0));
        List<Worker> workers = lines.subList(numSkills + 1, 2 * numSkills + 1).stream().map(Worker::parseWorker).collect(Collectors.toList());
        Scheduler scheduler =  new Scheduler(workers);
        scheduler.printAssignmentMap();
    }
}