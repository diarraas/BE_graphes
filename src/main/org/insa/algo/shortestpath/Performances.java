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
    private static ArcInspector inspector_all = ArcInspectorFactory.getAllFilters().get(0);
    private static ArcInspector inspector_cars_length = ArcInspectorFactory.getAllFilters().get(1);
    private static ArcInspector inspector_cars_time = ArcInspectorFactory.getAllFilters().get(2); 
    private static ArcInspector inspector_no_car = ArcInspectorFactory.getAllFilters().get(3);
    private static String small_map, bigger_map, average_map, cars_only, no_car ;
    private static GraphReader small_reader, bigger_reader, average_reader,car_reader, no_car_reader;
    private static DijkstraAlgorithm[] dijkstra, astar ;
    
    
    public static void initAll() throws IOException {
    	
    	/*	Initializing Maps		*/
        
    	
    	average_map = "/home/emna/Bureau/S2/BE GRAPHE/BE_graphes/maps/belgium.mapgr";
        small_map = "/home/emna/Bureau/S2/BE GRAPHE/BE_graphes/maps/chile.mapgr";
        bigger_map = "/home/emna/Bureau/S2/BE GRAPHE/BE_graphes/maps/california.mapgr";
        cars_only = "/home/emna/Bureau/S2/BE GRAPHE/BE_graphes/maps/toulouse.mapgr";
        no_car = "/home/emna/Bureau/S2/BE GRAPHE/BE_graphes/maps/bordeaux.mapgr";
        
        /*
       
        average_map = "C:/Users/Assa Diarra/Documents/Uni/BE_graphes/libs/belgium.mapgr";
        small_map = "C:/Users/Assa Diarra/Documents/Uni/BE_graphes/libs/chile.mapgr";
        bigger_map = "C:/Users/Assa Diarra/Documents/Uni/BE_graphes/libs/california.mapgr";
        cars_only = "C:/Users/Assa Diarra/Documents/Uni/BE_graphes/libs/toulouse.mapgr";
        no_car = "C:/Users/Assa Diarra/Documents/Uni/BE_graphes/libs/bordeaux.mapgr"; 
         
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
    	
    	datas = new ShortestPathData[600];
    	dijkstra = new DijkstraAlgorithm[600] ;
    	astar = new AStarAlgorithm[600] ;
    	int i;
    	int origin = 588713;
    	int destination = 71369 ;
    	
    	for(i=0; i<100;i++) { //100 first couples of nodes on small map
    		datas[i] = new ShortestPathData(small_graph,small_graph.get(origin), small_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(small_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+1)*1234)%(small_graph.size()));
    	}
    	
    	origin = 778946;
    	destination = 36231 ;
    	for(i=100; i< 200;i++) { //100 second couples of nodes on average map
    		datas[i] = new ShortestPathData(average_graph,average_graph.get(origin), average_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(average_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(average_graph.size()));
    	}
    	
    	origin = 1712929;
    	destination = 928289 ;
    	for(i=200; i< 300;i++) { //100 third couples of nodes on bigger map
    		datas[i] = new ShortestPathData(bigger_graph,bigger_graph.get(origin), bigger_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(bigger_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(bigger_graph.size()));
    	}
    	
    	origin = 5669 ;
    	destination =  5185;
    	for(i=300; i < 400;i++) { //100 next couples of nodes on Toulouse map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector_cars_length);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(cars_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(cars_graph.size()));
    	}
    	
    	origin = 14737 ;
    	destination =  8182;
    	for(i=400; i < 500;i++) { //100 last couples of nodes on Bordeaux map
    		datas[i] = new ShortestPathData(no_car_graph,no_car_graph.get(origin), no_car_graph.get(destination), inspector_no_car);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*123)%(no_car_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*123)%(no_car_graph.size()));
    	}
    	
    	origin = 5669 ;
    	destination =  5185;
    	for(i=500; i < 600;i++) { //100 next couples of nodes on Toulouse map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector_cars_time);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*123)%(cars_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*123)%(cars_graph.size()));
    	}
  	
    }
        
	public static void launchTestDijkstraDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[600];
		int i;
		PrintWriter small = new PrintWriter(new FileWriter("chile_distance_dijkstra_100_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_dijkstra_100_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_distance_dijkstra_100_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_dijkstra_100_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_dijkstra_100_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 100 \n Dijkstra"); average.println("0 \n 100 \n Dijkstra"); big.println("0 \n 100 \n Dijkstra"); cars.println("0 \n 100 \n Dijkstra");no_car.println("0 \n 100 \n Dijkstra");
		for(i = 0; i < 50; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}

		for(i = 50; i < 100; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		
		for(i = 100; i < 150; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		
		for(i = 150; i < 200; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		
		for(i = 200; i < 250; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	}
	
	public static void launchTestAstarDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[600]; 
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_distance_astar_100_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_astar_100_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_distance_astar_100_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_astar_100_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_astar_100_data.txt"));
		small.println("chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0 \n 100 \n A*"); average.println("0 \n 100 \n A*"); big.println("0 \n 100 \n A*"); cars.println("0 \n 100 \n A*");no_car.println("0 \n 100 \n A*");
		for(i = 0; i < 100; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t ",datas[i].getOrigin().getId(),datas[i].getDestination().getId());
		}
		
		for(i = 100; i < 200; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())	average.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		
		for(i = 200; i < 300; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());

		}
		
		for(i = 300; i < 400; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());

		}
		
		for(i = 400; i < 500; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f km \t in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),solutions[i].getSolvingTime());
		}
		
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

	
	public static void launchTestDijkstraTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[600];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_temps_dijkstra_100_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_dijkstra_100_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_temps_dijkstra_100_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_dijkstra_100_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_dijkstra_100_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("1 \n 100 \n Dijkstra"); average.println("1 \n 100 \n Dijkstra"); big.println("0 \n 100 \n Dijkstra"); cars.println("0 \n 100 \n Dijkstra");no_car.println("0 \n 100 \n Dijkstra");
		for(i = 0; i < 100; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 100; i < 200; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 200; i < 300; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 500; i < 600; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 400; i < 500; i++) {
			solutions[i] = dijkstra[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	}
	
	public static void launchTestAstarTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[600];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("chile_temps_astar_100_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_astar_100_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("california_temps_astar_100_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_astar_100_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_astar_100_data.txt"));
		small.println("Chile"); average.println("Belgium"); big.println("California"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("1 \n 100 \n A*"); average.println("1 \n 100 \n A*"); big.println("0 \n 100 \n A*"); cars.println("0 \n 100 \n A*");no_car.println("0 \n 100 \n A*");
		for(i =0 ; i < 100; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime() );
		}
		
		for(i = 100; i < 200; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 200; i < 300; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 500; i < 600; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		for(i = 400; i < 500; i++) {
			solutions[i] = astar[i].doRun();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %d CPU time\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),solutions[i].getSolvingTime());
		}
		
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

}
