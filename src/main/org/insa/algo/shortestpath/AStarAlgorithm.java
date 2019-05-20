package org.insa.algo.shortestpath;

import org.insa.graph.Label;
import org.insa.graph.LabelStar;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label[] initLabels() {
    	LabelStar labels[] = new LabelStar[nbNodes] ;
    	int i ;
    	for(i = 0; i < nbNodes; i++) {
        	labels[i] = new LabelStar(graph.get(i), data.getDestination());
        }
    	return labels;
    }
}
