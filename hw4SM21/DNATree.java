public interface DNATree<E> extends Tree<E> {

    Position<E> childOne(Position<E> p) throws IllegalArgumentException;

    Position<E> childTwo(Position<E> p) throws IllegalArgumentException;
    
    Position<E> childThree(Position<E> p) throws IllegalArgumentException;

    Position<E> childFour(Position<E> p) throws IllegalArgumentException;

    Position<E> childFive(Position<E> p) throws IllegalArgumentException;

    Position<E> sibling(Position<E> p) throws IllegalArgumentException;
  }