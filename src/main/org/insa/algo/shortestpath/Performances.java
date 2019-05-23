package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.insa.graph.Graph;
import org.insa.algo.* ;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

public class Performances {

	private static Graph small_graph, bigger_graph, average_graph, cars_graph, no_car_graph;
    private static ShortestPathData datas[]; 
    private static ArcInspector inspector ;
    private static String small_map, bigger_map, average_map, cars_only, no_car ;
    private static GraphReader small_reader, bigger_reader, average_reader,car_reader, no_car_reader;
    private static DijkstraAlgorithm[] dijkstra, astar ;
    
    
    public static void initAll() throws IOException {
    	
    	/*	Initializing Maps		*/
        
    	average_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgique.mapgr";
        small_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/chili.mapgr";
        bigger_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/california.mapgr";
        cars_only = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        no_car = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr";
    	
        /*	Initializing Readers	*/
        
        small_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(small_map))));
    	bigger_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(bigger_map))));
    	average_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(average_map))));
    	car_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(cars_only))));
    	no_car_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(no_car))));
    	
    	/*	Initializing Graphs		*/
    	
    	small_graph = small_reader.read();
    	average_graph = average_reader.read();
    	bigger_graph = bigger_reader.read();
    	cars_graph = car_reader.read();
    	no_car_graph = no_car_reader.read();
    	
    	/*	Initializing Data	and Algorithms*/
    	
    	datas = new ShortestPathData[100];
    	dijkstra = new DijkstraAlgorithm[100] ;
    	astar = new AStarAlgorithm[100] ;
    	inspector = ArcInspectorFactory.getAllFilters().get(0);
    	int i;
    	int origin = 588713;
    	int destination = 71369 ;
    	
    	for(i=0; i<20;i++) { //20 first couples of nodes on small map
    		datas[i] = new ShortestPathData(small_graph,small_graph.get(origin + (-1)*i*1234), small_graph.get(destination - (-1)*i*1234), inspector);
    	}
    	
    	origin = 778946;
    	destination = 36231 ;
    	for(i=20; i< 40;i++) { //20 second couples of nodes on average map
    		datas[i] = new ShortestPathData(average_graph,average_graph.get(origin + (-1)*i*1234), average_graph.get(destination - (-1)*i*1234), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    	}
    	
    	origin = 1712929;
    	destination = 928289 ;
    	for(i=40; i< 60;i++) { //20 third couples of nodes on bigger map
    		datas[i] = new ShortestPathData(bigger_graph,bigger_graph.get(origin + (-1)*i*1234), bigger_graph.get(destination - (-1)*i*1234), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    	}
    	
    	inspector = ArcInspectorFactory.getAllFilters().get(1);
    	origin = 5669 ;
    	destination =  5185;
    	for(i=60; i < 80;i++) { //20 next couples of nodes on Toulouse map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin + (-1)*i*1234), cars_graph.get(destination - (-1)*i*1234), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    	}
    	
    	inspector = ArcInspectorFactory.getAllFilters().get(3);
    	origin = 14737 ;
    	destination =  8182;
    	for(i=80; i < 100;i++) { //20 last couples of nodes on Bordeaux map
    		datas[i] = new ShortestPathData(no_car_graph,no_car_graph.get(origin + (-1)*i*1234), no_car_graph.get(destination - (-1)*i*1234), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    	}
    	
    }
        
	public static void launchTestDijkstraDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[100];
		int i ;
		BufferedWriter small = new BufferedWriter(new FileWriter("chili_distance_20_data.txt"));
		BufferedWriter average = new BufferedWriter(new FileWriter("belgique_distance_20_data.txt"));
		BufferedWriter big = new BufferedWriter(new FileWriter("california_distance_20_data.txt"));
		BufferedWriter cars = new BufferedWriter(new FileWriter("toulouse_distance_20_data.txt"));
		BufferedWriter no_car = new BufferedWriter(new FileWriter("bordeaux_distance_20_data.txt"));
		for(i = 0; i < 20; i++) {
			solutions[i] = dijkstra[i].doRun();
			small.write('0');
			small.write("20");
			//small.write("" + solutions.g);
			
			
		}
		
	}
	
	public static void launchTestAstar() {
		ShortestPathSolution[] solutions = new ShortestPathSolution[100];
		int i ;
		for(i = 0; i < 100; i++) {
			solutions[i] = astar[i].doRun();
		}
	}

}

