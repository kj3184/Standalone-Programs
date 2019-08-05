/*
   1. Only write your code in the methods that have the comment 'write your code here' or in the section 'write optional helper methods here'
   2. Do not modify anything else
   3. If your code cannot compile or fails the test cases in 'main()', you will not receive a response from us
*/

import java.util.*;
import java.util.regex.*;

public class TreeTraversal {
	public static void main(String... args) {

		/*
		   Consider the following tree:

		         1
		      /  |  \
		     2   4   6
		   / |  / \  |  \  
		  3  9 5  7  11 12
		      / \      / | \
		     13 16    14 23 17
		       / \
		      24 32


		  Assuming the numbers are the ids of each node, the tree can be written down as follows:

		  1(2,4,6) 2(3,9) 4(5,7) 6(11,12) 5(13,16) 12(14,23,17) 16(24,32)

		  In the example above, for the group 1(2,4,6), node 2, 4 and 6 are the child nodes of
		  node 1. Note that extra whitespaces should be accepted. Assume ids are positive integers only.
		*/

		Tree first = new Tree("1(2,4, 6) 2(3, 9) 4(5,7)  6(11,12 ) 5(13,16)   12(14, 23, 17) 16( 24,32 )");
		assertTrue(first.getLevelOfNodeWithId(1) == 5);
		assertTrue(first.getLevelOfNodeWithId(4) == 4);
		assertTrue(first.getLevelOfNodeWithId(5) == 3);
		assertTrue(first.getLevelOfNodeWithId(12) == 3);
		assertTrue(first.getLevelOfNodeWithId(16) == 2);
		assertTrue(first.getLevelOfNodeWithId(23) == 2);
		assertTrue(first.getLevelOfNodeWithId(32) == 1);

		/*
		      2
		   / | | \ 
		  5  4 3  1
		  |     \
		  7      9
		 / \   /  \
		12 10 11  14
		    |
		    13
		   / | \
		 16  8  15
		*/

		Tree second = new Tree(" 2(5, 4, 3,1)  5(7)   3(9) 7(12, 10)   9(11, 14)  10(13) 13(16,8,15)");
		assertTrue(second.getLevelOfNodeWithId(2) == 6);
		assertTrue(second.getLevelOfNodeWithId(5) == 5);
		assertTrue(second.getLevelOfNodeWithId(3) == 5);
		assertTrue(second.getLevelOfNodeWithId(12) == 3);
		assertTrue(second.getLevelOfNodeWithId(11) == 3);
		assertTrue(second.getLevelOfNodeWithId(13) == 2);
		assertTrue(second.getLevelOfNodeWithId(8) == 1);
	}

	private static void assertTrue(boolean v) {
		if(!v) {
			Thread.dumpStack();
			System.exit(0);
		}
	}
}

class Tree {
	private Node root;

	// do not add new properties

	public Tree(String treeData) {
		// write your code here
		parseTreeData(treeData);
	}

	/**
	  * Finds a node with a given id and return it's level.
	  * The nodes at the bottom of the tree have a level of 1. 
	  *
	  * @param id The id of node
	  * @return The level of the the node of that id, or -1 if a node is not found 
	  */
	public int getLevelOfNodeWithId(int id) {
		// write your code here
		Integer result = -1;     
		int index = 0;
		int heightOfTree = 1;
        
    	LinkedList<Node> queueFIFO = new LinkedList<Node>();
        queueFIFO.add(this.root);
        Map<Object,Object> sameLevelChildrenMap = new HashMap<>();
        sameLevelChildrenMap.put(1,String.valueOf(this.root.getId()));
        
        while (queueFIFO.size() > 0) {
        	
        	Node node = queueFIFO.get(index);
        	List<Node> children = node.getChildren();
       	
        	if(!children.isEmpty()){
        		
        		populatingSamelevelChildrenMap(queueFIFO, sameLevelChildrenMap, node, children);
        		
        	}
        	queueFIFO.remove(index);

       } 
        
        heightOfTree = sameLevelChildrenMap.size();
        int levelForSearchNode = getLevelOfNode(id,sameLevelChildrenMap);
        result = (heightOfTree - levelForSearchNode) + 1;
        return result;    
        
	}

	

	// write optional helper methods here
	
	private void parseTreeData(String treeData) {
		Pattern ptr=Pattern.compile("(.*?)\\)");
		Matcher matcher=ptr.matcher(treeData);
		while(matcher.find()) {
			String [] result=matcher.group().trim().split("[, ?.(\\)\\s]");
			if(this.root==null)
			createNode(this.root,  Integer.parseInt(result[0]),0);
			for(String str:result) {
				if(str.trim().length()>0 && !str.equalsIgnoreCase(result[0])) {
					createNode(root, Integer.parseInt(str), Integer.parseInt(result[0]));
				}
					
			}
			
			
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void createNode(Node node,int nodeValue,int parentNodeValue) {
		if(node==null) {
			root=new Node(nodeValue);
		}
		else {
			if(node.getId()==parentNodeValue)
				node.appendChild(new Node(nodeValue,node));
			else {
				ListIterator<Node> ltr1= (ListIterator<Node>) node.getChildren().listIterator();
				while(ltr1.hasNext())
				{
					Node tmpNode=ltr1.next();
					if(tmpNode.getId()==parentNodeValue) {
						tmpNode.appendChild(new Node(nodeValue,tmpNode));
						break;
						}
					else {
						createNode(tmpNode, nodeValue, parentNodeValue);
					}
				}
			}
		}
    }

	
	private void populatingSamelevelChildrenMap(LinkedList<Node> queueFIFO, Map<Object, Object> SameLevelChildsMap,
			Node node, List<Node> children) {
		int levelOfParent = getLevelOfNode(node.getId(), SameLevelChildsMap);
		int levelOfChild = levelOfParent+1;

		StringBuffer childNodesId = new StringBuffer();
		
		for(Node n:children){
			
			childNodesId.append(n.getId()).append("-");
			queueFIFO.add(n);
			
		}
    		
		if(SameLevelChildsMap.containsKey(levelOfChild)){
			
			String childNodes = (String) SameLevelChildsMap.get(levelOfChild);
			childNodes = childNodes.concat(childNodesId.toString());        			
			SameLevelChildsMap.put(levelOfChild, childNodes);
			
		}else{
			
			SameLevelChildsMap.put(levelOfChild, childNodesId.toString());
			
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getLevelOfNode(int id,Map sameLevelChildrenMap){

		int i=0;
		boolean found = false;
		Set<Integer> keys = sameLevelChildrenMap.keySet();
		for(Integer key:keys){
			String s = (String) sameLevelChildrenMap.get(key);
			String[] childs = s.split("-");
			for(String childId:childs){
				if(Integer.valueOf(childId) == id){
					found = true;
					break;
				}
			}
			i++;
			
			if(found){
				break;
			}
		}
		return i;	
		
	}
	
    }





class Node {
	private Node parent;
	private List<Node> children;
	private int id;

	public Node(int id) {
		this.id = id;
		this.children = new ArrayList<Node>();
	}

	public Node(int id, Node parent) {
		this(id);
		this.parent = parent;
	}

	public void appendChild(Node child) {
		children.add(child);
	}

	public int getId() {
		return id;
	}

	public List<Node> getChildren() {
		return Collections.unmodifiableList(children);
	}
}
