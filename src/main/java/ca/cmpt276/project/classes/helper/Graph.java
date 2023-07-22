package ca.cmpt276.project.classes.helper;
import java.util.ArrayList;
public class Graph {
	private final ArrayList<Vertex> V;   // An array of vertices forming the graph 
										// (Each vertex contains a set of 4 edges starting from it)
										// The path stores the indices of this array.
	public Graph(){
		V = new ArrayList<Vertex>();
	}
	
	
	/**
     * A method that returns the array of vertices,
     * @return the set of vertices
     */
	public ArrayList<Vertex> getVertexSet(){
		return V;
	}
	
	/**
     * A method that returns the size of the vertex array,
     * @return the size of the vertex set
     */
	public int getVertexSetSize() {
		return V.size();
	}
	
	/**
     * A method that returns a vertex at the input index in the vertex set,
     * @param index the index of the vertex to be returned
     * @return the vertex at the input index in the vertex set
     */
	public Vertex getVertexAt(int index) {
		return V.get(index);
	}
	
	/**
     * A method that returns the index of vertex with a position similar to the input,
     * @param position the position of a vertex being searched for its index in the vertex set
     * @return the index of the corresponding vertex in the vertex set (-1 if there is not such a vertex)
     */
	public int getVertexIndex(Position position) 
	{
		for (int i = 0; i < V.size(); i++) {
			if (position.getX() == V.get(i).getPosition().getX() && position.getY() == V.get(i).getPosition().getY()) {
				return i;
			}
		}
		return -1;
	}
	
	/**
     * A method that returns the index of vertex similar to the input vertex (i.e. position and the edge set),
     * @param v the vertex being compared to search for its index in the vertex set
     * @return the index of the corresponding vertex in the vertex set (-1 if there is not such a vertex)
     */
	public int getVertexIndex(Vertex v) {
		return V.indexOf(v);
	}
	
	/**
     * A method that adds the input vertex into the vertex set if it doesn't exist in there,
     * @param v the vertex being added to the vertex set
     */
	public void appendVertex(Vertex v) {
		if (getVertexIndex(v) != -1)
			return;
		else
			V.add(new Vertex(v.getPosition()));
	}
	
	/**
     * A method that removes the input vertex from the vertex set if it exists in there,
     * @param v the vertex being removed from the vertex set
     */
	public void removeVertex(Vertex v) {
		if (getVertexIndex(v) != -1)
			V.remove(v);
	}
}

