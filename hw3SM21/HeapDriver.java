public class HeapDriver {

    public static void main(String[] args) {

        LinkedHeapPriorityQueue<Integer,String> heap = new LinkedHeapPriorityQueue<>();

        heap.insert(28, "I");
        heap.insert(9, "C");
        heap.insert(5, "X");

        System.out.println(heap.toString());

        System.out.println(heap.removeMin().getValue());

        heap.insert(27, "H");
        heap.insert(7, "B");
        heap.insert(1, "Z");
        heap.insert(14, "F");
        heap.insert(11, "D");
        heap.insert(3, "Y");
        heap.insert(6, "A");

        System.out.println(heap.toString());

        heap.removeMin();

        System.out.println(heap.toString());

        heap.insert(32, "J");
        heap.insert(13, "E");
        heap.insert(35, "K");
        heap.insert(20, "G");
        heap.insert(40, "L");

        heap.removeMin();

        System.out.println(heap.toString());

        System.out.println(heap.isEmpty());

        StringBuilder string = new StringBuilder();
        while(!heap.isEmpty())
            string.append(heap.removeMin().getValue()).append(" ");
        
        /*LinkedHeapPriorityQueue2<String,Integer> pq = new LinkedHeapPriorityQueue2<>();

        System.out.println("LinkedHeapPriorityQueue2:\n");

        pq.insert("a", 1);
        pq.insert("b" , 2);
        pq.insert("c" , 3);
        pq.insert("d" , 4);
        pq.insert("e" , 5);
        pq.insert("f" , 6);
        pq.insert("g" , 7);

        System.out.println("3 leveli dolu hali:\n" + pq.toString());

        pq.insert("h" , 8);
        System.out.println("yeni levele ilk node'u ekleme:");
        System.out.println(pq.toString() + "\n");

        System.out.println("levelin ilk node'unu silme:" );
        pq.removeMin();
        System.out.println(pq.toString() + "\n");

        System.out.println("levelin son node'unu silme:" );
        pq.removeMin();
        System.out.println(pq.toString() + "\n");

        System.out.println("sol child silme:");
        pq.removeMin();
        System.out.println(pq.toString() + "\n");

        System.out.println("sag child silme:");
        pq.removeMin();
        System.out.println(pq.toString() + "\n");



        System.out.println("LinkedHeapPriorityQueue:\n");
        LinkedHeapPriorityQueue<String,Integer> pq2 = new LinkedHeapPriorityQueue<>();

        pq2.insert("a", 1);
        pq2.insert("b" , 2);
        pq2.insert("c" , 3);
        pq2.insert("d" , 4);
        pq2.insert("e" , 5);
        pq2.insert("f" , 6);
        pq2.insert("g" , 7);
        System.out.println(pq2.toString() + "\n");

        System.out.println("yeni levele ilk node'unu ekleme:");
        pq2.insert("h" , 8);
        System.out.println(pq2.toString() + "\n");

        System.out.println("levelin ilk node'unu silme:");
        pq2.removeMin();
        System.out.println(pq2.toString());

        System.out.println("levelin son node'unu silme:");
        pq2.removeMin();
        System.out.println(pq2.toString() + "\n");

        System.out.println("sol child silme:" );
        pq2.removeMin();
        System.out.println(pq2.toString() + "\n");

        System.out.println("sag child silme:");
        pq2.removeMin();
        System.out.println(pq2.toString() + "\n");



        System.out.println("LinkedHeapPriorityQueue ornek 2:\n");
        LinkedHeapPriorityQueue<String,Integer> pq3 = new LinkedHeapPriorityQueue<>();

        pq3.insert("a" , 1);
        System.out.println("root ekleme:\n" + pq3.toString() + "\n");

        pq3.removeMin();
        System.out.println("root silme:\n" + pq3.toString() + "\n");

        pq3.insert("b", 2);
        pq3.insert("c" , 3);
        pq3.removeMin();
        System.out.println("rootun sol child'ini silme:\n" + pq3.toString() + "\n");

        pq3.insert("d" , 4);
        pq3.insert("e" , 5);
        pq3.removeMin();
        System.out.println("rootun sag child'ini silme:\n" + pq3.toString() + "\n\n");



        System.out.println("MaxPriorityQueue:\n");

        MaxPriorityQueue<String,Integer> pq4 = new MaxPriorityQueue<>();

        pq4.insert("a", 1);
        pq4.insert("b" , 2);
        pq4.insert("c" , 3);
        pq4.insert("d" , 4);
        pq4.insert("e" , 5);
        pq4.insert("f" , 6);
        pq4.insert("g" , 7);

        System.out.println(pq4.toString() + "\n");

        System.out.println("yeni levele ilk node'unu ekleme:");
        pq4.insert("h" , 8);
        System.out.println(pq4.toString() + "\n");

        System.out.println("levelin ilk node'unu silme:");
        pq4.removeMax();
        System.out.println(pq4.toString() + "\n");

        System.out.println("levelin son node'unu silme:");
        pq4.removeMax();
        System.out.println(pq4.toString() + "\n");

        System.out.println("sol node silme:");
        pq4.removeMax();
        System.out.println(pq4.toString() + "\n");

        System.out.println("sag node silme:");
        pq4.removeMax();
        System.out.println(pq4.toString());*/
    }
}