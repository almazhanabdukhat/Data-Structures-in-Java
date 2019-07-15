package almazhanc;

class TreeNode {
    TreeNode left;
    TreeNode right;
    TreeNode parent;
    int value; // this is same as the key in the lectures
    int auxilaryInteger;
    public TreeNode(int v) {
        this.value = v;
        this.auxilaryInteger = 0;
    }
}

public class LessThanK {
    
    //Problem 0 (preprocessing)
	
	//overloaded function called by the preProcessing function
    public static int preProcessing1(TreeNode root) {
    	
        // Base cases
        if (root == null) 
            return 0;

        if (root.left == null && root.right == null)
            return 1;

        // Update left and right subtrees
        int leftsum = preProcessing1(root.left);
        int rightsum = preProcessing1(root.right);
        
        //Add leftsum to current node
        //Auxilary integer stores # of nodes in the left subtree of the current node
        //(i.e. stores # of nodes with values less than the current node's value)
        
        root.auxilaryInteger = leftsum;
        
        // Returns number of the nodes in the tree where the current node is a root
        //i.e. sum of the nodes in the left and right subtrees + root
    
        return leftsum + rightsum + 1;
    }
    
    //we create an additional parent node only for the purpose of preProcessing
    public static void preProcessing(TreeNode root) {
        TreeNode rootParent = new TreeNode(0);
        rootParent.left = root;
        root.parent = rootParent;
        preProcessing1(rootParent);
    }
    
    //Problem 1
    public static int lessThanK(TreeNode root, int k) {
    	
    	//no elements are less than k
        if (root == null) {
            return 0;
            
        //the root and all elements in the left subtree are less than k 
        //method checks the right subtree recursively
        }  else if (root.value < k) {
            return 1 + root.auxilaryInteger + lessThanK(root.right,k) ;
            
        //method ignores elements in the right subtree (greater than the root and root.value > k)
        //method checks the left subtree recursively
        } else {
            return lessThanK(root.left,k);
        }
    }
    static void inorder(TreeNode node) {
        if (node == null)
            return;
        inorder(node.left);
        
        //optional: Value and auxilary integer  of a node are displayed
        System.out.print(node.value + "(" + node.auxilaryInteger + ") ");
        inorder(node.right);
    }
public static void main(String[] args) {
    	
        // Set up a tree 
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(5);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(6);
        root.right = new TreeNode(11);
        root.right.left = new TreeNode(10);
        root.right.left.left = new TreeNode(8);
        root.right.left.left.right = new TreeNode(9); 
        root.right.right = new TreeNode(13);
        root.right.right.left = new TreeNode(12);
        root.right.right.right = new TreeNode(14);
        
        
        preProcessing(root);
        //optional
        inorder(root);
        System.out.println();
        
        //call the functions
        System.out.println ("number of elements less than k:" +lessThanK(root,5));
}
}
