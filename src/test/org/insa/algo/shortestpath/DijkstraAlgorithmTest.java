package org.insa.algo.shortestpath;

import org.insa.*;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class DijkstraAlgorithmTest {

	/*	Atributes	*/
   
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath;
    
    //The shortest path algorithms
    private static DijkstraAlgorithm dijkstra ;
    private static BellmanFordAlgorithm bellman ;
    
    public static void initComponents(ShortestPathData data) throws IOException {
    	//init algorithms
    	bellman = new BellmanFordAlgorithm(data);
    	dijkstra = new DijkstraAlgorithm(data);
    }
	
    public static void testScenario(Graph map, int nature, Node origin, Node destination) {
    	
    }
	
    @Test
	public static void isValid() {
		assertEquals(true,dijkstra.doRun().getPath().isValid());
	}
    
    @Test
	public static void isShortest() {
    	
    }

}
