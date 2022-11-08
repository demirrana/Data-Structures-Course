public class LinkedDNATree<E> extends AbstractDNATree<E> {

    protected static class Node<E> implements Position<E> {

        private E element;
        private Node<E> parent;
        private Node<E> childOne, childTwo, childThree, childFour, childFive;

        public Node(E e, Node<E> parentNode, Node<E> child1, Node<E> child2, Node<E> child3, Node<E> child4, Node<E> child5) {

            element = e;
            parent = parentNode;
            childOne = child1;
            childTwo = child2;
            childThree = child3;
            childFour = child4;
            childFive = child5;
        }

        public E getElement() { return element; }

        public Node<E> getParent() { return parent; }

        public Node<E> getChildOne() { return childOne; }
        public Node<E> getChildTwo() { return childTwo; }
        public Node<E> getChildThree() { return childThree; }
        public Node<E> getChildFour() { return childFour; }
        public Node<E> getChildFive() { return childFive; }

        public void setElement(E e) { element = e; }

        public void setParent(Node<E> node) { parent = node; }

        public void setChildOne(Node<E> node) { childOne = node; }
        public void setChildTwo(Node<E> node) { childTwo = node; }
        public void setChildThree(Node<E> node) { childThree = node; }
        public void setChildFour(Node<E> node) { childFour = node; }
        public void setChildFive(Node<E> node) { childFive = node; }
    }

    protected Node<E> createNode(E e, Node<E> parentNode, Node<E> child1, Node<E> child2, 
                                Node<E> child3, Node<E> child4, Node<E> child5) {

        return new Node<E>(e, parentNode, child1, child2, child3, child4, child5);
    }

    protected Node<E> root = createNode(null, null, null, null, null, null, null);

    private int size = 1; //basta da empty leaf oldugu icin

    public LinkedDNATree() {}

    protected Node<E> validate(Position<E> pos) {

        if (!(pos instanceof Node))
            throw new IllegalArgumentException("Not valid position");

        Node<E> node = (Node<E>)pos;

        if (node == node.getParent())
            throw new IllegalArgumentException("p is no longer in the tree");

        return node;
    }

    public int size() { return size; }

    public Position<E> root() { return root; }

    public Position<E> parent(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getParent();
    }

    public Position<E> childOne(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getChildOne();
    }

    public Position<E> childTwo(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getChildTwo();
    }

    public Position<E> childThree(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getChildThree();
    }

    public Position<E> childFour(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getChildFour();
    }

    public Position<E> childFive(Position<E> pos) throws IllegalArgumentException {

        Node<E> node = validate(pos);
        return node.getChildFive();
    }
    
    public Position<E> addRoot(E e) throws IllegalStateException {

        if (!isEmpty()) 
            throw new IllegalStateException("Tree is not empty");

        root = createNode(e, null, null, null, null, null, null);
        size = 1;

        return root;
    }
    //herhangi bir child ekledigim zaman diger 4 child'i da (yoksa) icinde null sekilde create ediyorum
    public Position<E> addChildOne(Position<E> parentPos , E element) throws IllegalStateException {

        if (parentPos == null)
            throw new IllegalStateException("There is no such position");

        Node<E> parentNode = validate(parentPos);
        Node<E> childOne = createNode(element, parentNode, null, null, null, null, null);

        if (parentNode.getChildOne() == null)
            size++;
        parentNode.setChildOne(childOne);

        if (parentNode.getChildTwo() == null) {
            parentNode.setChildTwo(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildThree() == null) {
            parentNode.setChildThree(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildFour() == null) {
            parentNode.setChildFour(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildFive() == null) {
            parentNode.setChildFive(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }

        return childOne;
    }

    public Position<E> addChildTwo(Position<E> parentPos , E element) throws IllegalStateException {

        if (parentPos == null)
            throw new IllegalStateException("There is no such position");

        Node<E> parentNode = validate(parentPos);
        Node<E> childTwo = createNode(element, parentNode, null, null, null, null, null);

        if (parentNode.getChildOne() == null) {
            parentNode.setChildOne(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }

        if (parentNode.getChildTwo() == null)
            size++;
        parentNode.setChildTwo(childTwo);

        if (parentNode.getChildThree() == null) {
            parentNode.setChildThree(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }    
        if (parentNode.getChildFour() == null) {
            parentNode.setChildFour(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }    
        if (parentNode.getChildFive() == null) {
            parentNode.setChildFive(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        
        return childTwo;
    }

    public Position<E> addChildThree(Position<E> parentPos , E element) throws IllegalStateException {

        if (parentPos == null)
            throw new IllegalStateException("There is no such position");

        Node<E> parentNode = validate(parentPos);
        Node<E> childThree = createNode(element, parentNode, null, null, null, null, null);

        if (parentNode.getChildOne() == null) {
            parentNode.setChildOne(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildTwo() == null) {
            parentNode.setChildTwo(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }

        if (parentNode.getChildThree() == null)
            size++;
        parentNode.setChildThree(childThree);

        if (parentNode.getChildFour() == null) {
            parentNode.setChildFour(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildFive() == null) {
            parentNode.setChildFive(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        
        return childThree;
    }

    public Position<E> addChildFour(Position<E> parentPos , E element) throws IllegalStateException {

        if (parentPos == null)
            throw new IllegalStateException("There is no such position");

        Node<E> parentNode = validate(parentPos);
        Node<E> childFour = createNode(element, parentNode, null, null, null, null, null);

        if (parentNode.getChildOne() == null) {
            parentNode.setChildOne(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildTwo() == null) {
            parentNode.setChildTwo(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildThree() == null) {
            parentNode.setChildThree(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }

        if (parentNode.getChildFour() == null)
            size++;
        parentNode.setChildFour(childFour);
        
        if (parentNode.getChildFive() == null) {
            parentNode.setChildFive(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
    
        return childFour;
    }

    public Position<E> addChildFive(Position<E> parentPos , E element) throws IllegalStateException {

        if (parentPos == null)
            throw new IllegalStateException("There is no such position");

        Node<E> parentNode = validate(parentPos);
        Node<E> childFive = createNode(element, parentNode, null, null, null, null, null);

        if (parentNode.getChildOne() == null) {
            parentNode.setChildOne(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildTwo() == null) {
            parentNode.setChildTwo(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildThree() == null) {
            parentNode.setChildThree(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }
        if (parentNode.getChildFour() == null) {
            parentNode.setChildFour(createNode(null, parentNode, null, null, null, null, null));
            size++;
        }

        if (parentNode.getChildFive() == null)
            size++;
        parentNode.setChildFive(childFive);

        return childFive;
    }

    protected ArrayList<Position<E>> getChildren(Position<E> pos) {

        ArrayList<Position<E>> children = new ArrayList<>(5);

        if (childOne(pos) != null)
            children.add(childOne(pos));

        if (childTwo(pos) != null)
            children.add(childTwo(pos));

        if (childThree(pos) != null)
            children.add(childThree(pos));

        if (childFour(pos) != null)
            children.add(childFour(pos));

        if (childFive(pos) != null)
            children.add(childFive(pos));

        return children;
    }
}