import java.util.Comparator;

public class LinkedHeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {

    protected LinkedBinaryTree<Entry<K,V>> heap = new LinkedBinaryTree<>();

    private Position<Entry<K,V>> lastPosition;

    public LinkedHeapPriorityQueue() {

        super();
    }

    public LinkedHeapPriorityQueue(Comparator<K> comp) { 
    
        super(comp);
    }

    public LinkedHeapPriorityQueue(K[] keys , V[] values) {

        super();

        for (int i = 0; i < keys.length; i++) //her key,value pair'ini insertle yerlestirir.
            insert(keys[i] , values[i]);
    }

    public int size() { return heap.size(); }

    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {

        checkKey(key);
        PQEntry<K,V> newest = new PQEntry<K,V>(key, value); //degerimi olusturdum.

        Position<Entry<K,V>> walk = lastPosition;

        boolean inserted = false;
        
        if (heap.isEmpty()) { //bos bir binarytree ise root olarak ekler

            lastPosition = heap.addRoot(newest);
            inserted = true; //looplara girmez
        }

        else if (heap.root() == lastPosition) { //heapte sadece root varsa direkt sola ekler.

            lastPosition = heap.addLeft(lastPosition , newest);
            inserted = true; //looplara girmez
        }

        while (!inserted) { //bu loopta sag veya sol node olmasina gore sol veya root node'a ulasmaya calisir

            if (walk == heap.root()) //roota ulasirsa cikar
                break;

            else if (heap.right(heap.parent(walk)) == walk) //son node bir right child ise
                walk = heap.parent(walk); //parent'a giderek devam et.

            else if (heap.left(heap.parent(walk)) == walk) { //bulundugumuz node bir left child ise

                if (heap.right(heap.parent(walk)) == null) { //node'umun parentinin righti bossa oraya yerlestiririm.

                    heap.addRight(heap.parent(walk) , newest);
                    lastPosition = heap.right(heap.parent(walk)); //last node'umu guncelledim.
                    inserted = true; //asagidaki while'a girmez.
                    break;
                }
                
                walk = heap.right(heap.parent(walk)); //node'un parent'inin sag node'una gider.
                break;
            }
        }

        while (!inserted) { //bu kez buldugum root ya da left childdan asagiya dogru bakiyorum

            if (heap.left(walk) == null) { //left node'u bos olana kadar arar, bulunca oraya yerlestiririm.

                heap.addLeft(walk , newest);
                lastPosition = heap.left(walk);
                inserted = true;
                break;
            }

            walk = heap.left(walk);
        }

        upheap(lastPosition); //her eklenen node icin heap-order property saglanir

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

        if (heap.size() == 1) { //Yalnizca root varsa direkt onu siler

            removed = heap.root().getElement();
            lastPosition = null;
            heap.remove(heap.root());
            return removed;
        }

        else if (heap.size() == 2) {

            removed = heap.left(heap.root()).getElement();
            lastPosition = heap.root();
            heap.remove(heap.left(lastPosition));
            return removed;
        }

        else if (heap.size() == 3) {

            removed = heap.right(heap.root()).getElement();
            lastPosition = heap.left(heap.root());
            heap.remove(heap.right(heap.root()));
            return removed;
        }
        
        //Root ile son node'daki elementleri swap eder
        Entry<K,V> rootEntry = heap.root().getElement(); 
        heap.set(heap.root() , lastPosition.getElement());
        heap.set(lastPosition , rootEntry);

        Position<Entry<K,V>> removedPos = lastPosition;
        boolean foundLast = false; //yeni last node'u bulup bulamadigimizi belirten boolean

        while (!foundLast) {

            if (heap.root() == lastPosition) //root'a ulastiysa right node'lara bakmak icin diger loopa gecer
                break;
            
            else if (heap.left(heap.parent(lastPosition)) == lastPosition) //node bir left childsa parentini alarak devam eder
                lastPosition = heap.parent(lastPosition);

            else if (heap.right(heap.parent(lastPosition)) != null && heap.right(heap.parent(lastPosition)) == lastPosition) {
            //node bir right childsa left sibling son node olarak ayarlanir
                lastPosition = heap.left(heap.parent(lastPosition));
                break;
            }
        }

        while (!foundLast) { //Buraya geldiyse ya roottur ya da bir parent'in left node'udur

            if (heap.left(lastPosition) == null) { //Child'i yoksa bu last node'dur

                foundLast = true;
                break;
            }

            lastPosition = heap.right(lastPosition); //Child'i olmayan en son node'u bulana kadar sagdan devam eder
        }
        
        heap.remove(removedPos); //Bastaki kaydettigimiz last node pozisyonundaki elementleri siler

        downheap(heap.root()); //Tekrar heap-order property saglanir.

        return removed;
    }

    public String toString() {

        String heapStr = "";
        
        for (Position<Entry<K,V>> pos: heap.breadthfirst())
            heapStr = heapStr + "key:" + pos.getElement().getKey() + ", value:" + pos.getElement().getValue() + "\n";
    
        return heapStr;
    }
}