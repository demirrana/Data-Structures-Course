public interface PriorityQueueMax<K,V> {

    int size();

    boolean isEmpty();

    Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

    Entry<K,V> max();

    Entry<K,V> removeMax();
  }
  