package ca.cmpt276.project.classes.helper;
import java.util.ArrayList;
public class Path {
	private final ArrayList<Integer> P; // An array of indices of vertices (of a graph). It stores only indices, so we will need the graph to get 
										// vertices via these index values.
	
	public Path(){
		P = new ArrayList<Integer>();
	}
	
	/**
     * A method that returns the length of the path,
     * @return the length of the path
     */
	public int length() {
		return P.size();
	}
	
	/**
     * A method that returns the index of the node in the path that has a value similar to the input vertex index,
     * @param vertexIndex the index value being compared to search for the path index of a corresponding node
     * @return the index in the path of a satisfying node
     */
	public int find(int vertexIndex) {
		return P.indexOf(vertexIndex);
	}
	
	/**
     * A method that computes the cost of this path based on an input graph,
     * @param g the graph being referred to compute cost of this path (Vertices in the path are all in the graph)
     * @return the cost of this path
     */
	public int computePathCost(Graph g) {
		boolean isFound;
		int totalCost = 0;
		
		for (int i = 0; i < length() - 1; i++) {
			isFound = false;
			for (int j = 0; j < g.getVertexSet().get(P.get(i)).getEdgeSetSize(); j++) {
				if (P.get(i + 1) == g.getVertexSet().get(P.get(i)).getEdgeSet().get(j).desVertexIndex) {
					isFound = true;
					totalCost += 1;
					break;
				}
			}
			assert(isFound);
		}
		return totalCost;
	}
	
	/**
     * A method that returns the vertex index (in a graph) of the node at the input index in the path,
     * @param index the index of the node being searched for its vertex-index value
     * @return the vertex index (in a graph) of the node at the input index
     */
	public int getIndexOfVertexAt(int index) {
		assert(index >= 0 && index < length());
		return P.get(index);
	}
	
	/**
     * A method that adds a node with an input vertex-index value to the path at the input index,
     * @param index the index position to add the new node into the path
     * @param vertexIndex the index value of the added node
     */
	public void insert(int index, int vertexIndex) {
		assert(index >= 0 && index <= length());
		P.add(index, vertexIndex);
	}
	
	/**
     * A method that adds a node with an input vertex-index value at the end of the path,
     * @param vertexIndex the index value of the added node
     */
	public void append(int vertexIndex) {
		P.add(vertexIndex);
	}
	
	/**
     * A method that removes a node at the input index in the path,
     * @param index the index where the node being removed
     */
	public void remove(int index) {
		assert(index >= 0 && index < length());
		P.remove(index);
	}
	
	/**
     * A method that returns a string list of the position of vertices in the path based on an input graph,
     * @param g the graph being referred to print the list of positions
     * @return an ordered array of position (in text) of all vertices in the path
     */
	public ArrayList<String> getPathNameList(Graph g) {
		ArrayList<String> path = new ArrayList<String>();
		for (int i = 0; i < length(); i++) {
			int vertexIndex = this.getIndexOfVertexAt(i);
			path.add(g.getVertexAt(vertexIndex).getPosition().toString());
		}
		return path;
	}
	
	/**
     * A method that returns the position of the second vertex in the path,
     * @param g the graph being referred to get the position
     * @return the position of the second vertex in the path (next to the beginning vertex)
     */
	public Position getPositionOfSecondVertex(Graph g) {
		return g.getVertexAt(this.getIndexOfVertexAt(1)).getPosition();
	}
	
	
	/**
     * A method that returns the shortest path from one position to another position based on the input graph
     * @param g the graph being referred to print the list of positions
     * @param departure the position of the beginning vertex in the searched path in the graph
     * @param destination the position of the ending vertex in the searched path in the graph
     * @param pathCount the number of counted path that connect these two vertices (NOTE: Input 0 as the argument when calling this function)
     * @param currentPath the current path that serves as a connector in the recursive process (NOTE: Input an empty path when calling this function
     * @return the shortest path from the departure position to the destination position
     */
	public static Path computeMinCostPath(Graph g, Position departure, Position destination, int pathCount, Path currentPath) {
		assert(g.getVertexSetSize() >= 1);
		int depVertexIndex = g.getVertexIndex(departure);
		int desVertexIndex = g.getVertexIndex(destination);
		assert(depVertexIndex != -1 && desVertexIndex != -1);
		Path minCostPath = new Path();
		if (depVertexIndex == desVertexIndex) {
			for (int i = 0; i < currentPath.length(); i++) {
				minCostPath.append(currentPath.getIndexOfVertexAt(i));
			}
			minCostPath.append(desVertexIndex);
			pathCount++;
			return minCostPath;
		} else if (currentPath.find(depVertexIndex) != -1) {
			for (int i = 0; i < currentPath.length(); i++) {
				minCostPath.append(currentPath.getIndexOfVertexAt(i));
			}
			return minCostPath;
		} else {
			Vertex depVertex = new Vertex(g.getVertexSet().get(depVertexIndex).getPosition());
			for (int i = 0; i < g.getVertexSet().get(depVertexIndex).getEdgeSetSize(); i++) {
				Edge temp = new Edge();
				temp.desVertexIndex = g.getVertexSet().get(depVertexIndex).getEdgeSet().get(i).desVertexIndex;
				depVertex.appendEdge(temp.desVertexIndex);
			}
			ArrayList<Edge> E = new ArrayList<Edge>(depVertex.getEdgeSet());
			currentPath.append(depVertexIndex);
			for (int i = 0; i < E.size(); i++) {
				Position nextVertexPosition = g.getVertexAt(E.get(i).desVertexIndex).getPosition();
				if (currentPath.find(E.get(i).desVertexIndex) != -1) {
					continue;
				}
				Path candidatePath = computeMinCostPath(g, nextVertexPosition, destination, pathCount, currentPath);
				if (candidatePath.length() == 0) {
					continue;
				} else if (candidatePath.getIndexOfVertexAt(candidatePath.length() - 1) != desVertexIndex) {
					continue; 
				} else if (minCostPath.length() == 0) {
					minCostPath = new Path();
					for (int j = 0; j < candidatePath.length(); j++) {
						minCostPath.append(candidatePath.getIndexOfVertexAt(j));
					}
					continue;
				} else if (candidatePath.computePathCost(g) < minCostPath.computePathCost(g)) {
					minCostPath = new Path();
					for (int j = 0; j < candidatePath.length(); j++) {
						minCostPath.append(candidatePath.getIndexOfVertexAt(j));
					}
					continue;
				}
			}
			currentPath.remove(currentPath.length() - 1);
			return minCostPath;
		}
	}
}

