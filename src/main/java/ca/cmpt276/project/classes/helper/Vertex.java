package ca.cmpt276.project.classes.helper;
import java.util.ArrayList;

public class Vertex {
	private final Position position; 
	private final ArrayList<Edge> E; //A container to store the edges emanating from this vertex. All the elements of
	//E have the same origin vertex which is the *this object. But they have different destination 
	//vertices which are given by the desVertexIndex member variable of each element of the E container 
	
	public Vertex() {
		position = new Position();
		E = new ArrayList<Edge>();
	}
	
	/**
     * A method that sets position for this vertex,
     * @param position the position being set for this vertex
     */
	public Vertex(Position position) {
		this.position = new Position(position.getX(), position.getY());
		E = new ArrayList<Edge>();
	}
	
	/**
     * A method that returns position of this vertex,
     * @return the position of this vertex
     */
	public Position getPosition() {
		return position;
	}
	
	/**
     * A method that returns edge set of this vertex,
     * @return the edge set of this vertex
     */
	public ArrayList<Edge> getEdgeSet() {
		return E;
	}
	
	/**
     * A method that returns size of the edge set of this vertex,
     * @return the size of the edge set of this vertex
     */
	public int getEdgeSetSize() {
		return E.size();    // min = 0 and max = 4 for a cell
	}
	
	/**
     * A method that returns an edge whose destination has the same index as the input,
     * @param desVertexIndex the index of the destination vertex being compared to search for the corresponding edge in the edge set
     * @return the corresponding edge which has a destination index similar to the input 
     */
	public Edge getEdge(int desVertexIndex) {
		int index = -1;
		for (int i = 0; i < E.size(); i++)
		{
			if (desVertexIndex == E.get(i).desVertexIndex)
			{
				index = i;
				break;
			}
		}
		assert(index != -1);
		return E.get(index);
	}
	
	/**
     * A method that creates an edge with the input destination-vertex index and adds it into the edge set if it doesn't exist in there,
     * @param desVertexIndex the index of the vertex which is the destination of the edge being added to the set
     */
	public void appendEdge(int desVertexIndex) {
		boolean isFound = false;
		for (int i = 0; i < E.size(); i++)
		{
			if (desVertexIndex == E.get(i).desVertexIndex)
			{
				isFound = true;
				break;
			}
		}
		assert(!isFound);
		Edge temp = new Edge();
		temp.desVertexIndex = desVertexIndex;
		this.E.add(temp);
	}
}
