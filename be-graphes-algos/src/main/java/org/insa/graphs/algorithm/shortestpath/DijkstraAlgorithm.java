package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }      

    protected Label CreateLabel(Node n, boolean costknown, double cost, Arc parent, ShortestPathData data) {
    	return new Label(n, costknown, cost, parent);
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        ArrayList<Arc> solutionArcs = new ArrayList<Arc>();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        Label[] labelList = new Label[nbNodes];
        
        Label l = this.CreateLabel(data.getOrigin(), true, 0, null, data);
        labelList[data.getOrigin().getId()] = l;
        heap.insert(l);
        notifyOriginProcessed(data.getOrigin());
        
        while (!heap.isEmpty()) {
        	Node currentNode = heap.deleteMin().getNode();
        	
        	labelList[currentNode.getId()].setCostKnown(true);
        	notifyNodeMarked(currentNode);
        	
        	for (Arc arc : currentNode.getSuccessors()) {
        		
        		if (!data.isAllowed(arc))
        			continue;
        		
        		if(labelList[arc.getDestination().getId()] == null) 
        		{
        			Label x = this.CreateLabel(arc.getDestination(), false, labelList[currentNode.getId()].getCost() + data.getCost(arc), arc, data);
        			heap.insert(x);
        			labelList[arc.getDestination().getId()] = x;
        			notifyNodeReached(arc.getDestination());
        		}
        		else
        		{
        			if (labelList[currentNode.getId()].getCost() + data.getCost(arc) < labelList[arc.getDestination().getId()].getCost()) {
      					heap.remove(labelList[arc.getDestination().getId()]);        			
        				labelList[arc.getDestination().getId()].setCost(labelList[currentNode.getId()].getCost() +  data.getCost(arc));
            			labelList[arc.getDestination().getId()].setPredecessor(arc);
            			heap.insert(labelList[arc.getDestination().getId()]);
        			}
        		}
        	}
        	
        	if (labelList[data.getDestination().getId()] != null)
        		if (labelList[data.getDestination().getId()].isCostKnown())
        			break;        	
        }
    
        //construct solution
        try {
        	if (labelList[data.getDestination().getId()].getPredecessor() == null) {
        		return new ShortestPathSolution(data, Status.INFEASIBLE);
        	}
        }
        catch (NullPointerException exception) {
        	return new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        notifyDestinationReached(data.getDestination());
        
        Arc arc = labelList[data.getDestination().getId()].getPredecessor();
        while (arc != null) {
        	solutionArcs.add(arc);
        	arc = labelList[arc.getOrigin().getId()].getPredecessor();
        }
        
        Collections.reverse(solutionArcs);
                
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, solutionArcs));
        
        return solution;
    }

}