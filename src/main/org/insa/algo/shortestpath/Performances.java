package org.insa.algo.shortestpath;

import java.io.PrintWriter;
import java.io.BufferedInputStream;
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
        
    	average_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr";
        small_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/chile.mapgr";
        bigger_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/california.mapgr";
        cars_only = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        no_car = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr";
        
        /*
        average_map = "/Documents/uni/BE_graphes/libs/belgium.mapgr";
        small_map = "/Documents/uni/BE_graphes/libs/chile.mapgr";
        bigger_map = "/Documents/uni/BE_graphes/libs/california.mapgr";
        cars_only = "/Documents/uni/BE_graphes/libs/toulouse.mapgr";
        no_car = "/Documents/uni/BE_graphes/libs/bordeaux.mapgr"; 
         
         * */
    	
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
    		datas[i] = new ShortestPathData(small_graph,small_graph.get(origin), small_graph.get(destination), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(small_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+1)*1234)%(small_graph.size()));
    	}
    	
    	origin = 778946;
    	destination = 36231 ;
    	for(i=20; i< 40;i++) { //20 second couples of nodes on average map
    		datas[i] = new ShortestPathData(average_graph,average_graph.get(origin), average_graph.get(destination), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(average_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(average_graph.size()));
    	}
    	
    	origin = 1712929;
    	destination = 928289 ;
    	for(i=40; i< 60;i++) { //20 third couples of nodes on bigger map
    		datas[i] = new ShortestPathData(bigger_graph,bigger_graph.get(origin), bigger_graph.get(destination), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(bigger_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(bigger_graph.size()));


    	}
    	
    	inspector = ArcInspectorFactory.getAllFilters().get(1);
    	origin = 5669 ;
    	destination =  5185;
    	for(i=60; i < 80;i++) { //20 next couples of nodes on Toulouse map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(cars_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(cars_graph.size()));
    	}
    	
    	inspector = ArcInspectorFactory.getAllFilters().get(3);
    	origin = 14737 ;
    	destination =  8182;
    	for(i=80; i < 100;i++) { //20 last couples of nodes on Bordeaux map
    		datas[i] = new ShortestPathData(no_car_graph,no_car_graph.get(origin), no_car_graph.get(destination), inspector);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(no_car_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(no_car_graph.size()));
    	}
    	
    }
        
	public static void launchTestDijkstraDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[100];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_distance_dijkstra_20_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_dijkstra_20_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_distance_dijkstra_20_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_dijkstra_20_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_dijkstra_20_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 20 \n Dijkstra"); average.println("0 \n 20 \n Dijkstra"); big.println("0 \n 20 \n Dijkstra"); cars.println("0 \n 20 \n Dijkstra");no_car.println("0 \n 20 \n Dijkstra");
		for(i = 0; i < 20; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			small.println(" ");
		}
		
		for(i = 20; i < 40; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			average.println(" ");
		}
		
		for(i = 40; i < 60; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			big.println(" ");
		}
		
		for(i = 60; i < 80; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			cars.println(" ");
		}
		
		for(i = 80; i < 100; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			no_car.println(" ");
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	}
	
	public static void launchTestAstarDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[100]; 
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_distance_astar_20_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_astar_20_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_distance_astar_20_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_astar_20_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_astar_20_data.txt"));
		small.println("chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 20 \n A*"); average.println("0 \n 20 \n A*"); big.println("0 \n 20 \n A*"); cars.println("0 \n 20 \n A*");no_car.println("0 \n 20 \n A*");
		for(i = 0; i < 20; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t ",datas[i].getOrigin().getId(),datas[i].getDestination().getId());
			small.println(" ");
		}
		
		for(i = 20; i < 40; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())	average.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			average.println(" ");
		}
		
		for(i = 40; i < 60; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			big.println(" ");

		}
		
		for(i = 60; i < 80; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			cars.println(" ");

		}
		
		for(i = 80; i < 100; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f km \t in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
			no_car.println(" ");
		}
		
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

	
	public static void launchTestDijkstraTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[100];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_temps_dijkstra_20_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_dijkstra_20_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_temps_dijkstra_20_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_dijkstra_20_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_dijkstra_20_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 20 \n Dijkstra"); average.println("0 \n 20 \n Dijkstra"); big.println("0 \n 20 \n Dijkstra"); cars.println("0 \n 20 \n Dijkstra");no_car.println("0 \n 20 \n Dijkstra");
		for(i = 0; i < 20; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			small.println(" ");
		}
		
		for(i = 20; i < 40; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			average.println(" ");
		}
		
		for(i = 40; i < 60; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			big.println(" ");
		}
		
		for(i = 60; i < 80; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			cars.println(" ");
		}
		
		for(i = 80; i < 100; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			no_car.println(" ");
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	}
	
	public static void launchTestAstarTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[100];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_temps_astar_20_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_astar_20_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_temps_astar_20_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_astar_20_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_astar_20_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 20 \n A*"); average.println("0 \n 20 \n A*"); big.println("0 \n 20 \n A*"); cars.println("0 \n 20 \n A*");no_car.println("0 \n 20 \n A*");
		for(i = 0; i < 20; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime() );
			small.println(" ");
		}
		
		for(i = 20; i < 40; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			average.println(" ");
		}
		
		for(i = 40; i < 60; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			big.println(" ");
		}
		
		for(i = 60; i < 80; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			cars.println(" ");
		}
		
		for(i = 80; i < 100; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %d CPU time",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
			no_car.println(" ");
		}
		
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

}

