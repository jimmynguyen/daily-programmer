package io.github.jimmynguyen.dailyprogrammer.c161h.entity;

import java.util.*;
import java.util.stream.Collectors;

public class Node {
    private String id;
    private Map<Node, Integer> neighborCapacityMap;
    public Node(String id) {
        this.id = id;
        this.neighborCapacityMap = new HashMap<>();
    }
    public String getId() {
        return id;
    }
    public void addNeighbor(Node node, int capacity) {
        neighborCapacityMap.put(node, capacity);
    }
    public void decrementCapacity(Node node) {
        neighborCapacityMap.put(node, neighborCapacityMap.get(node)-1);
    }
    public boolean isNeighbor(Node node) {
        return neighborCapacityMap.containsKey(node);
    }
    public List<Node> getPathToNode(Node node) {
        List<Node> visited = new ArrayList<>();
        visited.add(this);
        return getPathToNode(node, visited);
    }
    public List<Node> getPathToNode(Node node, List<Node> visited) {
        if (isNeighbor(node) && neighborCapacityMap.get(node) > 0) {
            visited.add(node);
            return visited;
        }
        List<Node> visitedCopy;
        Map<Integer, List<Node>> shortestPathMap = new HashMap<>();
        for (Map.Entry<Node, Integer> neighborCapacityMapEntry : neighborCapacityMap.entrySet()) {
            if (neighborCapacityMapEntry.getValue() > 0 && !visited.contains(neighborCapacityMapEntry.getKey())) {
                visitedCopy = new ArrayList<>(visited);
                visitedCopy.add(neighborCapacityMapEntry.getKey());
                List<Node> pathToNode = neighborCapacityMapEntry.getKey().getPathToNode(node, visitedCopy);
                if (!pathToNode.isEmpty()) {
                    if (!shortestPathMap.containsKey(pathToNode.size())) {
                        shortestPathMap.put(pathToNode.size(), pathToNode);
                    }
                }
            }
        }
        if (!shortestPathMap.isEmpty()) {
            return shortestPathMap.get(Collections.min(shortestPathMap.keySet()));
        }
        return Collections.emptyList();
    }
    public String toString() {
        return String.format("%s: %s", id, neighborCapacityMap.entrySet().stream().map(e -> String.format("[%s %d]", e.getKey().getId(), e.getValue())).collect(Collectors.joining(", ")));
    }
}
