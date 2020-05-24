package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
  
    @Override
    protected LabelStar CreateLabel(Node n, boolean costknown, double cost, Arc parent, ShortestPathData data) {
    	if (data.getMode() == AbstractInputData.Mode.LENGTH)
    		return new LabelStar(n, costknown, cost, parent, (float)Point.distance(n.getPoint(), data.getDestination().getPoint()));
    	else if (data.getMode() == AbstractInputData.Mode.TIME)
    	{
    		int graph_speed = data.getGraph().getGraphInformation().getMaximumSpeed();
    		int data_speed = data.getMaximumSpeed();
    		int speed = 0;
    		double ec = 0;
    		if (graph_speed == -1 && data_speed == -1) 
    			speed = 130; 
    		else if (graph_speed == -1)
    			speed = data_speed;
    		else if (data_speed == -1)
    			speed = graph_speed;
    		else 
    			speed = Math.min(graph_speed, data_speed);
    		
    		ec = 3.6 * n.getPoint().distanceTo(data.getDestination().getPoint())/(double)speed;
    		return new LabelStar(n, costknown, cost, parent, ec);
    	} else
    		return null;
    }
}
