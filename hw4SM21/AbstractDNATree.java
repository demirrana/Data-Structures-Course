public abstract class AbstractDNATree<E> extends AbstractTree<E> implements DNATree<E> {

    public Position<E> sibling(Position<E> p) { //bir sagindaki sibling'i doner

        Position<E> parent = parent(p);

        if (parent == null)
            return null;

        if (p == childOne(parent))
            return childTwo(parent);

        else if (p == childTwo(parent))
            return childThree(parent);

        else if (p == childThree(parent))
            return childFour(parent);

        else if (p == childFour(parent))
            return childFive(parent);

        else //5. child'in siblingini 1. child olarak ayarladim
            return childOne(parent);
    }

    public int numChildren(Position<E> p) {

        int count=0;

        if (childOne(p) != null)
            count++;

        if (childTwo(p) != null)
            count++;

        if (childThree(p) != null)
            count++;

        if (childFour(p) != null)
            count++;

        if (childFive(p) != null)
            count++;

        return count;
    }

    public Iterable<Position<E>> children(Position<E> p) {

        ArrayList<Position<E>> snapshot = new ArrayList<>(5);

        if (childOne(p) != null)
            snapshot.add(childOne(p));

        if (childTwo(p) != null)
            snapshot.add(childTwo(p));

        if (childThree(p) != null)
            snapshot.add(childThree(p));

        if (childFour(p) != null)
            snapshot.add(childFour(p));

        if (childFive(p) != null)
            snapshot.add(childFive(p));

        return snapshot;
    }

    public Iterable<Position<E>> positions() {
        return preorder();
    }
}