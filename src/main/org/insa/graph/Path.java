package org.insa.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator ;


/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * author : DHOUIB
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
	public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
    	if(nodes.size()==0) {
        	return new Path(graph);
        }
        if(nodes.size() == 1) {
        	return new Path(graph, nodes.get(0));
        }
   
        List<Arc> arcs = new ArrayList<Arc>();        
        //If there's nodes is empty
        if(nodes.size()==0) {
        	return new Path(graph);
        }
        
        //If there's only one node in the list
        if(nodes.size() == 1) {
        	return new Path(graph, nodes.get(0));
        }
        
        //If there's 2 or more nodes in the list
        ListIterator<Node> it = nodes.listIterator();
        
        Node current=it.next();
        Arc chemin=current.getSuccessors().get(0);
        List<Arc> Arc_valid = new ArrayList<Arc>();
        
        while (it.hasNext()) {
        	Node aux = current;
        	current=it.next();
        	
        	//Find list of Arcs valid:
        	Node element;
        	for(int j=0;j<aux.getSuccessors().size();j++) {
        		element = aux.getSuccessors().get(j).getDestination();
        		if(element==current) {
        			Arc_valid.add(aux.getSuccessors().get(j));
        		}
        	}
        	
        	//The next node is a successor of the current node
        	if(Arc_valid.size()!=0){
        		//If there is only one possibility
        		if(aux.getNumberOfSuccessors()==1) {
        			chemin = aux.getSuccessors().get(0);
        		}
        		//If there is so many possibilities
        		else {
        			int i;
        			double MinTime =Arc_valid.get(0).getMinimumTravelTime();
        			for(i=0; i<Arc_valid.size();i++) {
            			Arc iteration = Arc_valid.get(i);
            			if(iteration.getMinimumTravelTime()<MinTime) {
            				MinTime=iteration.getMinimumTravelTime();
            				chemin=Arc_valid.get(i);
            			}
        			}
        		}
    			arcs.add(chemin);
        		
        	}
        	else {
            //The next node is not a successor of the next node 
        		throw new IllegalArgumentException();
        	}
        	Arc_valid.clear();
        }
        return new Path(graph, arcs);
       
    }
    /**
     * author : DIARRA
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        if(nodes.size()==0) {
        	return new Path(graph);
        }
        
        if(nodes.size() == 1) {
        	return new Path(graph, nodes.get(0));
        }
   
        List<Arc> arcs = new ArrayList<Arc>();
        ListIterator<Node> it = nodes.listIterator() ;
        Node previous_node = null;
        Node current_node = it.next();
        
        while(it.hasNext()) {
        	previous_node = current_node;
            current_node = it.next();
        	List<Arc> successors = previous_node.getSuccessors() ;
        	ListIterator<Arc> arc_it = successors.listIterator() ;
        	Arc current_arc = null ; 
        	Arc optimal_arc = null ;
        	float longueur = 0 ;
        	while(arc_it.hasNext()) {
        		current_arc = arc_it.next();
        		if(current_arc.getDestination() == current_node) { //bon successeur
        			if(longueur == 0 || current_arc.getLength() <= longueur) {
    					optimal_arc = current_arc ;
    					longueur = optimal_arc.getLength();
    		    	}
        		}
        	}
	        if(optimal_arc != null) {
	        	arcs.add(optimal_arc);
	        }else{
	        	throw new IllegalArgumentException("Invalid list of nodes.");
	        }
        	
        }
        return new Path(graph, arcs);
       
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * author : DIARRA
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * 
     */
    public boolean isValid() {
    	List<Arc> arcs = this.getArcs() ;
    	ListIterator<Arc> it = arcs.listIterator() ;
    	Arc current ;
    	Arc previous ;
    	if(this.isEmpty() || this.size() == 1) {
        	return true ;
    	}
    	if(arcs.get(0).getOrigin() == this.origin) {
    		previous = it.next() ;
    		current = it.next();
    		while(it.hasNext() && (previous.getDestination() == current.getOrigin())) {	
	        	previous = current;
	        	current = it.next();
	        }
	        if(!it.hasNext() || previous.getDestination() == current.getOrigin()){
	        	return true ;
	        }
    	}
	     return false;
    }

    /**
     * author : DHOUIB
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     */
    public float getLength() {
    	float length=0;
    	List<Arc> arcs =this.getArcs();  	
    	ListIterator<Arc> it = arcs.listIterator();
    	while(it.hasNext()) {
    		Arc ce_arc = it.next();
    		length=length + ce_arc.getLength();
    	}
        return length;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * author : DIARRA
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     */
    public double getTravelTime(double speed) {
    	List<Arc> arcs = this.getArcs() ;
    	ListIterator<Arc> it = arcs.listIterator() ;
    	double travelTime = 0 ;
    	if(!this.isEmpty()) {
	        while(it.hasNext()) {
	        	travelTime += (it.next().getTravelTime(speed));
	        }
    	}
    	return travelTime ;
    }

    /**
     * author : DHOUIB
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     */
    public double getMinimumTravelTime() {
    	double time=0;
    	List<Arc> arcs =this.getArcs();  	
    	ListIterator<Arc> it = arcs.listIterator();
    	while(it.hasNext()) {
    		Arc ce_arc=it.next();
    		time= time+ce_arc.getMinimumTravelTime();
    	}
        return time;
    }

}

