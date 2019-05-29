package org.insa.algo.shortestpath;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	private static ArrayList<ShortestPathData> data_test= new ArrayList<ShortestPathData>();

    
    public ArrayList<ShortestPathData> oracle(){
    	int i , j;
    	int nodes_id[]= { 32951, 13293, 21095, 4999, 16060};
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
    
    
    
    

    //@Test
	public void testdoRun() {
        	
		ShortestPathSolution solution;
		Path path ;
        ShortestPathSolution solution_inverse ;
        Path path_inverse;
		
		//First Element of arc inspector
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);

		//Path valid
		System.out.println("--- Pour un path valid: ---");
		origin= graph.get(541036);
		destination= graph.get(262972);
		System.out.println("De " + origin+ "à" + destination);
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
		
		
        assertEquals(Status.OPTIMAL, solution.getStatus());
        System.out.println("Statut obtimal");
        
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        System.out.println("Origine et");

        assertTrue(path.isValid());
        
		ShortestPathSolution [][] solution_tests = new ShortestPathSolution [path.getArcs().size()][path.getArcs().size()] ;
		Path [][] path_tests = new Path [path.getArcs().size()][path.getArcs().size()] ;
		
		for (int i=solution.getPath().getArcs().size()-4; i<path.getArcs().size(); i++) {
			for (int j=solution.getPath().getArcs().size()-4; j<path.getArcs().size(); j++) {
				origin= path.getArcs().get(i).getOrigin();
				destination= path.getArcs().get(i).getDestination();
				data = new ShortestPathData(graph, origin , destination, arcInspector);
				solution_tests[i][j] = new DijkstraAlgorithm(data).doRun();
				path_tests[i][j] = solution.getPath();
			}
		}
		
		for (int i= solution.getPath().getArcs().size()-4; i<solution.getPath().getArcs().size()-3; i++) {
			for (int j=solution.getPath().getArcs().size()-4; j<solution.getPath().getArcs().size()-3; j++) {
				if(i!=j) {
			        assertEquals(path_tests[i][j].getLength(), path_tests[j][i].getLength(),0);
			        assertEquals(path_tests[i][j].getMinimumTravelTime(), path_tests[j][i].getMinimumTravelTime(),0);
			        assertEquals(Status.OPTIMAL, solution_tests[i][j].getStatus());
			        assertEquals(origin, path_tests[i][j].getOrigin());
			        assertEquals(destination, path_tests[i][j].getDestination());
			        assertTrue(path_tests[i][j].isValid());
				}
			}
		}
		   
		destination= graph.get(262972);
		origin= graph.get(541036);
		System.out.println("De " + origin+ "à" + destination);

		data = new ShortestPathData(graph, origin , destination, arcInspector);
        solution_inverse = new DijkstraAlgorithm(data).doRun();
        path_inverse= solution_inverse.getPath();
        assertEquals(path.getLength(), path_inverse.getLength(),0);
        assertEquals(path.getMinimumTravelTime(), path_inverse.getMinimumTravelTime(),0);
        
        
		//Null path
		System.out.println("*** Pour un null path ***");
		
        origin= graph.get(251342);
		destination= graph.get(251342);
		
		System.out.println("De " + origin+ "à" + destination);

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
        assertTrue(path.isEmpty());


		//Path not valid
		System.out.println("*** Pour un path invalid ***");
		
        origin= graph.get(16029);
		destination= graph.get(619891);
		
		System.out.println("De " + origin+ "à" + destination);

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
		Label destination_label= new Label(destination);
        assertEquals(Status.INFEASIBLE, solution.getStatus());
        assertEquals(Double.POSITIVE_INFINITY, destination_label.getCost(),0);
		
	}
    
    @Test
    public void testSituation() throws IOException {
    	
		System.out.println("*** Tests de correction commencent.. ***");
    	
        map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
    	
		System.out.println("*** Mode: distance ***");

		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		testdoRun(); 
		
		System.out.println("*** Mode: temps ***");
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
		
		testdoRun(); 
  	
    }
	
	@Test
	public void testdoRunOracle() throws IOException {
		
		System.out.println("*** Tests de correction de Dijkstra avec oracle commencent.. ***");
		
		ShortestPathSolution solution;
		ShortestPathSolution expected;
		Path solution_path;
		Path expected_path;
		int i;
		
        map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/guadeloupe.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
		
		ArrayList<ShortestPathData> oracle_test= oracle();
		
		for (i=0 ; i< oracle_test.size() ;i++) {
			data = oracle_test.get(i);
			
			solution = new DijkstraAlgorithm(data).doRun();

			expected = new BellmanFordAlgorithm(data).doRun();

			
			solution_path = solution.getPath();
			expected_path = expected.getPath();
			
			if(solution.isFeasible() && expected.isFeasible())
			{
				assertEquals(expected_path.getLength(), solution_path.getLength() , 0);
				assertEquals(expected_path.getMinimumTravelTime(), solution_path.getMinimumTravelTime() , 0);

	        }

			
		}
	}
	
	@Test
	public void testdoRunSansOracle() throws IOException {
		
		System.out.println("*** Tests de correction de Dijkstra sans oracle commencent.. ***");

		ShortestPathSolution solution;
		Path path ;
		double Minimum_Travel_Time_fastest_path;
		double Minimum_Travel_Time_shortest_path;
		double length_shortest_path; 
		double length_fastest_path; 
		
        map = "/home/dhouib/Bureau/S2/BE GRAPHE/BE_graphes/maps/bretagne.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
    	
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		origin= graph.get(541036);
		destination= graph.get(262972);
		
		System.out.println("De " + origin+ "à" + destination);

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_shortest_path=path.getMinimumTravelTime();
		length_shortest_path=path.getLength();
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
		
		origin= graph.get(541036);
		destination= graph.get(262972);
		
		System.out.println("De " + origin+ "à" + destination);

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new DijkstraAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_fastest_path=path.getMinimumTravelTime();
		length_fastest_path=path.getLength();
		
		assertTrue(length_fastest_path>=length_shortest_path);
		assertTrue(Minimum_Travel_Time_fastest_path<=Minimum_Travel_Time_shortest_path);
		
	}
	
}
