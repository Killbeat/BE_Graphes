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

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        ArrayList<Arc> solutionArcs = new ArrayList<Arc>();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        Label[] labelList = new Label[nbNodes];
        /*
        // initialize heap and labelList
        for (Node node : graph.getNodes()) {
        	if (node != data.getOrigin()) {
        		Label l = new Label(node, false, 1000000, null);
            	heap.insert(l);
            	labelList[node.getId()] = l;
        	} else {
        		Label l = new Label(node, true, 0, null);
        		heap.insert(l);
        		labelList[node.getId()] = l;
        	}
        }
        */
        
        Label l = new Label(data.getOrigin(), true, 0, null);
        labelList[data.getOrigin().getId()] = l;
        heap.insert(l);
        notifyOriginProcessed(data.getOrigin());
        
        while (!heap.isEmpty() || !labelList[data.getDestination().getId()].isCostKnown()) {
        	Node currentNode = heap.deleteMin().getNode();
        	
        	labelList[currentNode.getId()].setCostKnown(true);
        	notifyNodeMarked(currentNode);
        	
        	for (Arc arc : currentNode.getSuccessors()) {
        		
        		if(labelList[arc.getDestination().getId()] == null) 
        		{
        			Label x = new Label(arc.getDestination(), false, labelList[currentNode.getId()].getCost() + arc.getLength(), arc);
        			heap.insert(x);
        			labelList[arc.getDestination().getId()] = x;
        			notifyNodeReached(arc.getDestination());
        		}
        		else 
        		{
        			if (labelList[currentNode.getId()].getCost() + arc.getLength() < labelList[arc.getDestination().getId()].getCost()) {
        				heap.remove(labelList[arc.getDestination().getId()]);
        				labelList[arc.getDestination().getId()].setCost(labelList[currentNode.getId()].getCost() + arc.getLength());
            			labelList[arc.getDestination().getId()].setPredecessor(arc);
            			heap.insert(labelList[arc.getDestination().getId()]);
        			}
        		}
        	}
        }
    
        //construct solution
        if (labelList[data.getDestination().getId()].getPredecessor() == null) {
        	return new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        notifyDestinationReached(data.getDestination());
        
        Arc arc = labelList[data.getDestination().getId()].getPredecessor();
        while (arc != null) {
        	solutionArcs.add(arc);
        	arc = labelList[arc.getOrigin().getId()].getPredecessor();
        }
        
        Collections.reverse(solutionArcs);
                
        solution = new ShortestPathSolution(data, Status.FEASIBLE, new Path(graph, solutionArcs));
                
        return solution;
    }

}
