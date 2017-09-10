import java.io.*;
import java.util.*;

public class Through_the_Forest_of_Fangorn {    
	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputString = null;
		try {
			inputString = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data = inputString.split(" ");
		
		int numOfNodes = Integer.parseInt(data[0]) + 1;
        int numOfInput = Integer.parseInt(data[1]);
        
        StageNode[] arrayOfNodes = new StageNode[numOfNodes];        
        
        for (int i = 0; i < numOfInput; i++) {
        	try {
				inputString = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		data = inputString.split(" ");
			int predecessor = Integer.parseInt(data[0]);
			int successor = Integer.parseInt(data[1]);
			int weight = Integer.parseInt(data[2]);
						
			StageNode successorNode = new StageNode(successor, weight);
			successorNode.successor = arrayOfNodes[predecessor];
			arrayOfNodes[predecessor] = successorNode;
		}
        
        System.out.println(RedBlue(arrayOfNodes, 1, numOfNodes - 1));
	}
	
	static int RedBlue(StageNode[] arrayOfNodes, int source, int sink) {
		StageNode[] graphDouble = new StageNode[arrayOfNodes.length * 2 - 1];
		for (int i = 1; i < arrayOfNodes.length; i++) {
			StageNode current = arrayOfNodes[i];
			while (current != null) {
				int newWeight = 1;
				StageNode addEdge;
				if (current.road == 1) {
					newWeight = 0;
					addEdge = new StageNode(current.value + arrayOfNodes.length - 1, newWeight);
					addEdge.successor = graphDouble[i];
					graphDouble[i] = addEdge;
				}
				addEdge = new StageNode(current.value, newWeight);
				addEdge.successor = graphDouble[i];
				graphDouble[i] = addEdge;
				addEdge = new StageNode(current.value + arrayOfNodes.length - 1, newWeight);
				addEdge.successor = graphDouble[i + arrayOfNodes.length - 1];
				graphDouble[i + arrayOfNodes.length - 1] = addEdge;
				current = current.successor;
			}
		}
		int[] distance = Dijkstra(graphDouble, source);
		return distance[sink + arrayOfNodes.length - 1];
	}
	
	static int[] Dijkstra(StageNode[] graphDouble, int source) {
		int[] weight = new int[graphDouble.length];
        for (int i = 0; i < weight.length; i++) {
        	weight[i] = -1;
        }
        Comparator<NodeWithDistance> comparator = new DistanceComparator();
		PriorityQueue<NodeWithDistance> q = new PriorityQueue<>(graphDouble.length, comparator);
		weight[source] = 0;
		NodeWithDistance sourceNode = new NodeWithDistance(source, weight[source]);
		q.add(sourceNode);
		
		while (!q.isEmpty()) {
			int u = q.remove().value;
			StageNode current = graphDouble[u];
        	while (current != null) {
        		if (weight[current.value] == -1) {
            		weight[current.value] = weight[u] + current.road;
        			NodeWithDistance newNode = new NodeWithDistance(current.value, weight[current.value]);
        			q.add(newNode);
        		}
        		else if (weight[u] + current.road < weight[current.value]) {
        			weight[current.value] = weight[u] + current.road;
        		}
        		current = current.successor;
        	}
		}
		
		return weight;
	}
}

class StageNode {
	int value;
	int road;
	StageNode successor;
	
	StageNode(int value, int road) {
		this.value = value;
		this.road = road;
	}
}

class NodeWithDistance {
	int value;
	int distance;
	
	NodeWithDistance(int value, int distance) {
		this.value = value;
		this.distance = distance;
	}
}

class DistanceComparator implements Comparator<NodeWithDistance> {
	@Override
	public int compare(NodeWithDistance o1, NodeWithDistance o2) {
		// TODO Auto-generated method stub
		return o1.distance - o2.distance;
	}
	
}