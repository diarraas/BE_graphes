package org.insa.base;
import java.io.IOException;

import org.insa.algo.shortestpath.Performances;

public class LaunchTest {

	public static void main(String[] args) throws IOException{
		
		//Initializing Tests components
		System.out.println("Initializing Tests components");
		Performances.initAll();
		
		//Lauching tests
		System.out.println("Launching Tests");

		Performances.launchTestAstarDistance();
		
		System.out.println("A* Distance ----- OK");
		
		Performances.launchTestAstarTime();
		
		System.out.println("A* Time ----- OK");
		
		Performances.launchTestDijkstraDistance();
		
		System.out.println("Dijkstra Distance ----- OK");
		
		Performances.launchTestDijkstraTime();
		
		System.out.println("Dijkstra TIme ----- OK");
		
	}

}
