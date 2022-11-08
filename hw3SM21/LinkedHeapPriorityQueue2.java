import java.util.Comparator;

public class LinkedHeapPriorityQueue2<K,V> extends AbstractPriorityQueue<K,V> {

    protected LinkedBinaryTree<Entry<K,V>> heap = new LinkedBinaryTree<>();

    private Position<Entry<K,V>> lastPosition;
    private Position<Entry<K,V>> rightestPosition; //Tum level dolu olan son leveldeki en sag position
    private Position<Entry<K,V>> leftestPosition; //lastPosition'in bulundugu leveldeki ilk position (en soldaki)
    private int level = 0;
    private int nodeInThatLevel = 0;

    public LinkedHeapPriorityQueue2() {

        super();
    }

    public LinkedHeapPriorityQueue2(Comparator<K> comp) { 
    
        super(comp);
    }

    public LinkedHeapPriorityQueue2(K[] keys , V[] values) {

        super();

        for (int i = 0; i < keys.length; i++) //her key,value pair'ini insertle yerlestirir.
            insert(keys[i] , values[i]);
    }

    public int size() { return heap.size(); }

    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {

        checkKey(key);
        PQEntry<K,V> newest = new PQEntry<K,V>(key, value); //degerimi olusturdum.

        if (heap.isEmpty()) { //Heap bossa root olarak ekleyip prev ve next'ini kendisi (root) yapar

            nodeInThatLevel++;
        
            lastPosition = heap.addRoot(newest);
            heap.setNext(lastPosition , lastPosition);
            heap.setPrev(lastPosition , lastPosition);

            rightestPosition = lastPosition;
            leftestPosition = lastPosition;

            nodeInThatLevel = 0;
            level++;
        }

        else if (heap.size() == 1) { //Heap'te sadece root varsa root ile left birbirlerinin next ve previouslari olur

            nodeInThatLevel++;
        
            lastPosition = heap.addLeft(lastPosition , newest);
            heap.setNext(heap.root() , lastPosition);
            heap.setNext(lastPosition , heap.root());
            heap.setPrev(lastPosition , heap.root());

            rightestPosition = heap.root();
            leftestPosition = lastPosition;
        }
        
        else if (heap.size() == 2) { //Heap'te root ve onun left child'i varsa

            nodeInThatLevel++;
        
            lastPosition = heap.addRight(heap.root() , newest);

            heap.setNext(heap.left(heap.root()) , lastPosition);
            heap.setNext(lastPosition , heap.left(heap.root()));
            heap.setPrev(lastPosition , heap.left(heap.root()));
            heap.setPrev(lastPosition , heap.left(heap.root()));

            rightestPosition = lastPosition;

            level++;
            nodeInThatLevel = 0;
        }

        else {

            nodeInThatLevel++; //O leveldeki node sayisi artar
            
            if (nodeInThatLevel == Math.pow(2 , level)) { //levele ait son node'u ekliyorsam

                heap.setNext(lastPosition , heap.addRight(heap.parent(lastPosition) , newest)); //
                lastPosition = heap.right(heap.parent(lastPosition));
                heap.setNext(lastPosition , leftestPosition);
                heap.setPrev(lastPosition , heap.left(heap.parent(lastPosition)));

                rightestPosition = lastPosition; //Leveldeki son node'a geldigim icin bu, en sagdaki node olarak kaydedilir
                
                nodeInThatLevel = 0; //Bir sonraki levele gececegim icin oradaki node sayisi sifirlanir
                level++; //Sonraki levele gecer
            }

            else if (nodeInThatLevel == 1) { //levele ait ilk node'u ekliyorsam 

                leftestPosition = heap.addLeft(heap.getNext(lastPosition) , newest);
                
                heap.setNext(rightestPosition , leftestPosition);

                lastPosition = leftestPosition;

                heap.setNext(leftestPosition , rightestPosition);
                heap.setPrev(leftestPosition , rightestPosition);
            }

            else if (heap.left(heap.parent(lastPosition)) == lastPosition) { //lastPosition sol node ise

                heap.setNext(lastPosition , heap.addRight(heap.parent(lastPosition) , newest)); //parent'ina sag child eklenip sol child'in nexti yapilir
                lastPosition = heap.right(heap.parent(lastPosition)); //parent'in sag node'u lastPosition yapilir
                heap.setNext(lastPosition , rightestPosition); //sag node'un nexti ust en sagdaki node olur
                heap.setPrev(lastPosition , heap.left(heap.parent(lastPosition))); //sag node'un prev'i sol node olur
                
            }

            else if (heap.right(heap.parent(lastPosition)) != null && heap.right(heap.parent(lastPosition)) == lastPosition) {
            //lastPosition right node ise

                Position<Entry<K,V>> previousLastPos = lastPosition;
                heap.setNext(lastPosition , heap.addLeft(heap.getNext(heap.parent(lastPosition)) , newest)); //lastPosition'in parent'inin nextinin sol child'i olarak koyar ve next olarak ayarlar
                lastPosition = heap.getNext(lastPosition);
                heap.setNext(lastPosition , rightestPosition);
                heap.setPrev(lastPosition , previousLastPos);
            }
        }

        upheap(lastPosition);
        
        return newest;
    }

    public void upheap(Position<Entry<K,V>> givenPos) {

        Position<Entry<K,V>> walk = givenPos; //Her iterasyonda ust seviyeye gececek position

        if (heap.root() != givenPos) { //Verilen node root ise upheap'e gerek yok.

            while (walk != heap.root()) { //Root'a gelene kadar karsilastirarak yerlestirir.

                Position<Entry<K,V>> parentPos = heap.parent(walk); //Bulundugumuz position'un parent'i
                
                if (compare(walk.getElement() , parentPos.getElement()) >= 0) //Parent daha kucuk ya da esitse upheap durur
                    break;
                
                if (compare(walk.getElement() , parentPos.getElement()) < 0) { //Bulundugumuz node parent'indan kucukse yer degisir.

                    Entry<K,V> walkEntry = walk.getElement();
                    heap.set(walk , parentPos.getElement());
                    heap.set(parentPos , walkEntry);
                }

                walk = parentPos;
            }
        }
    }

    public void downheap(Position<Entry<K,V>> entryPos) {
        
        if (heap.left(entryPos) == null) //Sol node yoksa zaten sag da olmayacagindan hicbir sey yapmaz
            return;
        
        Position<Entry<K,V>> walk = entryPos; //Verilen position ile baslar
        
         while (heap.left(walk) != null) { //Sol node'u olmayan bir node'a gelene kadar

            Position<Entry<K,V>> leftPos = heap.left(walk);
            Position<Entry<K,V>> smallPos = leftPos; //Sag ve sol node'lardan en kucuk elemani iceren position

            if (heap.right(walk) != null) { //Sag node da varsa en kucuk elemani bulmak icin sol node ile karsilastirir

                Position<Entry<K,V>> rightPos = heap.right(walk);
                
                if (compare(rightPos.getElement() , smallPos.getElement()) < 0)
                    smallPos = rightPos;
            }

            if (compare(smallPos.getElement() , walk.getElement()) >= 0) //Child'daki eleman parent'indan buyuk ya da ona esitse yapacak bir sey kalmaz
                break;

            //Child daha kucuk oldugunda ikisini swap eder
            Entry<K,V> entry = smallPos.getElement();
            heap.set(smallPos , walk.getElement());
            heap.set(walk , entry);

            walk = smallPos; //Iterasyon her seferinde bir alt seviyeye (child'a) iner
        }
    }

    public boolean isEmpty() {

        return heap.isEmpty();
    }

    public Entry<K,V> min() {

        if (heap.isEmpty()) //Root bossa null doner
            return null;
        
        return heap.root().getElement(); //Aksi hâlde roottaki elementi doner
    }

    public Entry<K,V> removeMin() {

        if (heap.isEmpty()) //Tree bossa null doner
            return null;

        Entry<K,V> removed = null; //silinecek ve return edilecek entry

        Entry<K,V> rootEntry = heap.root().getElement();
        heap.set(heap.root() , lastPosition.getElement());
        heap.set(lastPosition , rootEntry);

        removed = rootEntry;

        if (heap.size() == 1) { //treede sadece root varsa

            level--;
            nodeInThatLevel = 0;

            heap.setNext(lastPosition , null); //silinen position'in next ve previni null yapar
            heap.setPrev(lastPosition , null);

            rightestPosition = null;
            leftestPosition = null;

            heap.remove(lastPosition);
            lastPosition = null;
        }

        else if (heap.size() == 2) { //treede root ve left child'i varsa

            level--;
            nodeInThatLevel = 1; //0. levelde 1 tane node var (o da root)

            heap.setNext(lastPosition , null);
            heap.setPrev(lastPosition , null);
            heap.remove(lastPosition);

            lastPosition = heap.root();
            heap.setNext(lastPosition , lastPosition);
            heap.setPrev(lastPosition , lastPosition);
        }

        else if (heap.size() == 3) { //tree'de root ve child'lari varsa

            nodeInThatLevel--;
        
            Position<Entry<K,V>> previousLastPos = lastPosition; //right child position'u

            lastPosition = heap.getPrev(lastPosition); //yeni lastPosition left child olur

            heap.setNext(previousLastPos , null); //silme islemi
            heap.setPrev(previousLastPos , null);
            heap.remove(previousLastPos);

            heap.setNext(lastPosition , heap.root());
        }

        else {

            if (nodeInThatLevel == 1) { //Son levelde yalniz bir node varken remove yaparsam

                level--;
                nodeInThatLevel = (int)Math.pow(2 , level);

                heap.setNext(lastPosition , null);
                heap.setPrev(lastPosition , null);
                heap.remove(lastPosition);

                lastPosition = rightestPosition;
                heap.setNext(rightestPosition , getLeftestPos());
            }

            else if (nodeInThatLevel == 0 || nodeInThatLevel == (int)Math.pow(2 , level)) { //Level'in en sag node'unu siliyorsak
            //0 veya 2^level dememin sebebi remove veya insert yapilisina gore bazen 0 bazen de 2^level olabiliyor
                nodeInThatLevel = (int)Math.pow(2 , level) - 1;
            
                lastPosition = heap.getPrev(lastPosition);

                heap.setNext(rightestPosition , null);
                heap.setPrev(rightestPosition , null);
                heap.remove(rightestPosition);

                rightestPosition = getRightestPos();

                heap.setNext(lastPosition , rightestPosition);
            }

            else { //lastPosition, diger durumlar haricinde bir left child veya right child ise

                nodeInThatLevel--;
                
                Position<Entry<K,V>> previousLastPos = lastPosition; 
                
                lastPosition = heap.getPrev(lastPosition); //lastPosition onceki node olur

                heap.setNext(lastPosition , rightestPosition); //yeni lastPosition'in next'i en sagdaki node olarak ayarlanir

                heap.setNext(previousLastPos , null); //silme islemi
                heap.setPrev(previousLastPos , null);
                heap.remove(previousLastPos);
            }
        }

        if (lastPosition != null)
            downheap(heap.root());

        return removed;
    }

    private Position<Entry<K,V>> getLeftestPos() {

        Position<Entry<K,V>> walk = heap.root();

        while (heap.left(walk) != null)
            walk = heap.left(walk);

        return walk;
    }

    private Position<Entry<K,V>> getRightestPos() {

        Position<Entry<K,V>> walk = heap.root();

        while (heap.right(walk) != null)
            walk = heap.right(walk);

        return walk;
    }

    public String toString() {

        String heapStr = "";
        
        for (Position<Entry<K,V>> pos: heap.breadthfirst())
            heapStr = heapStr + "key:" + pos.getElement().getKey() + ", value:" + pos.getElement().getValue() + "\n";
    
        return heapStr;
    }
}