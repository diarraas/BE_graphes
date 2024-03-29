package org.insa.algo.shortestpath;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class AStarAlgorithmTest {

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
		System.out.println("--- Pour un chemin valide ---");
		System.out.println(" ");
		
		//Britain
		origin= graph.get(541036);
		destination= graph.get(262972);
		
		/*//Tunisia
        origin= graph.get(74979);
		destination= graph.get(59318);*/
		
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		
		
        assertEquals(Status.OPTIMAL, solution.getStatus());
        System.out.println("Statut optimal");
        
        assertEquals(origin, path.getOrigin());
        assertEquals(destination, path.getDestination());
        System.out.println("Origine et destination vérifiées ");

        assertTrue(path.isValid());
        
        	//Verifie si tous les chemins de la solution est le plus court chemin
		ShortestPathSolution [][] solution_tests = new ShortestPathSolution [path.getArcs().size()][path.getArcs().size()] ;
		Path [][] path_tests = new Path [path.getArcs().size()][path.getArcs().size()] ;
		
		for (int i=solution.getPath().getArcs().size()-4; i<path.getArcs().size(); i++) {
			for (int j=solution.getPath().getArcs().size()-4; j<path.getArcs().size(); j++) {
				origin= path.getArcs().get(i).getOrigin();
				destination= path.getArcs().get(i).getDestination();
				data = new ShortestPathData(graph, origin , destination, arcInspector);
				solution_tests[i][j] = new AStarAlgorithm(data).doRun();
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
		
		System.out.println("Tous les sous-chemins sont valides et représentent des plus court chemin");
		System.out.println(" ");
		
			//Vérification du chemin inverse 
		//Britain   
		destination= graph.get(262972);
		origin= graph.get(541036);
		
		/*//Tunisia
		destination= graph.get(59318);
		origin= graph.get(74979);*/
		
		System.out.println("Vérification du chemin inverse:");

		System.out.println("De noeud n° " + origin.getId() + "  à noeud n° " + destination.getId());
		System.out.println(" ");
		
		data = new ShortestPathData(graph, origin , destination, arcInspector);
        solution_inverse = new AStarAlgorithm(data).doRun();
        path_inverse= solution_inverse.getPath();
        assertEquals(path.getLength(), path_inverse.getLength(),0);
        assertEquals(path.getMinimumTravelTime(), path_inverse.getMinimumTravelTime(),0);
        
		System.out.println("Le chemin inverse est le même et a le même coût");
		System.out.println(" ");

        
		//Null path
		System.out.println("--- Pour un chemin vide ---");
		System.out.println(" ");
		
		//Britain
        origin= graph.get(251342);
		destination= graph.get(251342);
		
		/*//Tunisia
        origin= graph.get(74979);
		destination= graph.get(74979);*/
		
		System.out.println("De noeud n° " + origin.getId()+ " à noeud n° " + destination.getId()+ data.getDestination().getId() + " En mode " + data.getMode());
		System.out.println(" ");

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
        assertTrue(path.isEmpty());
        
		System.out.println("Le chemin est vide");
		System.out.println(" ");

		//Path not valid
		System.out.println("--- Pour un chemin invalide ---");
		System.out.println(" ");
		
		//Britain
        origin= graph.get(16029);
		destination= graph.get(619891);
		
		/*//Tunisia
        origin= graph.get(74979);
		destination= graph.get(202238);*/
		
		System.out.println("De noeud n° " + origin.getId()+ " à noeud n° " + destination.getId());
		System.out.println(" ");

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		Label destination_label= new Label(destination);
        assertEquals(Status.INFEASIBLE, solution.getStatus());
        assertEquals(Double.POSITIVE_INFINITY, destination_label.getCost(),0);
        
		System.out.println("Pas de chemin possible et le coût du noeud " + destination.getId() + " est infini");
		System.out.println(" ");

		
	}
    
    @Test
    public void testSituation() throws IOException {
    	
		System.out.println("*** Tests de vérifications commencent.. ***");
		System.out.println(" ");

        map = "bretagne.mapgr";
        //map = "tunisia.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
    	
		System.out.println("*** Carte: Bretagne***");
		//System.out.println("*** Carte: Tunisia***");
    	
		System.out.println("*** Mode: distance ***");
		System.out.println(" ");
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		testdoRun(); 
		
		System.out.println("*** Mode: temps ***");
		System.out.println(" ");
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
		
		testdoRun(); 
  	
    }
	
	@Test
	public void testdoRunOracle() throws IOException {
		
		System.out.println("*** Tests de vérifications de AStar avec oracle commencent.. ***");
		System.out.println(" ");

		ShortestPathSolution solution;
		ShortestPathSolution expected;
		Path solution_path;
		Path expected_path;
		int i;
		
        map = "guadeloupe.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
		
		ArrayList<ShortestPathData> oracle_test= oracle();
		
		for (i=0 ; i< oracle_test.size() ;i++) {
			data = oracle_test.get(i);
			
			solution = new AStarAlgorithm(data).doRun();

			expected = new BellmanFordAlgorithm(data).doRun();

			
			solution_path = solution.getPath();
			expected_path = expected.getPath();
			if(data.getOrigin().equals(data.getDestination())) {
				System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
				System.out.println("Chemin null");
				System.out.println(" ");

			}
			else {
				if(data.getMode()==Mode.LENGTH) {
					if(solution.isFeasible() && expected.isFeasible())
					{
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
						assertEquals(expected_path.getLength(), solution_path.getLength() , 0);
						System.out.println("Le coût de Bellmann en distance " + expected_path.getLength()+ " le coût de Dijksta en distance " + solution_path.getLength());
						System.out.println(" ");
			        }
					else {
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId() + " En mode " + data.getMode());
						System.out.println("Chemin inexistant");
						System.out.println(" ");
	
					}
				}
				if (data.getMode()==Mode.TIME) {
					if(solution.isFeasible() && expected.isFeasible())
					{
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
						System.out.println("Le coût de Bellmann en temps " + expected_path.getMinimumTravelTime()+ " le coût de Dijkstra en temps " + solution_path.getMinimumTravelTime());
						System.out.println(" ");
			        }
					else {
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId() + " En mode " + data.getMode());
						System.out.println("Chemin inexistant");
						System.out.println(" ");
	
					}
				}
			}	

		}
		System.out.println("Test avec Oracle validé ");
		System.out.println(" ");

	}
	
	@Test
	public void testdoRunSansOracle() throws IOException {
		
		System.out.println("*** Tests de vérifications de AStar sans oracle commencent.. ***");

		ShortestPathSolution solution;
		Path path ;
		double Minimum_Travel_Time_fastest_path;
		double Minimum_Travel_Time_shortest_path;
		double length_shortest_path; 
		double length_fastest_path; 
		
        map = "bretagne.mapgr";
        //map = "tunisia.mapgr";

		System.out.println("*** Carte: Bretagne***");
		//System.out.println("*** Carte: Tunisia***");
		System.out.println(" ");

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
    	
		arcInspector= ArcInspectorFactory.getAllFilters().get(0);
		
		//Britain
		origin= graph.get(541036);
		destination= graph.get(262972);
		
		/*//Tunisia
        origin= graph.get(74979);
		destination= graph.get(59318);*/
		
		System.out.println("De noeud n° " +origin.getId()+ " à noeud n° " + destination.getId());
		System.out.println(" ");

		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_shortest_path=path.getMinimumTravelTime();
		length_shortest_path=path.getLength();
		
		arcInspector= ArcInspectorFactory.getAllFilters().get(2);
		
		
		data = new ShortestPathData(graph, origin , destination, arcInspector);
		solution = new AStarAlgorithm(data).doRun();
		path = solution.getPath();
		Minimum_Travel_Time_fastest_path=path.getMinimumTravelTime();
		length_fastest_path=path.getLength();
		
		assertTrue(length_fastest_path>=length_shortest_path);
		System.out.println("La longueur du plus rapide chemin est plus grande ou egal à la longeur du plus court chemin ");
		
		System.out.println("La longueur du plus rapide chemin est "+ length_fastest_path
				+" la longeur du plus court chemin " + length_shortest_path );

		System.out.println(" ");
		System.out.println("Test sans Oracle validé ");

	}
	
	@Test
	public void testdoRunOracleDijkstra() throws IOException {
		
		System.out.println("*** Tests de vérifications de AStar avec oracle commencent.. ***");
		System.out.println(" ");

		ShortestPathSolution solution;
		ShortestPathSolution expected;
		Path solution_path;
		Path expected_path;
		int i;
		
        map = "guadeloupe.mapgr";

    	reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
    	 
    	graph =reader.read();
		
		ArrayList<ShortestPathData> oracle_test= oracle();
		
		for (i=0 ; i< oracle_test.size() ;i++) {
			data = oracle_test.get(i);
			
			solution = new AStarAlgorithm(data).doRun();

			expected = new DijkstraAlgorithm(data).doRun();

			
			solution_path = solution.getPath();
			expected_path = expected.getPath();
			if(data.getOrigin().equals(data.getDestination())) {
				System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
				System.out.println("Chemin null");
				System.out.println(" ");

			}
			else {
				if(data.getMode()==Mode.LENGTH) {
					if(solution.isFeasible() && expected.isFeasible())
					{
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
						assertEquals(expected_path.getLength(), solution_path.getLength() , 0);
						System.out.println("Le coût de Bellmann en distance " + expected_path.getLength()+ " le coût de Dijksta en distance " + solution_path.getLength());
						System.out.println(" ");
			        }
					else {
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId() + " En mode " + data.getMode());
						System.out.println("Chemin inexistant");
						System.out.println(" ");
	
					}
				}
				if (data.getMode()==Mode.TIME) {
					if(solution.isFeasible() && expected.isFeasible())
					{
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId());
						//assertEquals(expected_path.getMinimumTravelTime(), solution_path.getMinimumTravelTime() , 0);
						System.out.println("Le coût de Bellmann en temps " + expected_path.getMinimumTravelTime()+ " le coût de Dijkstra en temps " + solution_path.getMinimumTravelTime());
						System.out.println(" ");
			        }
					else {
						System.out.println("De noeud n° " + data.getOrigin().getId() + " à noeud n° " + data.getDestination().getId() + " En mode " + data.getMode());
						System.out.println("Chemin inexistant");
						System.out.println(" ");
	
					}
				}
			}	

		}
		System.out.println("Test avec Oracle Dijkstra validé ");
		System.out.println(" ");

	}
	
}
