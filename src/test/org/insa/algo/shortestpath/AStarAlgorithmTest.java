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

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class AStarAlgorithmTest {

    private static Graph graph;
    private Node origin;
    private Node destination;
    private ArcInspector arcInspector;
    private ShortestPathData data; 
    private static String map;
    private static GraphReader reader;
    private static Node [] nodes;
	private static ArrayList<ShortestPathData> data_test= new ArrayList<ShortestPathData>();

    
    @BeforeClass
    public static void initAll() throws IOException {
    	
        map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/guadeloupe.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
    	

    }
    
    public ArrayList<ShortestPathData> oracle(){
    	int i , j;
    	int nodes_id[]= { 32951, 13293, 21095, 4999, 16060, 14943, 27459, 27384, 26582, 4307};
    	for (i=0 ; i< nodes_id.length ;i++) {
        	for (j=0 ; j< nodes_id.length;j++) {
            origin= graph.get(i);
    		destination= graph.get(j);
    		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
    		data_test.add(new ShortestPathData(graph, origin , destination, arcInspector));
    		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
    		data_test.add(new ShortestPathData(graph, origin , destination, arcInspector));
        	}
    	}
    	return data_test;
    }
    
    

    @Test
	public void testdoRun() {
		ShortestPathSolution solution;
		Path path ;
		
		//First Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);

		//Path valid
		origin= graph.get(20883);
		destination= graph.get(9116);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.OPTIMAL, solution.getStatus());
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        assertTrue(path.isValid());
//       assertEquals(path.getLength(), solution.getCost());

//       assertEquals(path.getLength(), solution.getAllCosts());
        
        
		//Null path
        origin= graph.get(9116);
		destination= graph.get(9116);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		//assertEquals(0 , path.getArcs().size());
        //assertEquals(null, path.getOrigin());
        assertTrue(path.isEmpty());


		//Path not valid
        origin= graph.get(16029);
		destination= graph.get(18865);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.INFEASIBLE, solution.getStatus());
        

        //Third Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);

		//Path valid
		origin= graph.get(9116);
		destination= graph.get(20883);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.OPTIMAL, solution.getStatus());
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        assertTrue(path.isValid());
//        assertEquals(path.getMinimumTravelTime(), solution.getAllCosts());
      
        
		//Path not valid
        origin= graph.get(16029);
		destination= graph.get(18865);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertEquals(Status.INFEASIBLE, solution.getStatus());
        
		//Null path
        origin= graph.get(9116);
		destination= graph.get(9116);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertTrue(path.isEmpty());
		
	}
	
	@Test
	public void testdoRunOracle() {
		ShortestPathSolution solution;
		ShortestPathSolution expected;
		Path solution_path;
		Path expected_path;
		int i;
		
		ArrayList<ShortestPathData> oracle_test= oracle();
		
		for (i=0 ; i< oracle_test.size() ;i++) {
			data = oracle_test.get(i);
			
			solution = new AStarAlgorithm(data).doRun();

			expected = new BellmanFordAlgorithm(data).doRun();

			
			solution_path = solution.getPath();
			expected_path = expected.getPath();
			
			if(solution.isFeasible() && expected.isFeasible())
			{
				assertEquals(expected_path.getLength(), solution_path.getLength() , 0);
				assertEquals(expected_path.getMinimumTravelTime(), solution_path.getMinimumTravelTime() , 0);

	        }
			else {
				assertEquals(expected_path,solution_path);
			}
			
		}
	}
	
	@Test
	public void testdoRunSansOracle() {
		ShortestPathSolution solution;
		Path path ;
		double Minimum_Travel_Time_fastest_path;
		double Minimum_Travel_Time_shortest_path;
		double length_shortest_path; 
		double length_fastest_path; 

		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		origin= graph.get(20883);
		destination= graph.get(9116);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_shortest_path=path.getMinimumTravelTime();
		length_shortest_path=path.getLength();
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
		
		origin= graph.get(20883);
		destination= graph.get(9116);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_fastest_path=path.getMinimumTravelTime();
		length_fastest_path=path.getLength();
		
		assertTrue(length_fastest_path>=length_shortest_path);
		assertTrue(Minimum_Travel_Time_fastest_path<=Minimum_Travel_Time_shortest_path);
		
	}
	
	
	
	
}
