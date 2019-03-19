package tga;
import java.util.*;

public class BinarySearchTree<K extends Comparable<K>,  V> implements BinarySearchTreeADT<K, V>  {

	protected Node root;

    protected class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node next(K other) {
            return other.compareTo(key) < 0 ? getLeft() : getRight();
        }

        public boolean isLeaf() {
            return getLeft() == null && getRight() == null;
        }
        @Override
        public String toString() { return "" + key; }

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}
    }

	
	@Override
	public void clear() {
		root = null;		
	}

	@Override
	public boolean isEmpty() {
		 return root == null;
	}

	@Override
	public V search(K key) {
	    return search(root, key);
	}

	private V search(Node node, K key) {
	    if (node == null) {
	        return null;
	    } else if (key.compareTo(node.key) == 0) {
	        return node.value;
	    }
	    return search(node.next(key), key);
	}


	@Override
	public void insert(K key, V value) {
	    root = insert(root, key, value);
	}

	private Node insert(Node node, K key, V value) {
	    if (node == null) {
	        return new Node(key, value);
	    } else if (key.compareTo(node.key) > 0) {
	        node.setRight(insert(node.getRight(), key, value));
	    } else if (key.compareTo(node.key) < 0) {
	        node.setLeft(insert(node.getLeft(), key, value));
	    }

	    return node;
	}


	@Override
	public boolean delete(K key) {
	   return deleteByCopying(key);
	}

	private boolean deleteByCopying(K key) {
	    Node parent = null, current = root;
	    for (; current != null && key.compareTo(current.key) != 0; parent = current, current = current.next(key));
	    
	    if (current == null) 
	        return false;
	    else if (current.getLeft() != null && current.getRight() != null) {
	        // Caso 3
	        Node tmp = current.getLeft();     
	        while (tmp.getRight() != null) tmp = tmp.getRight();
	        deleteByCopying(tmp.key); 
	        current.key = tmp.key; 
	    } else {
	        // Caso 1 ou Caso 2
	        Node nextNode = current.getRight() == null ? current.getLeft() : current.getRight();
	        if (current.equals(root)) root = nextNode;
	        else if (parent.getLeft().equals(current)) parent.setLeft(nextNode);
	        else parent.setRight(nextNode);
	    }

	    return true;
	}

	
	@Override
	public void preOrder() {
		   preOrder(root);
		}

		private void preOrder(Node node) {
		   if (node != null) {
		       System.out.print(node + " ");
		       preOrder(node.getLeft());
		       preOrder(node.getRight());
		   }
		}


	@Override
	public void inOrder() {
	    inOrder(root);
	}

	private void inOrder(Node node) {
	   if (node != null) {
	      inOrder(node.getLeft());
	      System.out.print(node + " ");
	      inOrder(node.getRight());
	   }
	}


	@Override
	public void postOrder() {
	   postOrder(root);
	}

	private void postOrder(Node node) {
	   if (node != null) {
	     postOrder(node.getLeft());
	     postOrder(node.getRight());
	     System.out.print(node + " ");
	   }
	}

	@Override
	public void levelOrder() {
		// TODO Auto-generated method stub
		
	}
	
    // Método semelhante ao search, porém retorna o nó
    public BinarySearchTree<K, V>.Node nodeSearch(K key) {
        return nodeSearch(root, key);
    }
    private BinarySearchTree<K, V>.Node nodeSearch(Node node, K key) {
    	if (node == null) {
	        return null;
	    } else if (key.compareTo(node.key) == 0) {
	        return node;
	    }
	    return nodeSearch(node.next(key), key);
    }
	
	
	public String ListAncestors(K key) {
		return ListAncestors(root, key, "");
	}
	
	private String ListAncestors(Node node, K key, String concat) {
		// condições de saída
		// caso não exista o nó (não encontrada a chave)
		if(node == null)
			return null;
		// se a chave passada é da raiz, não tem ancestrais
		if(root.key == key)
			return "Esta chave não possui ancestrais";
		// concatena o valor do ancestral
		concat = concat + " " + node.toString();
		// verifica a direção
		int dir = node.key.compareTo(key);
		if(dir < 0)
			//verifica se não é o final
			if(key.compareTo(node.getRight().key) == 0)
				return concat;
		if(dir > 0)
			//verifica se não é o final
			if(key.compareTo(node.getLeft().key) == 0)
				return concat;
			
		return ListAncestors(node.next(key), key, concat);
	}
	
	
	@Override
	public String toString() {
	    return root == null ? "[empty]" : printTree(new StringBuffer());
	}

	private String printTree(StringBuffer sb) {
	    if (root.getRight() != null) {
	        printTree(root.getRight(), true, sb, "");
	    }
	    sb.append(root + "\n"); 
	    if (root.getLeft() != null) {
	        printTree(root.getLeft(), false, sb, "");
	    }
	        
	    return sb.toString();
	}

	private void printTree(Node node, boolean isRight, StringBuffer sb, String indent) {
	    if (node.getRight() != null) {
	        printTree(node.getRight(), true, sb, indent + (isRight ? "        " : " |      "));
	    }
	    sb.append(indent + (isRight ? " /" : " \\") + "----- " + node + "\n"); 
	    if (node.getLeft() != null) {
	        printTree(node.getLeft(), false, sb, indent + (isRight ? " |      " : "        "));
	    }
	}


}
