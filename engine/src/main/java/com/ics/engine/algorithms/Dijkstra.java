package com.ics.engine.algorithms;

import java.util.*;

public class Dijkstra {
  public static class Graph {
    private final Map<String, List<Edge>> adj = new HashMap<>();
    
    public void addEdge(String u, String v, int w) {
      adj.computeIfAbsent(u, k -> new ArrayList<>()).add(new Edge(v, w));
    }
    
    public List<Edge> neighbors(String u) {
      return adj.getOrDefault(u, List.of());
    }
    
    public Set<String> vertices() {
      return adj.keySet();
    }
  }
  
  public record Edge(String v, int w) {}

  public static Map<String, Integer> shortestPaths(Graph g, String source) {
    Map<String, Integer> dist = new HashMap<>();
    PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
        Comparator.comparingInt(Map.Entry::getValue)
    );
    dist.put(source, 0);
    pq.add(Map.entry(source, 0));
    
    while (!pq.isEmpty()) {
      var cur = pq.poll();
      String u = cur.getKey();
      int d = cur.getValue();
      
      if (d != dist.get(u)) continue;
      
      for (Edge e : g.neighbors(u)) {
        int nd = d + e.w();
        if (nd < dist.getOrDefault(e.v(), Integer.MAX_VALUE)) {
          dist.put(e.v(), nd);
          pq.add(Map.entry(e.v(), nd));
        }
      }
    }
    return dist;
  }
  
  public static int shortestPath(Graph g, String source, String target) {
    Map<String, Integer> dist = shortestPaths(g, source);
    return dist.getOrDefault(target, Integer.MAX_VALUE);
  }
}





