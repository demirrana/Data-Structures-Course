import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class hw3Test {
    @Test void testHeap1() {
        LinkedHeapPriorityQueue<Integer,String> heap = new LinkedHeapPriorityQueue<>();

        assertTrue(heap.isEmpty());
        heap.insert(28, "I");
        heap.insert(9, "C");
        heap.insert(5, "X");
        assertEquals("X", heap.removeMin().getValue());
        heap.insert(27, "H");
        heap.insert(7, "B");
        heap.insert(1, "Z");
        heap.insert(14, "F");
        heap.insert(11, "D");
        heap.insert(3, "Y");
        heap.insert(6, "A");
        assertEquals("Z", heap.removeMin().getValue());
        heap.insert(32, "J");
        heap.insert(13, "E");
        heap.insert(35, "K");
        heap.insert(20, "G");
        heap.insert(40, "L");
        assertEquals("Y", heap.removeMin().getValue());
        assertFalse(heap.isEmpty());

        StringBuilder string = new StringBuilder();
        while(!heap.isEmpty())
            string.append(heap.removeMin().getValue()).append(" ");
        assertEquals("A B C D E F G H I J K L ", string.toString());
    }

    @Test void testHeap2() {
        LinkedHeapPriorityQueue2<Integer,String> heap = new LinkedHeapPriorityQueue2<>();

        assertTrue(heap.isEmpty());
        heap.insert(28, "I");
        heap.insert(9, "C");
        heap.insert(5, "X");
        assertEquals("X", heap.removeMin().getValue());
        heap.insert(27, "H");
        heap.insert(7, "B");
        heap.insert(1, "Z");
        heap.insert(14, "F");
        heap.insert(11, "D");
        heap.insert(3, "Y");
        heap.insert(6, "A");
        assertEquals("Z", heap.removeMin().getValue());
        heap.insert(32, "J");
        heap.insert(13, "E");
        heap.insert(35, "K");
        heap.insert(20, "G");
        heap.insert(40, "L");
        assertEquals("Y", heap.removeMin().getValue());
        assertFalse(heap.isEmpty());

        StringBuilder string = new StringBuilder();
        while(!heap.isEmpty())
            string.append(heap.removeMin().getValue()).append(" ");
        assertEquals("A B C D E F G H I J K L ", string.toString());
    }

    @Test void testMaxQueue() {
        MaxPriorityQueue<Integer,String> queue = new MaxPriorityQueue<>();

        assertTrue(queue.isEmpty());
        queue.insert(28, "I");
        queue.insert(9, "C");
        queue.insert(46, "X");
        assertEquals("X", queue.removeMax().getValue());
        queue.insert(27, "H");
        queue.insert(7, "B");
        queue.insert(48, "Z");
        queue.insert(14, "F");
        queue.insert(11, "D");
        queue.insert(43, "Y");
        queue.insert(6, "A");
        assertEquals("Z", queue.removeMax().getValue());
        queue.insert(32, "J");
        queue.insert(13, "E");
        queue.insert(35, "K");
        queue.insert(20, "G");
        queue.insert(40, "L");
        assertEquals("Y", queue.removeMax().getValue());
        assertFalse(queue.isEmpty());

        StringBuilder string = new StringBuilder();
        while(!queue.isEmpty())
            string.append(queue.removeMax().getValue()).append(" ");
        assertEquals("L K J I H G F E D C B A ", string.toString());
    }
}