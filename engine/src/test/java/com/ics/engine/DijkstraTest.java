package com.ics.engine;

import com.ics.engine.algorithms.Dijkstra;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
  @Test
  void shortestPathBasic() {
    var g = new Dijkstra.Graph();
    g.addEdge("A", "B", 2);
    g.addEdge("A", "C", 5);
    g.addEdge("B", "C", 1);
    
    Map<String, Integer> dist = Dijkstra.shortestPaths(g, "A");
    assertEquals(0, dist.get("A"));
    assertEquals(2, dist.get("B"));
    assertEquals(3, dist.get("C"));
  }
  
  @Test
  void shortestPathSingleTarget() {
    var g = new Dijkstra.Graph();
    g.addEdge("A", "B", 2);
    g.addEdge("A", "C", 5);
    g.addEdge("B", "C", 1);
    
    assertEquals(3, Dijkstra.shortestPath(g, "A", "C"));
    assertEquals(Integer.MAX_VALUE, Dijkstra.shortestPath(g, "A", "D"));
  }
}





