package org.insa.base;
import java.io.IOException;
import org.insa.algo.shortestpath.*;

public class LaunchTest {

	public static void main(String[] args) throws IOException{
		
		//Initializing Tests components
		System.out.println("Initializing Tests components");
		PerformancesBis.initAll();
		 
		//Lauching tests
		System.out.println("Launching Tests");

		PerformancesBis.launchTestAstarDistance();
		
		System.out.println("A* Distance ----- OK");
		
		PerformancesBis.launchTestAstarTime();
		
		System.out.println("A* Time ----- OK");
		 
		PerformancesBis.launchTestDijkstraDistance();
		
		System.out.println("Dijkstra Distance ----- OK");
		
		PerformancesBis.launchTestDijkstraTime();
		
		System.out.println("Dijkstra TIme ----- OK");
		
	}

}
