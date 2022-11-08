public class DriverExpressionTree {

    public static void main(String[] args) {
        
        ExpressionTree tree = new ExpressionTree();

        tree.addRoot("-");
        tree.addLeft(tree.root(), "sin");
        tree.addLeft(tree.left(tree.root()), "+");
        tree.addLeft(tree.left(tree.left(tree.root())), "8");
        tree.addRight(tree.left(tree.left(tree.root())), "x");
        tree.addRight(tree.root(), "10");

        System.out.println();

        ExpressionTree tree2 = new ExpressionTree("( sin ( cos 8 + sin ( 3 - 1 ) ) - 10 )");

        ExpressionTree treeBasic = new ExpressionTree("( 3 + 5 )");

        ExpressionTree derivativeTree = treeBasic.derivative();

        System.out.println(derivativeTree.toString());

        System.out.println("treem: " + tree2.toString());

        System.out.println(tree);

        System.out.println(tree.evaluate(-8));

        System.out.println();

        tree.displayTree();

        ExpressionTree tree3 = new ExpressionTree("( sin ( sin ( 3 + 5 ) + cos 8 ) + 1 )");
        System.out.println(tree3.toString());
    }
}