package org.insa.graph;

public class LabelStar extends Label implements Comparable<Label> {
	private Node destination ;
	
	public LabelStar(Node current) {
		super(current);
	}
	
	public void setDestination(Node noeud) {
		this.destination = noeud ;
	}
	
	public double getTotalCost() {
		return (this.getCost() + Point.distance(this.getCurrent_node().getPoint(), this.destination.getPoint())) ;
	}
}
