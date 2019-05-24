package org.insa.graph;

public class LabelStar extends Label implements Comparable<Label> {
	private double distance_from_dest ;
	
	public LabelStar(Node current,Node destination) {
		super(current);
		this.distance_from_dest = Point.distance(current.getPoint(), destination.getPoint());
	}
		
	public double getTotalCost() {
		return (this.getCost() + this.distance_from_dest) ;
	}
	
}
