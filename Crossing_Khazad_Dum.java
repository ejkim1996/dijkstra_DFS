import java.io.*;
import java.util.*;

public class Crossing_Khazad_Dum {
	static ArrayList<Integer> topSort = new ArrayList<>();
    static int predOfFirstNode;
    static int[] predecessor;
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
        int[] color = new int[numOfNodes];
        predecessor = new int[numOfNodes];
        
        for (int i = 0; i < color.length; i++) {
        	color[i] = 0;
        }
        
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
						
			StageNode successorNode = new StageNode(successor);
			successorNode.successor = arrayOfNodes[predecessor];
			arrayOfNodes[predecessor] = successorNode;
		}
		
		int loop = DFS(arrayOfNodes, color);
		
		if (loop == 1) {
			System.out.println(1);
//			String print = "";
//			for (int i = 0; i < topSort.size(); i ++) {
//				print += " " + topSort.get(i);
//			}
//			System.out.println(print.substring(1));
			String print = " " + predOfFirstNode;
			int current = predecessor[predOfFirstNode];
			while (current != predOfFirstNode) {
				print = " " + current + print;
				current = predecessor[current];
			}
			System.out.println(print.substring(1));
		}
		else {
			System.out.println(0);
		}
		
//		input.close();
	}
	
	static int DFS(StageNode[] arrayOfNodes, int[] color) {
		for (int i = 1; i < arrayOfNodes.length; i++) {
			if (color[i] == 0) {
				return RecDFS(i, arrayOfNodes, color);
			}
		}
		return 0;
	}
	
	static int RecDFS(int nodeValue, StageNode[] arrayOfNodes, int color[]) {
		color[nodeValue] = 1;
		StageNode current = arrayOfNodes[nodeValue];
		while (current != null) {
			if (color[current.value] == 0) {
				predecessor[current.value] = nodeValue;
				topSort.add(nodeValue);
				int currentValue = current.value;
				current = current.successor;
				return RecDFS(currentValue, arrayOfNodes, color);
				
			}
			else {
				topSort.add(nodeValue);
				predecessor[current.value] = nodeValue;
				predOfFirstNode = nodeValue;
				return 1;
			}
		}
		return 0;
	}

}

class StageNode {
	int value;
	StageNode successor;
	
	StageNode(int value) {
		this.value = value;
	}
}