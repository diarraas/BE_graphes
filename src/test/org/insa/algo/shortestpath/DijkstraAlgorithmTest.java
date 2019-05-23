package org.insa.algo.shortestpath;

import org.insa.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {

    private static Graph graph;
    private Node origin;
    private Node destination;
    private ArcInspector arcInspector;
    private ShortestPathData data; 
    private static String map;
    private static GraphReader reader;
    
    @BeforeClass
    public static void initAll() throws IOException {
    	
        map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	
    	graph =reader.read();

    }

	@Test
	public void testdoRun() {
		ShortestPathSolution solution;
		Path path ;
		
		//First Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);

		//Path valid
		origin= new Node(144589, null);
		destination= new Node(553673, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.OPTIMAL, solution.getStatus());
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        assertTrue(path.isValid());
//       assertEquals(path.getLength(), solution.getAllCosts());
        
        
		//Null path
        origin= new Node(144589, null);
		destination= new Node(144589, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
		//assertEquals(0 , path.getArcs().size());
        //assertEquals(null, path.getOrigin());
        assertTrue(path.isEmpty());

		//Path not valid
        origin= new Node(513508, null);
		destination= new Node(126344, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.INFEASIBLE, solution.getStatus());
        
        //Third Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);

		//Path valid
		origin= new Node(144589, null);
		destination= new Node(553673, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.OPTIMAL, solution.getStatus());
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        assertTrue(path.isValid());
//        assertEquals(path.getMinimumTravelTime(), solution.getAllCosts());
        
        
		//Null path
        origin= new Node(144589, null);
		destination= new Node(144589, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertTrue(path.isEmpty());
		assertEquals(0 , path.getArcs().size());

		//Path not valid
        origin= new Node(513508, null);
		destination= new Node(126344, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.INFEASIBLE, solution.getStatus());
		
	}
	
	@Test
	public void testdoRunOracle() {
		ShortestPathSolution solution;
		ShortestPathSolution expected;
		
		//First Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		//Path valid
		origin= new Node(144589, null);
		destination= new Node(553673, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertSame(expected.getPath() ,  solution.getPath());
        
        
		//Null path
        origin= new Node(144589, null);
		destination= new Node(144589, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertEquals(expected.getPath(), solution.getPath());


		//Path not valid
        origin= new Node(513508, null);
		destination= new Node(126344, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertEquals(expected.getPath(), solution.getPath());
        
        //Third Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);

		//Path valid
		origin= new Node(144589, null);
		destination= new Node(553673, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertSame(expected.getPath() ,  solution.getPath());
        
        
		//Null path
        origin= new Node(144589, null);
		destination= new Node(144589, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertEquals(expected.getPath(), solution.getPath());

		//Path not valid
        origin= new Node(513508, null);
		destination= new Node(126344, null);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		expected = new BellmanFordAlgorithm(data).doRun();
        assertEquals(expected.getPath(), solution.getPath());
	}
}
