public class Driver {

    public static void main(String[] args) {

        LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();

        tree.addRoot("ancak");
        tree.addLeft(tree.root() , "ama");
        tree.addRight(tree.root() , "asla");
        tree.addLeft(tree.left(tree.root()) , "ankara");
        tree.addRight(tree.left(tree.root()) , "adam");

        Position<String> pos = tree.right(tree.left(tree.root()));
        upheap(pos , tree);

        System.out.println(tree.root().getElement() + " " + tree.left(tree.root()).getElement());
    }

    public static void upheap(Position<String> entryPos , LinkedBinaryTree<String> tree) {

        if (tree.parent(entryPos) == null) //elimdekinin parenti yoksa 
            return;

        Position<String> walk = tree.parent(entryPos);
        
        while (walk != tree.root()) {

            walk = tree.parent(entryPos);
            String parentVal = "";

            if (entryPos.getElement().compareTo(walk.getElement()) < 0) {

                parentVal = walk.getElement();
                tree.set(walk , entryPos.getElement());
                tree.set(entryPos , parentVal);
            }

            entryPos = walk;
        }
        
        
    }
}