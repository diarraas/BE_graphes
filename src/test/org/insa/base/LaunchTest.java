package org.insa.base;
import java.io.IOException;
import org.insa.algo.shortestpath.*;

public class LaunchTest {

	public static void main(String[] args) throws IOException{
		
		//Initializing Tests components
		System.out.println("Initializing Tests components");
		PerformancesTest.initAll();
		 
		//Lauching tests
		System.out.println("Launching Tests");

		PerformancesTest.launchTestAstarDistance();
		
		System.out.println("A* Distance ----- OK");
		
		PerformancesTest.launchTestAstarTime();
		
		System.out.println("A* Time ----- OK");
		 
		PerformancesTest.launchTestDijkstraDistance();
		
		System.out.println("Dijkstra Distance ----- OK");
		
		PerformancesTest.launchTestDijkstraTime();
		
		System.out.println("Dijkstra TIme ----- OK");
		
	}

}
