package org.insa.graph;

import org.insa.algo.AbstractInputData;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label> {
	private double distance_from_dest ;
	
	public LabelStar(Node current,ShortestPathData data) {
		super(current);
		if(data.getMode() == AbstractInputData.Mode.LENGTH){
			this.distance_from_dest = Point.distance(current.getPoint(), data.getDestination().getPoint());
		}else{
			this.distance_from_dest = (Point.distance(current.getPoint(), data.getDestination().getPoint()))*(data.getGraph().getGraphInformation().getMaximumSpeed())*(3600/1000);
		}
	}
		
	public double getTotalCost() {
		return (this.getCost() + this.distance_from_dest) ;
	}
}


