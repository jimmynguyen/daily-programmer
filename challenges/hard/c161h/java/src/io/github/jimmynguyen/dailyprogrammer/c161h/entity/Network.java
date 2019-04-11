package io.github.jimmynguyen.dailyprogrammer.c161h.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Network {
    private Map<String, Node> nodeMap;
    private List<String> calls;
    public Network(List<String> network, List<String> calls) {
        this(network);
        this.calls = calls;
    }
    private Network(List<String> network) {
        nodeMap = new HashMap<>();
        buildNetwork(network);
    }
    private void buildNetwork(List<String> network) {
        String[] lineParts;
        String nodeId1, nodeId2;
        int capacity;
        Node node1, node2;
        for (String line : network) {
            lineParts = line.split(" ");
            nodeId1 = lineParts[0];
            nodeId2 = lineParts[1];
            capacity = Integer.parseInt(lineParts[2]);
            node1 = nodeMap.get(nodeId1);
            node2 = nodeMap.get(nodeId2);
            if (node1 == null) {
                node1 = new Node(nodeId1);
                nodeMap.put(nodeId1, node1);
            }
            if (node2 == null) {
                node2 = new Node(nodeId2);
                nodeMap.put(nodeId2, node2);
            }
            node1.addNeighbor(node2, capacity);
            node2.addNeighbor(node1, capacity);
        }
    }
    private List<Node> getPath(String nodeId1, String nodeId2) {
        Node node1 = nodeMap.get(nodeId1);
        if (node1 == null) {
            throw new IllegalArgumentException(String.format("Invalid node %s", nodeId1));
        }
        Node node2 = nodeMap.get(nodeId2);
        if (node2 == null) {
            throw new IllegalArgumentException(String.format("Invalid node %s", nodeId2));
        }
        List<Node> path = node1.getPathToNode(node2);
        if (!path.isEmpty()) {
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) {
                    path.get(i).decrementCapacity(path.get(i-1));
                }
                if (i < path.size()-1) {
                    path.get(i).decrementCapacity(path.get(i + 1));
                }
            }
        }
        return path;
    }
    public void placeCalls() {
        placeCalls(calls);
    }
    public void placeCalls(List<String> calls) {
        System.out.println(calls.stream().map(line -> {
            String[] lineParts = line.split(" ");
            String nodeId1 = lineParts[0];
            String nodeId2 = lineParts[1];
            List<Node> path = getPath(nodeId1, nodeId2);
            String call = String.format("Call %s %s -- ", nodeId1, nodeId2);
            if (path.isEmpty()) {
                call += "failed";
            } else {
                call += String.format(path.size() == 2 ? "%s" : "placed %s", path.stream().map(Node::getId).collect(Collectors.joining(" ")));
            }
            return call;
        }).collect(Collectors.joining("\n")));
    }
    public String toString() {
        return nodeMap.values().stream().map(n -> n.toString()).collect(Collectors.joining("\n"));
    }
}
