//subbmitors name: tamir davidi, idan yosef
// id: tamir davidi - 315852608, idan yosef - 207522285
// usernames: tamirdavidi1, idanyosef
import AVLTree.AVLNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
 * arguments. Changing these would break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and can add functions of your own
 * according to your needs.
 */


public class AVLTree {
	
	
	
	private AVLNode root;
	private AVLNode VIRTUALSON;
	private AVLNode min; // the node with the minimum key
	private AVLNode max; // the node with the maximum key


    /**
     * This constructor creates an empty AVLTree.
     */
	
    public AVLTree(){ // O(1)
        this.root = null;
        this.VIRTUALSON = new AVLNode(-1, null, null, null, -1, false, false);
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() { // O(1)
        if(this.root == null)
        	return true;
        return false;
    }
    
   
    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     * 
     */
    public Boolean search(int k) {// O(log n)
        AVLNode node = this.root;
		while(node != null && node.isRealNode()) {
			if (node.getKey() == k) {
				return node.getValue();
			}
			else if (k < node.getKey()) {
				node = node.left;
			}
			else if(k > node.getKey()) { 
			     node = node.right;
			}
		}
		return null;
    }
    
    

    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
	 * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
	 * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     */
    
    public boolean is_avarian(AVLNode node) {// O(1) // return true iff node is avarian AVL
    	if (calc_BF(node) > 1 || calc_BF(node) < -1)
			return true;
		return false;
    }
    
    public int calc_BF(AVLNode node) {// O(1) // returns the BF of a node
    	if (node.isReal) {
    		return calc_height(node.getLeft())- calc_height(node.getRight()); 
    	}
    	else //
    		return 0;
    }
    
    public int calc_height(AVLNode node) {// O(1) // returns the height of a node - even null node
    	if (node == null)
    		return -1;
    	return node.getHeight();
    }
    
    public int calc_size(AVLNode node) {// O(1) // returns the size of a node - even null node
    	if (node == null)
    		return 0;
    	return node.getSize();
    }
    
    public int calc_cnttrue(AVLNode node) { // O(1) // returns the size of a node - even null node
    	if (node == null)
    		return 0;
    	return node.getCnttrue();
    }
    
    public int calc_rightcnttrue(AVLNode node) {// O(1)  // returns the size of a node - even null node
    	if (node == null)
    		return 0;
    	return node.rightCnttrue();
    }
    
    public int calc_subcnttrue(AVLNode node) {// O(1)  // returns the size of a node - even null node
    	if (node == null)
    		return 0;
    	return node.getsubCnttrue();
    }
    
    public void size_bf_height(AVLNode node) {// O(1)  // update the size bf and height of node
    	if (node != null) {
    		if (node.getLeft() == null)
	    			node.setLeft(VIRTUALSON);
    		if (node.getRight() == null)
    			node.setRight(VIRTUALSON);
    		node.setHeight(1 + Math.max(calc_height(node.getLeft()), calc_height(node.getRight())));  // height
    		node.setBF(calc_BF(node));															// BF
    		node.setSize(1 + calc_size(node.getLeft()) + calc_size(node.getRight()));				// size
    		if (node.getValue() == true) {
    			node.setsubCnttrue(1 + calc_subcnttrue(node.getLeft()) + calc_subcnttrue(node.getRight())); // dont add self
    			node.setCnttrue(1 + calc_subcnttrue(node.getLeft())); // dont add self
    			node.setrightCnttrue(1 + calc_subcnttrue(node.getRight())); // dont add self
    		}
    		else {// val = false
    			node.setsubCnttrue(calc_subcnttrue(node.getLeft())+calc_subcnttrue(node.getRight())); // dont add self
    			node.setCnttrue(calc_subcnttrue(node.getLeft())); // dont add self
    			node.setrightCnttrue(calc_subcnttrue(node.getRight())); // dont add self
    		}
    	}
    }
    
    public boolean changed_bf_from_zero(AVLNode node, int before_bf, int after_bf) {// O(1)
    	if (node == null)
    		return false;    	
    	if (before_bf == 0 && after_bf != 0)
    		return true;
    	return false;
    	
    }
    
    public int insert(int k, boolean i) { // O(log n)
    	if (search(k) != null) { // if k is in the tree already
    		return -1; // the key is already inside' do nothing
    	}
    	/// we know here the key is not in the tree
    	AVLNode node = new AVLNode(k, i, true); // create the node
    	AVLNode temp; // used in switch
    	node.setLeft(VIRTUALSON);
		node.setRight(VIRTUALSON);
		AVLNode right;
		AVLNode left;
		AVLNode right_left;
		AVLNode left_right;
		String which_son = "";
		if (this.min == null)
    		this.min = node;
    	if (this.max == null)
    		this.max = node;
    	
    	// minimum + maximum maintance
		
    	if (k < this.min.getKey())
    		this.min = node;
    	if (k > this.max.getKey())
    		this.max = node;
    	//
    	
    	if (this.root == null) { // if the first root is null , just add the new node as root
    		this.root = node;
    		node.setBF(0); //  beacause has no children
    		node.setSize(1); // no children
    		node.setHeight(0); // leaf
    		node.setParent(null); 
    		if (node.getValue()) {
    			node.setsubCnttrue(1);
    			node.setCnttrue(1);
    	    	node.setrightCnttrue(1);
    		}
    		else { // val = false
    			node.setsubCnttrue(0);
    			node.setCnttrue(0);
    		    node.setCnttrue(0);
    		    /////
    		}
    		return 1;
    	}
    	AVLNode root = this.root; // pointer to the root
    	AVLNode prev = null;
    	
    	while(root != null) { // we know the key not in the tree
    		if (k < root.getKey()) {
    			prev = root;
    			root = root.getLeft();
    		}
    		else if (k > root.getKey()) {
    			prev = root;
    			root = root.getRight();
    		}
    	}
    	// now "root" is null - where we need to put the new node, prev is the parent of new
    	node.setParent(prev);
    	if (k < prev.getKey()) {
    		prev.setLeft(node);
    	}
    	else if (k > prev.getKey()) {
    		prev.setRight(node);
    	}
    	 
    	boolean rotated = false;
    	int count_height_change = 0;
    	int before_bf_node = node.getBF();
    	int before_bf_left;
    	int before_bf_right;
    	if (node.getLeft() != null)
    		before_bf_left = node.getLeft().getBF();
    	else 
    		before_bf_left = 0;
    	if (node.getRight() != null)
    		before_bf_right = node.getRight().getBF();
    	else 
    		before_bf_right = 0;
    	
    	// rotations if needed
    	while (node != null) {
    		// height + BF + size Maintenance 
    		
    		right = node.getRight();
			left = node.getLeft();
							
    		size_bf_height(right);
			size_bf_height(left);
    		size_bf_height(node);			
			
    		// count height change
    		if (changed_bf_from_zero(node, before_bf_node, node.getBF()))
				count_height_change++;
			if (left != null && changed_bf_from_zero(left, before_bf_left, left.getBF()))
				count_height_change++;
			if (right != null && changed_bf_from_zero(right, before_bf_right, right.getBF()))
				count_height_change++;
    		// update befores
    		before_bf_node = node.getBF();
    		if (node.getLeft() != null)
        		before_bf_left = node.getLeft().getBF();
        	else 
        		before_bf_left = 0;
        	if (node.getRight() != null)
        		before_bf_right = node.getRight().getBF();
        	else 
        		before_bf_right = 0;
    		
    		if (is_avarian(node)) { // if node is avarian AVL
    			// categoraize him
    			right = node.getRight();
    			left = node.getLeft();
    			if (node.getBF() == 2) { // BF is 2
    				if(left.getBF() == 1) { // Left son BF is 1
    					// right rotation
    					left.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(left);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(left);
    						which_son = "";   						
    					}
    					
    					
    					node.setLeft(node.getLeft().getRight());
    					if (node.getLeft() == null)
    						node.setLeft(VIRTUALSON);
    					else if (node.getLeft().getRight() != null)
    						node.getLeft().getRight().setParent(node); ////// added
    					
    					left.setRight(node); // changed also here
    					node.setParent(left);
    					

    					//update root
    					if (node == this.root)
    						this.root = left;   					
    					//maintanance
    					size_bf_height(left);
    					size_bf_height(node);
    					
    					rotated = true;
    				}
    				else if (left.getBF() == -1){ // Left son BF is -1
    					left_right = left.getRight();
    					// left rotation
    					left_right.setParent(node);
    					node.setLeft(left_right); // changed also here
    					   				
    					
    					left.setRight(left.getRight().getLeft());
    					if (left.getRight() == null)
    						left.setRight(VIRTUALSON);
    					else if (node.getRight().getLeft() != null)
    						node.getRight().getLeft().setParent(node); ////// added
    					   					
    					left_right.setLeft(left);
    					left.setParent(left_right);
    					
    					// right rotation
    					temp = left; // for nohoot, rename updated pointers
    					left = left_right;
    					left_right = temp;
    						// now start the right rotation
    					left.setParent(node.getParent());
    					if (node.getParent() != null) {   						
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(left);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(left);
    						which_son = "";    						
    					}
    					
    					node.setLeft(left.getRight()); 
    					
    					
    					
    					left.setRight(node); // changed also here
    					node.setParent(left); 
    					
    					//update root
    					if (node == this.root)
    						this.root = left;
    					//maintanance
    					size_bf_height(left_right);
    					size_bf_height(left);
    					size_bf_height(node);
    					rotated = true;
    				}
    				
    			}
    			else if (node.getBF() == -2) { // BF is -2
    				if(right.getBF() == 1) { // right son BF is 1
    					right_left = right.getLeft();
    					// right rotation
    					right_left.setParent(node);
    					node.setRight(right_left);
    					   					
    					right.setLeft(right_left.getRight());
    					if (right.getLeft() == null)
    						right.setLeft(VIRTUALSON);
    					
    					right_left.setRight(right); // changed also here
    					right.setParent(right_left);
    					
    					// left rotation
    					 // update pointers name for nohoot
    					temp = right;
    					right = right_left;
    					right_left = temp;
    					
    					right.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(right);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(right);
    						which_son = "";
    					}
    					
    				
    					node.setRight(right.getLeft());
    					
    					right.setLeft(node);
    					node.setParent(right);
  					
    				
    					//update root
    					if (node == this.root)
    						this.root = right;
    					//maintanance
    					size_bf_height(right_left);
    					size_bf_height(right);    					
    					size_bf_height(node);
    					rotated = true;
    				}
    				else if (right.getBF() == -1){ // right son BF is -1
    					// left rotation
    					right.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(right);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(right);
    						which_son = "";
    					}
    					
    					right_left = node.getRight().getLeft();
    					node.setRight(right_left);
    					if (node.getRight() == null)
    						node.setRight(VIRTUALSON);
    					else if (right_left != null)
    						right_left.setParent(node); ////// added
  					
    					right.setLeft(node);
    					node.setParent(right);
    					
    					//update root
    					if (node == this.root)
    						this.root = right;
    					//maintanance
    					size_bf_height(right);
    					size_bf_height(node);
    					rotated = true;
    				}
    			}
    			//node.setBF(0); // after we fixed it BF is 0
    			// now we fixed the balance of the tree
    			
    		}
      		
			node = node.getParent(); // go up until need to fix - actually till the root
    	}
    	
    	
    	if (rotated)
    		return 1 + count_height_change;    // in insert only one change 
    	// not rotated
    	return count_height_change;
    		
    }

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) { //log(n)
    	if (search(k) == null) {
    		return -1;
    	}
    	List<AVLNode> nodes = new ArrayList<>();
    	List<Integer> h = new ArrayList<>();
    	List<Boolean> b = new ArrayList<>();
    	AVLNode node = this.root;
    	AVLNode temp;
		AVLNode right;
		AVLNode left;
		AVLNode right_left;
		AVLNode left_right;
		String which_son = "";
		while(node.getKey() != k) {
			if (k < node.getKey()) {
				node = node.left;
			}
			if(k > node.getKey()) { 
			     node = node.right;
			}
		}
		boolean r = false;
		AVLNode node1 = node;
		while (node1 != null) {
			nodes.add(node1);
			h.add(node1.getHeight());
			node1 = node1.getParent();
		}
		
	    if (this.min.getKey() == node.getKey()) {
	    	this.min = node.getParent();
	    }
	    if (this.max.getKey() == node.getKey()) {
	    	this.max = node.getParent();
	    }
	    
		if(node.isLeaf()) {
			if(node.getParent().right == node) {
				node.getParent().setRight(VIRTUALSON);
			}
			else if(node.getParent().left == node) {
				node.getParent().setLeft(VIRTUALSON);
			}
			node.setParent(null);
			node = node.getParent();
		}
		else if (!node.left.isRealNode()) {
			 if(node.getParent() != null) {
				 if (node.getParent().right == node) {
					 node.getParent().right = node.right; 
				 }
				 else {
					 node.getParent().left = node.left; 
				 }
			 }
			 else {
				 this.root = node.right;
			 }
			 node.right.setParent(node.getParent());
			 node = node.getParent();
		}
		else if (!node.right.isRealNode()){
			if(node.getParent() != null) {
				 if (node.getParent().left == node) {
					 node.getParent().left = node.left; 
				 }
				 else {
					 node.getParent().right = node.right; 
				 }
			}
			else {
				 this.root = node.left;
		    }
			node.left.setParent(node.getParent());
			node = node.getParent();
	    }
		else {
		
			AVLNode succ = successor(node);
			if(succ.getParent() != null) {
				 if (succ.getParent().right == succ) {
					 succ.getParent().right = succ.right; 
				 }
				 else {
					 succ.getParent().left = succ.left; 
				 }
			 }
			 else {
				 this.root = succ.right;
			 }
			 r = true;
			 succ.right.setParent(succ.getParent());
			 node.key = succ.key;
			 node.info = succ.info;
			
		}
		
		boolean rotated = false;
		int count = 0;	
		int i = 0;
    	// rotations if needed
		while (node != null) {
			
			rotated = false;
    		// height + BF + size Maintenance 
			size_bf_height(node);
			right = node.getRight();
			left = node.getLeft();
			size_bf_height(right);
			size_bf_height(left);
			
			
    		if (is_avarian(node)) { // if node is avarian AVL
    			// categoraize him
    			right = node.getRight();
    			left = node.getLeft();
    			if (node.getBF() == 2) { // BF is 2
    				if(left.getBF() == 0 || left.getBF() == 1) { // Left son BF is 1
    					// right rotation
    					
    					left.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(left);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(left);
    						which_son = "";   						
    					}
    					
    					
    					node.setLeft(node.getLeft().getRight());
    					if (node.getLeft() == null)
    						node.setLeft(VIRTUALSON);
    					else if (node.getLeft().getRight() != null)
    						node.getLeft().getRight().setParent(node); ////// added
    					
    					left.setRight(node); // changed also here
    					node.setParent(left);
    					
                        
    					//update root
    					if (node == this.root)
    						this.root = left;   					
    					//maintanance
    					size_bf_height(node);
    					size_bf_height(left);
    					rotated = true;
    				}
    				else if (left.getBF() == -1){ // Left son BF is -1
    		
    					left_right = left.getRight();
    					// left rotation
    					left_right.setParent(node);
    					node.setLeft(left_right); // changed also here
    					   				
    					
    					left.setRight(left.getRight().getLeft());
    					if (left.getRight() == null)
    						left.setRight(VIRTUALSON);
    					else if (node.getRight().getLeft() != null)
    						node.getRight().getLeft().setParent(node); ////// added
    					   					
    					left_right.setLeft(left);
    					left.setParent(left_right);
    					
    					// right rotation
    					temp = left; // for nohoot, rename updated pointers
    					left = left_right;
    					left_right = temp;
    						// now start the right rotation
    					left.setParent(node.getParent());
    					if (node.getParent() != null) {   						
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(left);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(left);
    						which_son = "";    						
    					}
    					
    					node.setLeft(left.getRight());   				
    					
    					left.setRight(node); // changed also here
    					node.setParent(left); 
    					
    					//update root
    					if (node == this.root)
    						this.root = left;
    					//maintanance
    					size_bf_height(node);
    					size_bf_height(left);
    					size_bf_height(left_right);
    					rotated = true;
    				}
    				
    			}
    			else if (node.getBF() == -2) { // BF is -2
    				if(right.getBF() == 1) { // right son BF is 1
    			        
    					right_left = right.getLeft();
    					// right rotation
    					right_left.setParent(node);
    					node.setRight(right_left);
    					   					
    					right.setLeft(right.getLeft().getRight());
    					if (right.getLeft() == null)
    						right.setLeft(VIRTUALSON);
    					
    					right_left.setRight(right); // changed also here
    					right.setParent(right_left);
    					
    					// left rotation
    					 // update pointers name for nohoot
    					temp = right;
    					right = right_left;
    					right_left = temp;
    					
    					right.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(right);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(right);
    						which_son = "";
    					}
    					
    					right_left = node.getRight().getLeft();
    					node.setRight(right_left);
    					if (node.getRight() == null)
    						node.setRight(VIRTUALSON);
    					else if (right_left != null)
    						right_left.setParent(node); ////// added
  					
    					right.setLeft(node);
    					node.setParent(right);
  					
    				
    					//update root
    					if (node == this.root)
    						this.root = right_left;
    					//maintanance
    					size_bf_height(node);
    					size_bf_height(right);
    					size_bf_height(right_left);
    					rotated = true;
    				}
    				else if (right.getBF() == -1 || right.getBF() == 0){ // right son BF is -1
    					// left rotation
    					
    					right.setParent(node.getParent());
    					if (node.getParent() != null) {
    						if (node.getParent().getLeft() == node)
    							which_son = "left";
    						else if (node.getParent().getRight() == node)
    							which_son = "right";
    						
    						if (which_son.equals("left"))
    							node.getParent().setLeft(right);
    						else if(which_son.equals("right"))
    							node.getParent().setRight(right);
    						which_son = "";
    					}
    					
    					right_left = node.getRight().getLeft();
    					node.setRight(right_left);
    					if (node.getRight() == null)
    						node.setRight(VIRTUALSON);
    					else if (right_left != null)
    						right_left.setParent(node); ////// added
  					
    					right.setLeft(node);
    					node.setParent(right);
    					
    					//update root
    					if (node == this.root)
    						this.root = right;
    					//maintanance
    					size_bf_height(node);
    					size_bf_height(right);
    					rotated = true;
    				}
    			}
    			//node.setBF(0); // after we fixed it BF is 0
    			// now we fixed the balance of the tree
    			
    		}
    		
    		
    		
    		
    		if(nodes.get(i).getKey() == node.getKey()) {
    		
    		  	
    	    	if(rotated) {
    	    		b.add(true);
    	    	}
    	    	else {
    	    		b.add(false);
    	    	}
    	    	i++;
    		}
    		
			node = node.getParent(); // go up until need to fix - actually till the root
    	}
		int j=0;
		if(r) {
			j++;
		}
		if(b.size()>0) {
			while(j<nodes.size()) {
				if((nodes.get(j).getHeight() != h.get(j)) || b.get(j)) {
					
					count++;
				}
				j++;
			}
		}
		else {
			while(j<nodes.size()) {
				if((nodes.get(j).getHeight() != h.get(j) )) {
					count++;
				}
				j++;
			}
		}
		return count;    
    }
    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
    public Boolean min() { // O(1)
    	if (empty()) // if the tree is empty - min = null
    		return null;
    	// if tree != empty
    	return this.min.getValue();

    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     * 
     */
    public Boolean max() { // O(1)
    	if (empty()) 
   		   return null;
    	
        return this.max.getValue();
    }
    
    public int keys_in_order(int[] arr, AVLNode root, int i) { //O(n)
    	if (root != null && root.isRealNode()) { // on a real node that exists
    		i = keys_in_order(arr, root.getLeft(), i); // go leftest 
    		arr[i++] = root.getKey(); // each insert make the index up by one, and save it that it will follow up the recursion
    		i = keys_in_order(arr, root.getRight(), i);  
    		return i;
    	}
    	return i;
    }
    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() { //O(n)
    	int[] arr = new int[this.root.getSize()]; // array length is num of nodes - size of root
    	keys_in_order(arr, this.root, 0); // ignore the num returned
        return arr;              
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
     public boolean[] infoToArray() { //O(n)
    	if  (empty()) {
        	return null;
        }
    	boolean[] arr = new boolean[this.root.getSize()]; // array length is num of nodes - size of root
    	info_in_order(arr, this.root, 0); // ignore the num returned
        return arr;
        	
     }
     
     public int info_in_order(boolean[] arr, AVLNode root, int i ) { //O(n)
     	if (root != null && root.isRealNode()) {
     		i = info_in_order(arr, root.getLeft(), i); 
     		arr[i++] = root.getValue(); 
     		i = info_in_order(arr, root.getRight(), i);  
     		return i;
     	}
     	return i;

     }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() { //O(1)
        return this.root.getSize(); 
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public AVLNode getRoot() { //O(1)
        return this.root;
    }

    /**
     * public boolean prefixXor(int k)
     *
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     *
     * precondition: this.search(k) != null
     *
     */
    public boolean prefixXor(int k){ //O(log n)
    	AVLNode node = this.root;
    	int s = 0;
		while(node != null && node.isRealNode()) {
			if (node.getKey() == k) {
			  if(node.getValue() == true) {
				  s += node.getCnttrue();
			  }
			  else {
				  s += node.getCnttrue();
			  }
			  break;
			  
			}
			else if (node.getKey() < k) {
				node = node.getRight();
			}
			else if(node.getKey() > k) { 
			     node = node.getLeft();
			}
		}
		node = node.getParent();
		while(node != null) {
			if(k >= node.getKey()) {
				s += node.getCnttrue();
				
			}
			node = node.getParent();
		}
		 
		if(s % 2 == 1) {
			return true;
		}
		else {
			return false;
		}
    }

    /**
     * public AVLNode successor
     *
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     */
    public AVLNode successor(AVLNode node){ // O(log n)
        if (node.getRight() != null && node.getRight() != VIRTUALSON) { // node has right son
        	node = node.getRight();
        	while (node.getLeft() != null && node.getLeft() != VIRTUALSON) { // go to the leftest leaf - min of right son sub tree
        		node = node.getLeft();
        	}
        	return node;
        }
        else { // has no right son
        	while (node.getParent() != null) { // go up until first time this is up-right parent
        		if (node.getParent().getLeft() == node)
        			return node.getParent();
        		node = node.getParent(); // go up
        	}
        }
    	return null; // if has no right son or up right parent so this is the max - no succesor
    }

    /**
     * public boolean succPrefixXor(int k)
     *
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     *
     * precondition: this.search(k) != null
     */
    public boolean succPrefixXor(int k){ // O(log n)
    	int true_count = 0;
    	AVLNode node = this.min;

    	while (node.getKey() <= k) {	
    		if (node.getValue())
        		true_count++;
    		node = successor(node);
    		if (node == null) // too far
    			break;
    	}
    	if (true_count % 2 == 1)
    		return true;
    	else
    		return false;
    	
    }
    

    
    /**
     * public class AVLNode
     * <p>
     * This class represents a node in the AVL tree.
     * <p>
     * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
     * arguments. Changing these would break the automatic tester, and would result in worse grade.
     * <p>
     * However, you are allowed (and required) to implement the given functions, and can add functions of your own
     * according to your needs.
     */
    public class AVLNode { // from here down, all is O(1)
    	
    	// defining the fields:
    	private boolean info;
    	private int key;
    	private AVLNode left;
    	private AVLNode right;
    	private AVLNode parent;
    	private int height;
    	private int BF;
    	private int cnttrue;
    	private int rightcnttrue;
    	private int subcnttrue;
    	private int size; // num of nodes in this subtree colel root
    	private boolean isReal; // virtual = false
    	
    	public AVLNode(int key, AVLNode parent, AVLNode left, AVLNode right, int height, boolean info, boolean isReal) {// O(1) 
    		this.key = key;
    		this.parent = parent;
    		this.left = left;
    		this.right = right;
    		this.height = 0; 
    		this.info = info;
    		this.isReal = isReal;
    	}
    	
    	public AVLNode(int key, boolean info, boolean isReal) {// O(1) 
    		this.key = key;
    		this.info = info;
    		this.isReal = isReal;
    	}
    	
       
        
        //returns node's key (for virtual node return -1)
        public int getKey() {// O(1) 
            if (this.isReal) {
            	return this.key;
            }
            return -1;
        }

        //returns node's value [info] (for virtual node return null)
        public boolean getValue() {// O(1) 
            if (this.isReal) {
            	return this.info;
            }
            return (Boolean) null;
        }

        //sets left child
        public void setLeft(AVLNode node) { // O(1) 
            this.left = node;
        }

        //returns left child (if there is no left child return null)
        public AVLNode getLeft() {// O(1) 
            if (this.isReal)
            {
            	if (this.left != null && this.left.isReal)
            		return this.left;
            	return null;
            }
            return null;
        }

        //sets right child
        public void setRight(AVLNode node) {// O(1) 
            this.right = node;
        }
        
        public void setSize(int size) { // O(1) // sets the size
        	this.size = size;
        }
        
        public void size_up() { // O(1) // adds 1 to the size of the node
        	this.size = this.size + 1;
        }
        
        public void size_down() {// O(1)  // makes the size of the node smaller by 1
        	this.size = this.size - 1;
        }
        
        public int getSize() { // O(1) // returns the size 
        	return this.size;
        }
        
        public void setBF(int BF) {// O(1) // sets the balance factor of the node
        	this.BF = BF;
        }
        
        public int getBF() { // O(1) // returns the balance factor of the node
        	return this.BF;
        }
        
        public int getCnttrue() {// O(1) 
			return this.cnttrue;
		}

		public void setCnttrue(int cnttrue) {// O(1) 
			this.cnttrue = cnttrue;
		}
		
		public void cnttrue_up() {// O(1) 
			this.cnttrue = this.cnttrue + 1;
		}
		
		public int rightCnttrue() {// O(1) 
			return this.rightcnttrue;
		}
		
		public void setrightCnttrue(int rightcnttrue) {// O(1) 
		     this.rightcnttrue = rightcnttrue;
		}
		
		public int getsubCnttrue() {// O(1) 
			return this.subcnttrue;
		}
		
		public void setsubCnttrue(int subcnttrue) {// O(1) 
		     this.subcnttrue = subcnttrue;
		}
        
        //returns right child (if there is no right child return null)
        public AVLNode getRight() {// O(1) 
        	if (this.isReal)
            {
            	if (this.right != null && this.right.isReal)
            		return this.right;
            	return null;
            }
            return null;
        }

        //sets parent
        public void setParent(AVLNode node) {// O(1) 
            this.parent = node;
        }

        //returns the parent (if there is no parent return null)
        public AVLNode getParent() {// O(1) 
        	return this.parent;
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() {// O(1) 
            return this.isReal;
        }

        // sets the height of the node
        public void setHeight(int height) {// O(1) 
            this.height = height;
        }

        // Returns the height of the node (-1 for virtual nodes)
        public int getHeight() {// O(1) 
            if(this.isReal)
            	return this.height;
            return -1;
        }
        
        public boolean isLeaf() { // O(1) // checks if the node is leaf in a tree
        	if (this.left == null && this.right == null)
        		return true;
        	return false;
        }
        
        public String getText(){// O(1) 
            if (this.info != true && this.info != false) return "null";
            return (this.info) ? "true : "+Integer.toString(this.key) : "false : "+Integer.toString(this.key);}
        
    }

}



