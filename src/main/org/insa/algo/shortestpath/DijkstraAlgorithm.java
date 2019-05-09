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

    @Override
    protected ShortestPathSolution doRun() {
    	/* Initialization */
    	ShortestPathSolution solution = null ;
    	ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        Arc[] predecessorArcs = new Arc[nbNodes];
        int i ;
        Label labels[] = new Label[nbNodes] ;
        BinaryHeap<Node> stack = new BinaryHeap<>() ;
      
        for(i = 0; i < nbNodes; i++) {
        	labels[i] = new Label(graph.get(i));
        }
        
        labels[0].setCost(0);
        stack.insert(labels[0].getCurrent_node());
        Node current_node ;
        List<Arc> successors ;       
        int id_destination ;
        boolean changed = false ;
        double distance = 0 ;
        boolean not_marked_exists = true ;
        while(not_marked_exists) {
        	current_node = stack.deleteMin() ;
        	labels[current_node.getId()].mark();
        	successors = current_node.getSuccessors() ;
        	for(Arc current_arc : successors) {
        		distance = data.getCost(current_arc);
        		id_destination = (current_arc.getDestination()).getId() ; 
        		if(!labels[id_destination].isMarked()) {
        			if(labels[id_destination].getCost() > labels[current_node.getId()].getCost() + distance){
        				labels[id_destination].setCost(labels[current_node.getId()].getCost() + distance);
        				changed = true ;
        			}
        			if(changed) {
        				stack.insert(labels[id_destination].getCurrent_node());
        				labels[id_destination].setFather(current_node);
        				predecessorArcs[id_destination] = current_arc;
        			}
        		}
        	}
        	not_marked_exists = (!stack.isEmpty() && current_node != data.getDestination());
        }
        
        // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<Arc>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
                //arc = predecessorArcs[labels[arc.getDestination().getId()].getFather().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }

}
