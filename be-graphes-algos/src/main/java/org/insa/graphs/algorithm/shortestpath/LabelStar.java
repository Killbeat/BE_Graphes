package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {

	
	private float estimatedCost;
	
	public LabelStar(Node sc, boolean m, float c, Arc p, float ec) {
		super(sc, m, c, p);
		this.estimatedCost = ec;
	}
	
	public float getEstimatedCost() {
		return this.estimatedCost;
	}
	
	public void setEstimatedCost(float value) {
		this.estimatedCost = value;
	}
	
	@Override
	public float getTotalCost() {
		return this.estimatedCost + this.cost;
	}
}
