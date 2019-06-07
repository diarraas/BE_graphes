package org.insa.algo.shortestpath;

import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;

import org.insa.graph.Graph;
//import org.insa.graph.Node;
import org.insa.algo.* ;
//import org.insa.algo.weakconnectivity.WeaklyConnectedComponentsAlgorithm;
//import org.insa.algo.weakconnectivity.WeaklyConnectedComponentsData;
//import org.insa.algo.weakconnectivity.*;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class PerformancesTest { 

	private static Graph small_graph, bigger_graph, average_graph, cars_graph;// no_car_graph;
    private static ShortestPathData datas[]; 
    private static ArcInspector inspector_all = ArcInspectorFactory.getAllFilters().get(0);
    private static ArcInspector inspector_cars_length = ArcInspectorFactory.getAllFilters().get(1);
    private static ArcInspector inspector_cars_time = ArcInspectorFactory.getAllFilters().get(2); 
    //private static ArcInspector inspector_no_car = ArcInspectorFactory.getAllFilters().get(3);
    private static String small_map, bigger_map, average_map, cars_only; // no_car ;
    private static GraphReader small_reader, bigger_reader, average_reader,car_reader; // no_car_reader;
    private static DijkstraAlgorithm[] dijkstra, astar ;
    
    
    public static void initAll() throws IOException {
    	
    	/*	Initializing Maps		*/
    	
    	average_map = "belgium.mapgr";
        small_map = "chile.mapgr";
        bigger_map = "california.mapgr";
        cars_only = "midi-pyrenees.mapgr";
        //no_car = "fractal.mapgr"; //TRES LOURD. A lancer seul
        
          
        /*	Initializing Readers	*/
        
        small_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(small_map))));
    	bigger_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(bigger_map))));
    	average_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(average_map))));
    	car_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(cars_only))));
    	//no_car_reader = new BinaryGraphReader(
			//	new DataInputStream(new BufferedInputStream(new FileInputStream(no_car))));
    	 
    	/*	Initializing Graphs		*/
    	
    	small_graph = small_reader.read();
    	average_graph = average_reader.read();
    	bigger_graph = bigger_reader.read();
    	cars_graph = car_reader.read();
    	//no_car_graph = no_car_reader.read();
    	 
    	/*	Initializing Data	and Algorithms*/ 
    	datas = new ShortestPathData[300];
    	dijkstra = new DijkstraAlgorithm[300] ;
    	astar = new AStarAlgorithm[300] ;
    	int i;
    	
    	int origin = 588713;
    	int destination = 71369 ;
    	
    	for(i=0; i<50;i++) { //50 first couples of nodes on small map
    		datas[i] = new ShortestPathData(small_graph,small_graph.get(origin), small_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(small_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+1)*1234)%(small_graph.size()));
    	}
    	
    	origin = 778946;
    	destination = 36231 ;
    	for(i=50; i< 100;i++) { //50 second couples of nodes on average map
    		datas[i] = new ShortestPathData(average_graph,average_graph.get(origin), average_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(average_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(average_graph.size()));
    	}
    	
    	origin = 1712929;
    	destination = 928289 ;
    	for(i=100; i< 150;i++) { //50 third couples of nodes on bigger map
    		datas[i] = new ShortestPathData(bigger_graph,bigger_graph.get(origin), bigger_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(bigger_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(bigger_graph.size()));
    	}
    	
    	origin = 388093 ;
    	destination =  237791;
    	for(i=150; i < 200;i++) { //50 next couples of nodes on Midi-pyrénées map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector_cars_length);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*1234)%(cars_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*1234)%(cars_graph.size()));
    	}
    	/*
    	origin = 60852 ;
    	destination =  319735;
    	for(i=200; i < 250;i++) { //50 next couples of nodes on Fractal map
    		datas[i] = new ShortestPathData(no_car_graph,no_car_graph.get(origin), no_car_graph.get(destination), inspector_no_car);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*123)%(no_car_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*123)%(no_car_graph.size()));
    	}
    	*/
    	origin = 388093 ;
    	destination =  237791;
    	for(i=250; i < 300;i++) { //50 last couples of nodes on Midi-pyrénées map(time wise)
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector_cars_time);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1)*123)%(cars_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1)*123)%(cars_graph.size()));
    	}
    }
    
     @Test   
	public static void launchTestDijkstraDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[300];
		int i;
		BufferedWriter small = new BufferedWriter(new FileWriter("chile_distance_dijkstra_50_data.txt"));
		BufferedWriter average = new BufferedWriter(new FileWriter("belgium_distance_dijkstra_50_data.txt"));
		BufferedWriter big = new BufferedWriter(new FileWriter("california_distance_dijkstra_50_data.txt"));
		BufferedWriter cars = new BufferedWriter(new FileWriter("midi-pyrénées_distance_dijkstra_50_data.txt"));
		//BufferedWriter no_car = new BufferedWriter(new FileWriter("fractal_distance_dijkstra_50_data.txt"));
		small.write("Chile\n"); average.write("Belgium\n"); big.write("California\n"); cars.write("Midi-pyrénées\n");//no_car.write("Fractal\n");
		small.write("0\n50\nDijkstra\n"); average.write("0\n50\nDijkstra\n"); big.write("0 \n50\n Dijkstra\n"); cars.write("0\n50\nDijkstra\n");//no_car.write("0\n50\nDijkstra\n");
		int[] stats ;
		for(i = 0; i < 50; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			small.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}

		for(i = 50; i < 100; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			average.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 100; i < 150; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			big.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 150; i < 200; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		/*for(i = 200; i < 250; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}*/
		small.close(); average.close();big.close();//no_car.close();
		cars.close();
	}
     
     @Test   
	public static void launchTestAstarDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[300]; 
		int i ;
		BufferedWriter small = new BufferedWriter(new FileWriter("chile_distance_astar_50_data.txt"));
		BufferedWriter average = new BufferedWriter(new FileWriter("belgium_distance_astar_50_data.txt"));
		BufferedWriter big = new BufferedWriter(new FileWriter("california_distance_astar_50_data.txt"));
		BufferedWriter cars = new BufferedWriter(new FileWriter("midi-pyrénées_distance_astar_50_data.txt"));
		//BufferedWriter no_car = new BufferedWriter(new FileWriter("fractal_distance_astar_50_data.txt"));
		small.write("Chile\n"); average.write("Belgium\n"); big.write("California\n"); cars.write("Midi-pyrénées\n");//no_car.write("Fractal\n");
		small.write("0\n50\nA*\n"); average.write("0\n50\nA*\n"); big.write("0\n50\nA*\n"); cars.write("0\n50\nA*\n");//no_car.write("0\n50\nA*\n");
		int[] stats ;
		for(i = 0; i < 50; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())average.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 50; i < 100; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())average.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 100; i < 150; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		big.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 150; i < 200; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		cars.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");

		}
		
		/*for(i =200; i < 250; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		no_car.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getLength()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}*/
		
		small.close(); average.close();big.close();//no_car.close();
		cars.close();
	}

     @Test   
	public static void launchTestDijkstraTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[300];
		int i ;
		BufferedWriter small = new BufferedWriter(new FileWriter("chile_temps_dijkstra_50_data.txt"));
		BufferedWriter average = new BufferedWriter(new FileWriter("belgium_temps_dijkstra_50_data.txt"));
		BufferedWriter big = new BufferedWriter(new FileWriter("california_temps_dijkstra_50_data.txt"));
		BufferedWriter cars = new BufferedWriter(new FileWriter("midi-pyrénées_temps_dijkstra_50_data.txt"));
		//BufferedWriter no_car = new BufferedWriter(new FileWriter("fractal_temps_dijkstra_50_data.txt"));
		small.write("Chile\n"); average.write("Belgium\n"); big.write("California\n"); cars.write("Midi-pyrénées\n");//no_car.write("Fractal\n");
		small.write("1\n50\nDijkstra\n"); average.write("1\n50\nDijkstra\n"); big.write("1\n50\nDijkstra\n"); cars.write("1\n50\nDijkstra\n");//no_car.write("1\n50\nDijkstra\n");
		int[] stats ;
		for(i = 0; i < 50; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			small.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 50; i < 100; i++) { 
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			average.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 100; i < 150; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			big.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 250; i < 300; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		/*for(i = 200; i < 250; i++) {
			solutions[i] = dijkstra[i].run();
			stats = dijkstra[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}*/
		small.close(); average.close();big.close();
		//no_car.close();
		cars.close();
	}
      
     @Test   
	public static void launchTestAstarTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[300];
		int i ;
		BufferedWriter small = new BufferedWriter(new FileWriter("chile_temps_astar_50_data.txt"));
		BufferedWriter average = new BufferedWriter(new FileWriter("belgium_temps_astar_50_data.txt"));
		BufferedWriter big = new BufferedWriter(new FileWriter("california_temps_astar_50_data.txt"));
		BufferedWriter cars = new BufferedWriter(new FileWriter("Midi-pyrénées_temps_astar_50_data.txt"));
		//BufferedWriter no_car = new BufferedWriter(new FileWriter("fractal_temps_astar_50_data.txt"));
		small.write("Chile\n"); average.write("Belgium\n"); big.write("California\n"); cars.write("Midi-pyrénées\n");//no_car.write("Fractal\n");
		small.write("1\n50\nA*\n"); average.write("1\n50\nA*\n"); big.write("1\n50\nA*\n"); cars.write("1\n50\nA*\n");//no_car.write("1\n50\nA*\n");
		int[] stats ;
		for(i =0 ; i < 50; i++) { 
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		small.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
		for(i = 50; i < 100; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		average.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		 
		for(i = 100; i < 150; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		big.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		} 
		
		for(i = 250; i < 300; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		cars.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}
		
	/*	for(i = 200; i < 250; i++) {
			solutions[i] = astar[i].run();
			stats = astar[i].getStats();
			if(solutions[i] != null && solutions[i].isFeasible())		no_car.write(""+datas[i].getOrigin().getId()+"\t"+datas[i].getDestination().getId()+"\t"+solutions[i].getPath().getMinimumTravelTime()+"\t"+solutions[i].getSolvingTime().toMillis()+"\t"+stats[0]+"\t"+stats[1]+"\n");
		}*/
		
		small.close(); average.close();big.close();//no_car.close();
		cars.close();
	}
}