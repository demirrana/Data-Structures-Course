public class DNATreeExtended<E> extends LinkedDNATree<E> {

    public DNATreeExtended() {}
    
    public void insert(E element) {

        if (findSequence(root() , element.toString()) != null) { //roottan baslayip node'u aradigimda boyle bir node varsa ekleme yapilamaz
        //Bu metot 524. satirda
            System.out.println("There is already a node named " + element.toString() + " in the DNA Tree.");
            return;
        }

        String sequence = element.toString();
        String sequence$ = sequence + "$"; //yerlestirme yaparken $ karakterini de kullaniyorum

        Position<E> walk = root();

        if (walk == null) { //root yoksa root olarak ekler

            addRoot(element);
            System.out.println("The insertion of " + sequence + " was successful.");
            System.out.println("Level of the leaf node inserted : 0");
        }

        if (size() == 1 && root().getElement() == null) { //sadece root varsa ve onda da eleman yoksa oraya ekler

            root.setElement(element);
            System.out.println("The insertion of " + sequence + " was successful.");
            System.out.println("Level of the leaf node inserted : 0");
            return;
        }

        String treeSequence = ""; //treedeki branchleri her iterasyonda ekleyerek olusturulan sequence

        for (int i = 0; i < sequence$.length(); i++) {

            char character = sequence$.charAt(i);

            if (walk.getElement() == null && isExternal(walk)) { //geldigim position bos ve externalsa oraya element'i koyarim

                validate(walk).setElement(element);
                System.out.println("The insertion of " + sequence + " was successful.");
                System.out.println("Level of the leaf node inserted : " + i); //||||||||||||||||||||<
                return;
            }
            //diger karakterler icin de aynisi ($ haric)
            if (character == 'A') { //geldigimiz sequence karakteri A ise

                treeSequence += "A"; //tree'deki branchleri ekleyerek olusan sequence

                if (childOne(walk) != null) { //childOne null degilse 

                    if (childOne(walk).getElement() != null) { //aradigim position'da zaten node varsa split edip onu yerlestiririm
                    //i ve treeSequence guncelleyince ayni iterasyonu ilk defa donuyor gibi olur
                        split(childOne(walk) , i + 1); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }
                    
                    walk = childOne(walk); //position varsa A branch'ine ilerler

                    if (isInternal(walk) && treeSequence.equals(sequence)) { 
                    //branchlerden geldigimiz treeSequence ile sequence esitse ve internal node ise $ branchi olan childFive'a ekler ("A"nin asil yerine yerlestirilmesi gibi)

                        addChildFive(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + (i + 2));
                        return;
                    }
                }

                else if (childOne(walk) == null) { //childOne yoksa

                    if (walk.getElement() == null) { //uzerinde oldugumuz position'in elemani yoksa oraya ekleriz

                        addChildOne(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + i); //|||||||||||||||||||||||<
                        return;
                    }

                    else { //ya da position'da bir elemen mevcutsa split yapip isleme devam ederiz

                        split(walk , i); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }
                }
            }

            else if (character == 'C') {

                treeSequence += "C";

                if (childTwo(walk) != null) {

                    if (childTwo(walk).getElement() != null) {

                        split(childTwo(walk) , i + 1); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }

                    walk = childTwo(walk);

                    if (isInternal(walk) && treeSequence.equals(sequence)) {

                        addChildFive(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + (i + 2));
                        return;
                    }
                }

                else if (childTwo(walk) == null) {

                    if (walk.getElement() == null) {

                        addChildTwo(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + i); //||||||||||||||||||||<
                        return;
                    }

                    else {

                        split(walk , i); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }
                }
            }

            else if (character == 'G') {

                treeSequence += "G";

                if (childThree(walk) != null) {

                    if (childThree(walk).getElement() != null) {

                        split(childThree(walk) , i + 1); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }

                    walk = childThree(walk);

                    if (isInternal(walk) && treeSequence.equals(sequence)) {

                        addChildFive(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + (i + 2));
                        return;
                    }
                }

                else if (childThree(walk) == null) {

                    if (walk.getElement() == null) {

                        addChildThree(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + i); //|||||||||||||||||||||||||||<
                        return;
                    }

                    else {

                        split(walk , i); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }
                }
            }

            else if (character == 'T') {

                treeSequence += "T";

                if (childFour(walk) != null) {

                    if (childFour(walk).getElement() != null) {

                        split(childFour(walk) , i + 1); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }

                    walk = childFour(walk);

                    if (isInternal(walk) && treeSequence.equals(sequence)) {

                        addChildFive(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + (i + 2));
                        return;
                    }
                }

                else if (childFour(walk) == null) {

                    if (walk.getElement() == null) {

                        addChildFour(walk , element);
                        System.out.println("The insertion of " + sequence + " was successful.");
                        System.out.println("Level of the leaf node inserted : " + i); //|||||||||||||||||||||||||||||<
                        return;
                    }

                    else {

                        split(walk , i); //639. satir
                        i--;

                        if (treeSequence.length() == 1)           treeSequence = "";
                        else
                            treeSequence = treeSequence.substring(0 , treeSequence.length() - 1);

                        continue;
                    }
                }
            }

            else if (character == '$') { //$ karakterine gelebildiyse asil leveline ulasabilir ve $ branchine eklenir

                addChildFive(walk , element);
                System.out.println("The insertion of " + sequence + " was successful.");
                System.out.println("Level of the leaf node inserted : " + i); //|||||||||||||||||||||||||||||||||||<
                return;
            }
        }
    }

    public void remove(E element) {

        Position<E> pos = findSequence(root() , element.toString()); //varsa tree'deki yerini bulur (524. satir)

        if (pos == null) { //boyle bir sequence tree'de bulunmuyorsa silinemez

            System.out.println("There is no such node named " + element.toString() + " and it cannot be removed.");
            return;
        }

        if (pos == root()) { //sequence, root'ta yer aliyorsa yalniz element'i silinir

            Node<E> node = validate(pos);
            node.setElement(null);
            return;
        }

        mergeRemove(pos); //diger durumlarda asil silmeyi yapan metodu cagiririm (667. satir)
    }

    public void display() {

        preorderDisplay(false); //false verince uzunluk olmadan yazar (704. satir)
    }

    public void displayLengths() {

        preorderDisplay(true); //true verince uzunlukla beraber yazar (704. satir)
    }

    public void search(E sequenceDescriptor) {

        String sequence = sequenceDescriptor.toString();
        int len = sequence.length();

        int visits[] = {0}; //visit edilen node sayisini tuttugum array (array cunku manipule etmesi kolay)

        Position<E> pos = root();
        visits[0] = 1;

        if (pos == null) //root nullsa hicbir sey donmez
            return;

        if (size() == 1 && root().getElement() != null) { //treede sadece root varsa ve uyumluysa onun elementi yazilir

            if (sequence.charAt(len - 1) == '$' && root().getElement().toString().equals(sequence.substring(0 , len - 1))) {

                System.out.println(root.getElement().toString());
                System.out.println("Nodes visited : 1");
            }
            else if (sequence.charAt(len - 1) != '$' && root().getElement().toString().equals(sequence)) {

                System.out.println(root.getElement().toString());
                System.out.println("Nodes visited : 1");
            }
            return;
        }

        String treeSequence = "";

        if (isExternal(pos) && pos.getElement() == null) //sadece root varsa ve icinde eleman yoksa hicbir sey donmez
            return;
        //sonunda $ bulunan sequencelar icin loop
        for (int i = 0; i < sequence.length() && sequence.charAt(len - 1) == '$'; i++) {

            char character = sequence.charAt(i);
            //her karakterdeki mantik su: 
            if (character == 'A') {

                if (childOne(pos) != null) { //baktigim yerde node mevcutsa pos'u oraya set edip iterasyona devam ediyorum

                    visits[0] = visits[0] + 1;
                    pos = childOne(pos);
                }
                
                else if (childOne(pos) == null) { //baktigim yerde node yoksa

                    if (pos.getElement() == null) //elimdeki position empty ise bulunamaz
                        return;

                    if (pos.getElement().toString().equals(sequence.substring(0 , len - 1))) {
                    //elimdeki position verilen sequence'a esitse yazdiririm
                        System.out.println(pos.getElement().toString());
                        System.out.println("Visited nodes : " + visits[0]);
                    }

                    return; //farkli bir eleman varsa da bulunamaz
                }
            }
                
            else if (character == 'C') {

                if (childTwo(pos) != null) {

                    visits[0] = visits[0] + 1;
                    pos = childTwo(pos);
                }
                
                else if (childTwo(pos) == null) {

                    if (pos.getElement() == null)
                        return;
                    
                    if (pos.getElement().toString().equals(sequence.substring(0 , len - 1))) {

                        System.out.println(pos.getElement().toString());
                        System.out.println("Nodes visited : " + visits[0]);
                    }
                    
                    return;
                }
            }

            else if (character == 'G') {
                
                if (childThree(pos) != null) {

                    visits[0] = visits[0] + 1;
                    pos = childThree(pos);
                }
                
                else if (childThree(pos) == null) {

                    if (pos.getElement() == null)
                        return;

                    if (pos.getElement().toString().equals(sequence.substring(0 , len - 1))) {

                        System.out.println(pos.getElement().toString());
                        System.out.println("Nodes visited : " + visits[0]);
                    }

                    return;
                }
            }
             
            else if (character == 'T') {

                if (childFour(pos) != null) {

                    visits[0] = visits[0] + 1;
                    pos = childFour(pos);
                }
                
                else if (childFour(pos) == null) {

                    if (pos.getElement() == null)
                        return;

                    if (pos.getElement().toString().equals(sequence.substring(0 , len - 1))) {

                        System.out.println(pos.getElement().toString());
                        System.out.println("Visited nodes : " + visits[0]);
                    }

                    return;
                }
            }

            else if (character == '$') { //$ branchine kadar geldiyse 5. child ya da bulunulan position'da mi diye bakilir

                if (pos.getElement() != null && pos.getElement().toString().equals(sequence.substring(0 , len - 1))) {
                //bulundugumuz yerde eleman varsa ve bu eleman sequence'a esitse onu yazar
                    System.out.println(pos.getElement().toString());
                    System.out.println("Visited nodes : " + visits[0]);
                }
                
                if (childFive(pos) == null) //childFive'i yoksa null doner
                    return;
                
                pos = childFive(pos);
                visits[0] = visits[0] + 1;

                if (pos.getElement() == null) //childFive icinde degilse null doner
                    return;
                if (!pos.getElement().toString().equals(sequence.substring(0 , len - 1))) //childFive icindeki elemana esit degilse null doner
                    return;

                System.out.println(pos.getElement().toString());
                System.out.println("Visited nodes : " + visits[0]);
            }
        }

        treeSequence = ""; //branchlerden gectikce harf olarak ekliyorum ki sequence ile ayniysa gerekli yere ($) koyabileyim

        for (int i = 0; i < len && sequence.charAt(len - 1) != '$'; i++) {
        //$ olmadan arama olan durumlar icin
            char character = sequence.charAt(i);

            if (character == 'A') { //karakter mantigim su sekilde:

                treeSequence += "A"; //her branch'e geldigimde treeSequence'e ekliyorum
                
                if (childOne(pos) != null) { //A yonunde node varsa oraya gider ve visit edilen sayisini arttirir

                    pos = childOne(pos);
                    visits[0] = visits[0] + 1;
                }
                else //A yonunde node yoksa istenen sekilde baslayan nodelar da yoktur
                    return; 
            }

            else if (character == 'C') {

                treeSequence += "C";
                
                if (childTwo(pos) != null) {

                    pos = childTwo(pos);
                    visits[0] = visits[0] + 1;
                }
                else 
                    return; 
            }

            else if (character == 'G') {

                treeSequence += "G";
                
                if (childThree(pos) != null) {

                    pos = childThree(pos);
                    visits[0] = visits[0] + 1;
                }
                else 
                    return; 
            }

            else if (character == 'T') {

                treeSequence += "T";
                
                if (childFour(pos) != null) {

                    pos = childFour(pos);
                    visits[0] = visits[0] + 1;
                }
                else 
                    return; 
            }

            if ((treeSequence.startsWith(sequence)) || 
            (pos.getElement() != null && pos.getElement().toString().equals(sequence))) { //her iterasyonda branch'lerle 
            //olusan sequence eldeki sequence'le basliyor mu diye bakip basliyorsa veya gelinen position'da aradigimiz 
            //sequence varsa o position'in tum children kismini vs. aliyorum

                printRelated$(pos , visits); //ne kadar children varsa hepsini alir (628. satir)
                System.out.println("Nodes visited : " + visits[0]);
                return;
            }
        }
    }

    //verilen postan baslayip child'lara dogru giderek varsa istenen sequence'in position'unu bulur
    private Position<E> findSequence(Position<E> pos , String sequence) {

        String sequence$ = sequence + "$"; //daha kolay bulabilmek icin $ da ekliyorum ($ branchinde bulunan varsa)

        if (pos == null)
            return null;

        if (isExternal(pos) && pos.getElement() == null) //treede sadece root varsa ve elemani yoksa bulamaz
            return null;

        for (int i = 0; i < sequence$.length(); i++) {

            char character = sequence$.charAt(i);

            if (character == 'A') { //her karakter icin mantigim su sekilde:

                if (childOne(pos) != null) //A branchinde node varsa oradan devam eder
                    pos = childOne(pos);
                
                else if (childOne(pos) == null) { //A branchinde node yoksa

                    if (pos.getElement() == null) //ustunde bulundugumuz position bossa bulamaz
                        return null;

                    if (pos.getElement().toString().equals(sequence)) //bulundugumuz pos'ta sequence bulunuyorsa orayi doner
                        return pos;

                    return null; //positiondaki sequence farkliysa bulamaz
                }
            }
                
            else if (character == 'C') {

                if (childTwo(pos) != null)
                    pos = childTwo(pos);
                
                else if (childTwo(pos) == null) {

                    if (pos.getElement() == null)
                        return null;
                    
                    if (pos.getElement().toString().equals(sequence))
                        return pos;
                    
                    return null;
                }
            }

            else if (character == 'G') {
                
                if (childThree(pos) != null)
                    pos = childThree(pos);
                
                else if (childThree(pos) == null) {

                    if (pos.getElement() == null)
                        return null;

                    if (pos.getElement().toString().equals(sequence))
                        return pos;

                    return null;
                }
            }
             
            else if (character == 'T') {

                if (childFour(pos) != null)
                    pos = childFour(pos);
                
                else if (childFour(pos) == null) {

                    if (pos.getElement() == null)
                        return null;

                    if (pos.getElement().toString().equals(sequence))
                        return pos;

                    return null;
                }
            }

            else if (character == '$') { //sona gelindiginde

                if (pos.getElement() != null && pos.getElement().toString().equals(sequence)) //bulundugumuz yerde mi bakiyorum
                    return pos;
                
                if (childFive(pos) == null) //childFive'i yoksa null doner
                    return null;
                
                pos = childFive(pos);

                if (pos.getElement() == null) //childFive icinde degilse null doner
                    return null;
                if (!pos.getElement().toString().equals(sequence)) //childFive icindeki elemana esit degilse null doner
                    return null;

                return pos;
            }
        }

        return pos;
    }

    private void printRelated$(Position<E> pos , int[] visits) { //verilen pos'un tum descendant'larinin bos olmayan
    //elemanlarini recursive sekilde yazdirir
        if (pos.getElement() != null)
            System.out.println(pos.getElement());
        
        visits[0] = visits[0] + 1;

        for (Position<E> child : children(pos)) //preorder seklinde
            printRelated$(child , visits);
    }

    private void split(Position<E> walk , int characterIndex) { //walk position'daki elementi indexteki karakterine gore 
    //alt leveldeki node'lari acip oradan uygun olana yerlestirilir

        Node<E> walkNode = validate(walk); 

        //split edecegim node'un uzunlugu geldigim indexle ayni ise childFive olarak yerlestirilir
        if (walk.getElement().toString().length() == characterIndex) {

            addChildFive(walk , walk.getElement());
            walkNode.setElement(null);
            return;
        }

        //karaktere gore child yerini belirleyip ekliyorum
        char character = walk.getElement().toString().charAt(characterIndex);

        if (character == 'A')
            addChildOne(walk , walk.getElement());
        else if (character == 'C')
            addChildTwo(walk , walk.getElement());
        else if (character == 'G')
            addChildThree(walk , walk.getElement());
        else if (character == 'T')
            addChildFour(walk , walk.getElement());

        walkNode.setElement(null);
    }

    private void mergeRemove(Position<E> pos) { //merge islemi gerekli mi diye kontrol edip gerekliyse siblingleri 
    //birlestirip bir leveli siler

        Position<E> siblingWalk = pos;

        boolean mergeNeeded = true;
        
        for (int i = 0; i < 4; i++) { //eldeki position haric diger position'larda element var mi 
        //ya da external mi ona gore merge var ya da yok diyor

            siblingWalk = sibling(siblingWalk); //sibling metodum her seferinde bir sagdaki siblingi donuyor

            if (siblingWalk.getElement() != null || !isExternal(siblingWalk)) //siblinglerden biri internalsa veya bos degilse merge yapilmaz
                mergeNeeded = false;
        }

        Node<E> node = validate(pos);

        if (mergeNeeded) { //merge gerekiyorsa parentin tum cocuklarini null'a set eder ve position'u siler

            Position<E> parentPos = parent(pos);

            Node<E> parentNode = validate(parentPos);
            parentNode.setChildOne(null);
            parentNode.setChildTwo(null);
            parentNode.setChildThree(null);
            parentNode.setChildFour(null);
            parentNode.setChildFive(null);

            node.setElement(null);
            pos = null;
        }

        else //merge gerekmiyorsa sadece icindeki elemani siler
            node.setElement(null);
    }

    private Iterable<Position<E>> preorderDisplay(boolean printLength) { 
    //preorder mantigi
        ArrayList<Position<E>> positions = new ArrayList<>();

        String dots = "";

        if (!isEmpty())
            preorderDisplayHelper(root() , positions , dots , printLength);

        return positions;
    }

    private void preorderDisplayHelper(Position<E> pos , ArrayList<Position<E>> positions , String dots , boolean printLength) {
    //length istediginde her seferinde . ekleyedigi dot stringini doner ve preorder mantigiyla calisir
        if (pos.getElement() != null) {

            if (printLength)
                System.out.println(dots  + pos.getElement().toString() + " " + pos.getElement().toString().length());
            else
                System.out.println(dots + pos.getElement().toString());
        }
            
        else if (isExternal(pos))
            System.out.println(dots + "E");
        else if (isInternal(pos))
            System.out.println(dots + "I");
        
        dots += ".";

        for (Position<E> child : children(pos))
            preorderDisplayHelper(child , positions , dots , printLength);
    }
}