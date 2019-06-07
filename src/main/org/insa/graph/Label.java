package org.insa.graph;

public class Label implements Comparable<Label>{
	
	/*  Attributes  */
	private Node current_node ;
	private boolean marked ;
	private double cost ;
	private Node father ;
	private int id ; //Numéro du sommet associé
	
	public Label(Node current) {
		this.current_node = current ;
		this.father = null ;
		this.marked = false ;
		this.id = current.getId();
		this.cost = Double.POSITIVE_INFINITY ;
	}
	
	public double getCost() {
		return this.cost ;
	}
	
	public int getId() {
		return this.id ;
	}
	
	public Node getFather() {
		return this.father ;
	}
	
	public Node getCurrent_node() {
		return this.current_node ;
	}
	
	public boolean isMarked() {
		return this.marked ;
	}
	
	public void setCost(double new_cost) {
		this.cost = new_cost ;
	}
	
	public void mark() {
		this.marked = true ;
	}
	
	public void setFather(Node fath) {
		this.father = fath ;
	}
	public double getTotalCost() {
		return this.cost ;
	}
	
	public int compareTo(Label l) {
		int result = 0 ;
		if(this.getTotalCost() < l.getTotalCost()) {
			result = -1 ;
		}else if(this.getTotalCost() > l.getTotalCost()) {
			result = 1 ;
		}
		return result ;
	}

}
