package io.github.jimmynguyen.dailyprogrammer.c161h.entity;

import java.util.Arrays;
import java.util.List;

public class TestCase {
    private List<String> network;
    private List<String> calls;
    public TestCase(String[] network, String[] calls) {
        this.network = Arrays.asList(network);
        this.calls = Arrays.asList(calls);
    }
    public TestCase(List<String> network, List<String> calls) {
        this.network = network;
        this.calls = calls;
    }
    public List<String> getNetwork() {
        return network;
    }
    public List<String> getCalls() {
        return calls;
    }
}
