package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	private double estimatedCost;
	
	public LabelStar(Node sc, boolean m, double c, Arc p, double ec) {
		super(sc, m, c, p);
		this.estimatedCost = ec;
	}
	
	public double getEstimatedCost() {
		return this.estimatedCost;
	}
	
	public void setEstimatedCost(double value) {
		this.estimatedCost = value;
	}
	
	@Override
	public double getTotalCost() {
		return this.estimatedCost + this.cost;
	}
}
