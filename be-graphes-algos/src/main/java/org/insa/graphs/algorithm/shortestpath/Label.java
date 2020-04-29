package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

class Label implements Comparable<Label> {
	// node associated with this label
	private Node currentNode;
	// is the min cost known by the algorithm already ?
	private boolean costKnown;
	// current cost from start node to this one
	protected float cost;
	// precedent arc corresponding to shortest path from start node
	private Arc predecessor;
	
	public Label(Node sc, boolean m, float c, Arc p) {
		this.currentNode = sc;
		this.costKnown = m;
		this.cost = c;
		this.predecessor = p;
	}
	
	// getters setters
	
	public float getCost() {
		return this.cost;
	}
	
	public Node getNode() {
		return this.currentNode;
	}
	
	public boolean isCostKnown() {
		return this.costKnown;
	}
	
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public void setCostKnown(boolean costKnown) {
		this.costKnown = costKnown;
	}
	
	public void setPredecessor(Arc pred) {
		this.predecessor = pred;
	}
	
	public Arc getPredecessor() {
		return this.predecessor;
	}
	
	public float getTotalCost() {
		return this.cost;
	}
	
	@Override
	public int compareTo(Label otherLabel) {
		if (this.getTotalCost() < otherLabel.getTotalCost()) {
			return -1;
		} else if (this.getTotalCost() == otherLabel.getTotalCost()) {
			return 0;
		} else {
			return 1;
		}
	}
	
}
