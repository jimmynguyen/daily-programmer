package entity;

import java.util.*;
import java.util.stream.Collectors;

public class Scheduler {
    private final List<Worker> workers;

    public Scheduler(List<Worker> workers) {
        this.workers = workers;
    }

    public Map<Worker, String> getAssignmentMap() {
        Map<Worker, String> assignmentMap = new TreeMap<>(Comparator.comparing(Worker::getName));
        List<Worker> workers = new ArrayList<>(this.workers);
        while (!workers.isEmpty()) {
            Worker worker = workers.stream().sorted(Comparator.comparing(Worker::getNumSkills)).findFirst().orElse(null);
            String skill = worker.getSkills().get(0);
            assignmentMap.put(worker, skill);
            workers.remove(worker);
            workers.stream().forEach(w -> w.getSkills().remove(skill));
        }
        return assignmentMap;
    }

    public void printAssignmentMap() {
        System.out.println(getAssignmentMap().entrySet().stream().map(e -> String.format("%s %s", e.getKey().getName(), e.getValue())).collect(Collectors.joining("\n")));
    }
}
