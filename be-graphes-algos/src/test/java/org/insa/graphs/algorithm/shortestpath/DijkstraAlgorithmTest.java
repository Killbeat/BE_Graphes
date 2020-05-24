package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;

public class DijkstraAlgorithmTest {

	private static String map1Name;
	private static GraphReader reader1;
	private static Graph graph1;
	
	private static String map2Name;
	private static GraphReader reader2;
	private static Graph graph2;
	
	private static String map3Name;
	private static GraphReader reader3;
	private static Graph graph3;
	
	@BeforeClass
	public static void initAll() throws IOException {
        map1Name = "../Maps/carre.mapgr";      

        reader1 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map1Name))));

        graph1 = reader1.read();

        map2Name = "../Maps/insa.mapgr";      

        reader2 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map2Name))));

        graph2 = reader2.read();
        
        map3Name = "../Maps/toulouse.mapgr";

        reader3 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map3Name))));

        graph3 = reader3.read();
        
	}
	
	@Test
	public void testPathCorrect() {
		ShortestPathData spd = new ShortestPathData(graph1, graph1.get(0), graph1.get(10), ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathSolution sps = new DijkstraAlgorithm(spd).doRun();
		Path solution = sps.getPath();
		assertTrue(solution.isValid());
	}

	@Test
	public void scenario1shortest() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(195), graph2.get(659), ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution.getMinimumTravelTime() - assolution.getMinimumTravelTime() < 1e-6 && dsolution.getMinimumTravelTime() - bfsolution.getMinimumTravelTime() < 1e-6);
	}

	@Test
	public void scenario1fastest() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(195), graph2.get(659), ArcInspectorFactory.getAllFilters().get(2));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution.getMinimumTravelTime() - assolution.getMinimumTravelTime() < 1e-6 && dsolution.getMinimumTravelTime() - bfsolution.getMinimumTravelTime() < 1e-6);
	}
	
	@Test
	public void inverseScenario1shortest() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(659), graph2.get(195), ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution.getMinimumTravelTime() - assolution.getMinimumTravelTime() < 1e-6 && dsolution.getMinimumTravelTime() - bfsolution.getMinimumTravelTime() < 1e-6);
	}
	
	@Test
	public void inverseScenario1fastest() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(659), graph2.get(195), ArcInspectorFactory.getAllFilters().get(2));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution.getMinimumTravelTime() - assolution.getMinimumTravelTime() < 1e-6 && dsolution.getMinimumTravelTime() - bfsolution.getMinimumTravelTime() < 1e-6);
	}
	
	@Test
	public void testDestinationEqualsOriginLength() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(195), graph2.get(195), ArcInspectorFactory.getAllFilters().get(0));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution == null && assolution == null && bfsolution == null);
	}
	
	@Test
	public void testDestinationEqualsOriginTime() {
		ShortestPathData spd = new ShortestPathData(graph2, graph2.get(195), graph2.get(195), ArcInspectorFactory.getAllFilters().get(2));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		Path dsolution = dsps.getPath();
		Path assolution = assps.getPath();
		Path bfsolution = bfsps.getPath();
		assertTrue(dsolution == null && assolution == null && bfsolution == null);
	}
	
	@Test
	public void testInfeasibleLength() {
		ShortestPathData spd = new ShortestPathData(graph3, graph3.get(7449), graph3.get(8975), ArcInspectorFactory.getAllFilters().get(1));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		assertTrue(bfsps.getStatus() == AbstractSolution.Status.INFEASIBLE);
		assertTrue(dsps.getStatus() == AbstractSolution.Status.INFEASIBLE);
		assertTrue(assps.getStatus() == AbstractSolution.Status.INFEASIBLE); 
	}

	@Test
	public void testInfeasibleTime() {
		ShortestPathData spd = new ShortestPathData(graph3, graph3.get(7449), graph3.get(8975), ArcInspectorFactory.getAllFilters().get(3));
		ShortestPathSolution dsps = new DijkstraAlgorithm(spd).doRun();
		ShortestPathSolution assps = new AStarAlgorithm(spd).doRun();
		ShortestPathSolution bfsps = new BellmanFordAlgorithm(spd).doRun();
		assertTrue(bfsps.getStatus() == AbstractSolution.Status.INFEASIBLE);
		assertTrue(dsps.getStatus() == AbstractSolution.Status.INFEASIBLE);
		assertTrue(assps.getStatus() == AbstractSolution.Status.INFEASIBLE);		
	}
}
