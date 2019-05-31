package org.insa.algo.shortestpath;
import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected final ShortestPathData data = getInputData();
    protected double time ;
    protected ShortestPathSolution solution ;
    protected final Graph graph = data.getGraph();
    protected final int nbNodes = graph.size();
    protected BinaryHeap<Label> heap = new BinaryHeap<>() ;
    protected int stats[] = new int[3] ;
    
    protected Label[] initLabels() {
    	Label labels[] = new Label[nbNodes] ;
    	int i ;
    	for(i = 0; i < nbNodes; i++) {
        	labels[i] = new Label(graph.get(i));
        }
    	return labels;
    }
    
    public int[] getStats() {
		return stats;
	}
    
    @Override
    protected ShortestPathSolution doRun() {
    	/* Initialization */
        
        // Notify observers about the departure
        notifyOriginProcessed(data.getOrigin());
        int nb_explored =0, nb_marked =0; 
        Arc[] predecessorArcs = new Arc[nbNodes];
        Label labels[] = initLabels(); 
        labels[data.getOrigin().getId()].setCost(0);
        heap.insert(labels[data.getOrigin().getId()]);
        Node current_node ;
        List<Arc> successors ;       
        int id_destination ;
        double distance = 0 ;
        boolean not_marked_exists = true ;
        while(not_marked_exists) {
        	current_node = heap.deleteMin().getCurrent_node() ;
        	labels[current_node.getId()].mark();
        	//Notify observers that the current node has been marked
        	notifyNodeMarked(current_node);
        	nb_marked++ ;
        	successors = current_node.getSuccessors() ;
        	for(Arc current_arc : successors) {
        		distance = data.getCost(current_arc);
        		id_destination = (current_arc.getDestination()).getId() ; 
        		if(!labels[id_destination].isMarked()) {
        			if(labels[id_destination].getCost() > labels[current_node.getId()].getCost() + distance){
        				labels[id_destination].setCost(labels[current_node.getId()].getCost() + distance);
        				//Notify observers that this node has been reached
        				notifyNodeReached(current_arc.getDestination());
        				nb_explored ++ ;
        				heap.insert(labels[id_destination]);
        				labels[id_destination].setFather(current_node);
        				predecessorArcs[id_destination] = current_arc;
        			}
        		}
        	}
        	not_marked_exists = (!heap.isEmpty() && current_node != data.getDestination());
        }
        
        stats[0] = nb_explored ; stats[1] = nb_marked ;
        // Destination has no predecessor, the solution is infeasible...
        if (!data.getDestination().equals(data.getOrigin()) && predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {
            
        	// Notify the observers that destination has been reached.
            notifyDestinationReached(data.getDestination());
            if(data.getOrigin().compareTo(data.getDestination())==0) {
            	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph)); 
            }else {
            	
            	// Create the path from the array of predecessors...
                ArrayList<Arc> arcs = new ArrayList<Arc>();
	            Arc arc = predecessorArcs[data.getDestination().getId()];
	            while (arc != null) {
	               arcs.add(arc);
	               arc = predecessorArcs[arc.getOrigin().getId()];
	            }
	
	            // Reverse the path...
	            Collections.reverse(arcs);
	
	            // Create the final solution.
	            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
	         }        	
        }
        return solution;
    }

	

}
