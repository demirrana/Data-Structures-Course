public class ExpressionTree extends LinkedBinaryTree<String> {

    public ExpressionTree() { }

    public ExpressionTree(String infixExpression) {
    //bu metotta parantez sayilarindan ortadaki islemi bulup bunu root olarak ekliyorum. Root bulana kadar da
    //leftStack'i bu ifadenin sol tarafiyla dolduruyorum. Once bu stacki yardimci metoduma veriyorum (45. SATIR)
    //leftStack'in doldugunu bir degiskenle kontrol edip rightStacki sag tarafla dolduruyorum ve bunun icin de
    //loop bitince yardimci metodumu cagiriyorum. 
        LinkedStack<String> leftStack = new LinkedStack<>();
        LinkedStack<String> rightStack = new LinkedStack<>();

        String[] tokens = infixExpression.split(" ");

        int openingParantheses = 0 , closingParantheses = 0; //Kapanan parantez acilandan bir eksik oldugunda ortadaki islemi bulacak.

        boolean leftFinished = false;

        for (int i = 0; i < tokens.length; i++) {

            if (leftFinished)
                rightStack.push(tokens[i]);
            
            else if (isOperation(tokens[i]) && closingParantheses == openingParantheses - 1) {

                addRoot(tokens[i]);
                addToTree(leftStack , root() , "left" , false); //rootun leftine stacktekileri ekleyecek
                leftFinished = true;
            }

            else
            leftStack.push(tokens[i]); //sag tarafa ait degilse ekler

            if (tokens[i].equals("("))         openingParantheses++;
            else if (tokens[i].equals(")"))    closingParantheses++;
        }

        addToTree(rightStack , root() , "right" , false);
    }

    private void addToTree(LinkedStack<String> leftStack , Position<String> parentPos , String rightOrLeft , boolean extraSituation) {
    //stack ortadaki islemin sag veya solunu icerir, bu icerik parentPos'a left veya right olarak eklenir
    //rightOrLeft de saga mi sola mi eklenecegini gosterir.

        ArrayList<String> list = new ArrayList<>();
        LinkedStack<String> rightStack = new LinkedStack<>(); //metottan gelen stacki bosaltip sol tarafi dolduruyorum buna da sag tarafi

        while (!leftStack.isEmpty())
            list.add(0 , leftStack.pop());

        int openingParantheses = 0 , closingParantheses = 0;
        boolean leftFinished = false;

        String theOperation = "";

        boolean extraSituationController = false;

        if (rightOrLeft.equals("left") && (list.get(1).contains("sin") || list.get(1).contains("cos")) && 
            list.get(list.size() - 1).equals(")") && !extraSituation) {

            for (int i = 2; i < list.size(); i++) {

                if (leftFinished)
                    rightStack.push(list.get(i));

                else if (isOperation(list.get(i)) && openingParantheses - 1 == closingParantheses) {
                    extraSituationController = true;
                    leftFinished = true;
                    theOperation = list.get(i);
                }
                    
                else
                    leftStack.push(list.get(i));

                if (list.get(i).equals("("))        openingParantheses++;
                else if (list.get(i).equals(")"))   closingParantheses++;
            }
            
            addLeft(parentPos , list.get(1)); //sin veya cosu left olarak yerlestirir

            if (extraSituationController) {

                addLeft(left(parentPos) , theOperation);
                addToTree(leftStack , left(left(parentPos)) , "left" , false);
                addToTree(rightStack , left(left(parentPos)) , "right" , false);
            }
            else {
                addToTree(leftStack , left(parentPos) , "left" , extraSituationController);

                if (!rightStack.isEmpty())
                    addToTree(rightStack , left(parentPos), "right" , extraSituationController);
            }
        }

        else if (rightOrLeft.equals("right") && (list.get(0).contains("sin") || list.get(0).contains("cos")) && 
            list.get(list.size() - 1).equals(")") && list.get(list.size() - 2).equals(")")
            ) {

            for (int i = 1; i < list.size() - 1; i++) {

                if (leftFinished)
                    rightStack.push(list.get(i));
                else if (isOperation(list.get(i)) && openingParantheses - 1 == closingParantheses) {
                    extraSituationController = true;
                    leftFinished = true;
                    theOperation = list.get(i);
                }
                else
                    leftStack.push(list.get(i));
    
                if (list.get(i).equals("("))        openingParantheses++;
                else if (list.get(i).equals(")"))   closingParantheses++;
            }
            
            addRight(parentPos , list.get(0));

            if (extraSituationController) {

                addLeft(right(parentPos) , theOperation);
                addToTree(leftStack , left(right(parentPos)) , "left" , false);
                addToTree(rightStack , left(right(parentPos)) , "right" , false);
            }
            else 
                addToTree(leftStack , right(parentPos) , "left" , extraSituationController);
        }

        else if (rightOrLeft.equals("left") && (list.get(1).equals("cos") || list.get(1).equals("sin")) && 
                list.size() == 3) { // ( sin a seklindeki girdiler icin

            addLeft(parentPos , list.get(1)); //once sin veya cosu
            addLeft(left(parentPos) , list.get(2)); //sonra sin veya cosa bagli sayiyi ekler
        }

        else if (rightOrLeft.equals("right") && (list.get(0).equals("sin") || list.get(0).equals("cos")) &&
                list.size() == 3) { // sin a ) seklinde bir ifade

            addRight(parentPos , list.get(0)); //once sin veya cosu
            addLeft(right(parentPos) , list.get(1)); //sonra sin veya cosa bagli sayiyi ekler
        }

        else if (rightOrLeft.equals("left") && list.size() == 4) { 
        // (a + b seklinde veriyorum sadece sin veya cos icindeyse

            addLeft(parentPos , list.get(2));
            addLeft(left(parentPos) , list.get(1));
            addRight(left(parentPos) , list.get(3));
        }

        else if (rightOrLeft.equals("left") && list.size() == 2) {

            addLeft(parentPos , list.get(1));
        }

        else if (rightOrLeft.equals("right") && list.size() == 2) {

            addRight(parentPos , list.get(0));
        }

        else { 
            
            for (int i = 0; i < list.size(); i++) {

                if (leftFinished)
                    rightStack.push(list.get(i));
                
                else if (isOperation(list.get(i)) && closingParantheses == openingParantheses - 1) {

                    addLeft(parentPos , list.get(i));
                    addToTree(leftStack , left(parentPos) , "left" , false); //rootun leftine stacktekileri ekleyecek
                    leftFinished = true;
                }
                else
                    leftStack.push(list.get(i));
            
                if (list.get(i).equals("("))         openingParantheses++;
                else if (list.get(i).equals(")"))    closingParantheses++;
            }

            addToTree(rightStack , left(parentPos) , "right" , false);

        }

    }
    
    public ExpressionTree derivative() {

        ExpressionTree tree = new ExpressionTree();

        String infixExpression = toString();

        LinkedStack<String> leftStack = new LinkedStack<>();
        LinkedStack<String> rightStack = new LinkedStack<>();

        LinkedStack<String> derivationStack = new LinkedStack<>(); //turevleri alip koydugum stack

        String[] tokens = infixExpression.split(" ");

        int openingParantheses = 0 , closingParantheses = 0; //Kapanan parantez acilandan bir eksik oldugunda ortadaki islemi bulacak.

        boolean leftFinished = false;

        String theOperation = "";
        int operationIndex = 0;

        for (int i = 0; i < tokens.length; i++) {

            if (leftFinished) {

                rightStack.push(tokens[i]);
                continue;
            }
            
            else if (isOperation(tokens[i]) && closingParantheses == openingParantheses - 1) {
 
                theOperation = tokens[i];
                operationIndex = i;
                leftFinished = true;
                continue;
            }
            else
                leftStack.push(tokens[i]); //sag tarafa ait degilse ekler
            
            if (tokens[i].equals("("))         openingParantheses++;
            else if (tokens[i].equals(")"))    closingParantheses++;
        }

        if (theOperation.equals("+") || theOperation.equals("-")) {

            takeDerivative(leftStack , derivationStack , "left" , false , false);
            derivationStack.push(theOperation);
            takeDerivative(rightStack , derivationStack , "right" , false , false);
        }

        else {

            takeDerivative(leftStack , derivationStack , "left" , false , false);
            derivationStack.push(theOperation);

            for (int i = operationIndex + 1; i < tokens.length; i++)
                derivationStack.push(tokens[i]);

            takeDerivative(rightStack , derivationStack , "right" , false , false);

            derivationStack.push("+");

            for (int i = 0; i < operationIndex; i++)
                derivationStack.push(tokens[i]);

            derivationStack.push(theOperation);
            takeDerivative(rightStack , derivationStack , "right" , false , false);
        }

        String derivation = "";

        while (!derivationStack.isEmpty())
            derivation = derivationStack.pop() + " " + derivation;

        tree = new ExpressionTree(derivation);

        return tree;
    }
    
    //leftStack -> ifadenin islemden sol tarafa olan butun kismi ya da ozel durumlarda tum ifade
    //derivationStack -> turevleri alip ekledigim stack
    //rightOrLeft -> bu ifade islemin sag tarafi mi sol tarafi mi
    //cosOrSinOut -> bu ifade bir sin veya cos'un icinde mi yer aliyordu
    //extraSituation -> sin (sin(a + b) - cos(a * b)) gibi bir durum varsa (buna ozel if else koyamadigim icin)
    private void takeDerivative(LinkedStack<String> leftStack , LinkedStack<String> derivationStack , 
                                  String rightOrLeft , boolean cosOrSinOut , boolean extraSituation) {
        ArrayList<String> list = new ArrayList<>();
        LinkedStack<String> rightStack = new LinkedStack<>(); //metottan gelen stacki bosaltip sol tarafi dolduruyorum buna da sag tarafi

        while (!leftStack.isEmpty())
            list.add(0 , leftStack.pop());

        int openingParantheses = 0 , closingParantheses = 0;
        boolean leftFinished = false;

        String theOperation = "";
        int operationIndex = 0;
        
        boolean extraSituationController = false; //sin icindeki bir ifade (sin x + cos ( a + b) gibi bir seyse ayrica bakmak icin kullaniyorum
        
        //sol ifade sin veya cos ile basliyorsa
        if (rightOrLeft.equals("left") && (list.get(1).equals("sin") || list.get(1).equals("cos")) && 
            list.get(list.size() - 1).equals(")")  && !extraSituation) {

            derivationStack.push("(");

            for (int i = 2; i < list.size(); i++) { //icerideki ifadeyi infix sekle getirir

                leftStack.push(list.get(i));

                if (list.get(i).equals("("))           openingParantheses++;
                else if (list.get(i).equals(")"))      closingParantheses++;

                if (isOperation(list.get(i)) && closingParantheses == openingParantheses - 1)
                    extraSituationController = true;
            }

            derivationStack.push("(");

            if (list.get(1).equals("sin"))
                derivationStack.push("cos");
            else if (list.get(1).equals("- sin"))
                derivationStack.push("- cos");
            else if (list.get(1).equals("- cos"))
                derivationStack.push("sin");
            else 
                derivationStack.push("- sin");

            for (int i = 2; i < list.size(); i++)
                derivationStack.push(list.get(i));

            derivationStack.push("*");

            takeDerivative(leftStack , derivationStack , "left" , true , extraSituationController);

            derivationStack.push(")");
        }

        //sag ifade sin veya cos ile basliyorsa
        else if (rightOrLeft.equals("right") && (list.get(0).equals("sin") || list.get(0).equals("cos")) && 
                list.get(list.size() - 1).equals(")") && list.get(list.size() - 2).equals(")") &&
                !extraSituation) {
            
            for (int i = 1; i < list.size() - 1; i++) {

                leftStack.push(list.get(i));
        
                if (list.get(i).equals("("))           openingParantheses++;
                else if (list.get(i).equals(")"))      closingParantheses++;
        
                if (isOperation(list.get(i)) && closingParantheses == openingParantheses - 1)
                    extraSituationController = true;
            }

            derivationStack.push("(");

            if (list.get(0).equals("sin"))
                derivationStack.push("cos");
            else if (list.get(0).equals("- sin"))
                derivationStack.push("- cos");
            else if (list.get(0).equals("- cos"))
                derivationStack.push("sin");
            else 
                derivationStack.push("- sin");

            for (int i = 1; i < list.size() - 1; i++)
                derivationStack.push(list.get(i));

            derivationStack.push("*");

            takeDerivative(leftStack , derivationStack , "left" , true , extraSituationController);
        
            derivationStack.push(")");
        }

        else if (rightOrLeft.equals("left") && (list.get(1).equals("cos") || list.get(1).equals("sin")) && 
                list.size() == 3) { // ( sin a seklindeki girdiler icin

            derivationStack.push("(");
            
            if (list.get(2).equalsIgnoreCase("x")) {

                if (list.get(1).equals("sin"))
                    derivationStack.push("cos");
                else if (list.get(1).equals("- sin"))
                    derivationStack.push("- cos");
                else if (list.get(1).equals("- cos"))
                    derivationStack.push("sin");
                else 
                    derivationStack.push("- sin");

                derivationStack.push("X");
            }
            else
                derivationStack.push("0");
        }

        else if (rightOrLeft.equals("right") && (list.get(0).equals("sin") || list.get(0).equals("cos")) &&
                list.size() == 3) { // sin a ) seklinde bir ifade

            if (list.get(1).equalsIgnoreCase("x")) {

                if (list.get(0).equals("sin"))
                    derivationStack.push("cos");
                else if (list.get(0).equals("- sin"))
                    derivationStack.push("- cos");
                else if (list.get(0).equals("- cos"))
                    derivationStack.push("sin");
                else 
                    derivationStack.push("- sin");

                derivationStack.push("X");
            }
            else
                derivationStack.push("0");

            derivationStack.push(")");
        }

        else if (rightOrLeft.equals("left") && list.size() == 2) { // ( a seklinde

            derivationStack.push("(");
            
            if (list.get(1).equalsIgnoreCase("x"))
                derivationStack.push("1");
            else
                derivationStack.push("0");
        }

        else if (rightOrLeft.equals("right") && list.size() == 2) { // a ) seklinde

            if (list.get(0).equalsIgnoreCase("x"))
                derivationStack.push("1");
            else
                derivationStack.push("0");

            derivationStack.push(")");
        }

        else { // (ifade) seklinde veya daha uzun ifadeler icin
            
            for (int i = 0; i < list.size(); i++) {

                if (leftFinished)
                    rightStack.push(list.get(i));
                
                else if (isOperation(list.get(i)) && closingParantheses == openingParantheses - 1) {

                    theOperation = list.get(i);
                    leftFinished = true;
                    operationIndex = i;
                }
                else                 
                    leftStack.push(list.get(i));
            
                if (list.get(i).equals("("))         openingParantheses++;
                else if (list.get(i).equals(")"))    closingParantheses++;
            }

            if (theOperation.equals("+") || theOperation.equals("-")) {

                takeDerivative(leftStack , derivationStack , "left" , false , false);
                derivationStack.push(theOperation);
                takeDerivative(rightStack , derivationStack , "right" , false , false);
            }
            else {
                takeDerivative(leftStack , derivationStack , "left" , false , false);
                derivationStack.push("*");
                
                for (int i = 0; i < operationIndex; i++)
                    derivationStack.push(list.get(i));

                derivationStack.push("+");

                for (int i = operationIndex + 1; i < list.size(); i++)
                    derivationStack.push(list.get(i));

                takeDerivative(rightStack , derivationStack , "right" , false , false);
            }
        }
    }

    public void displayTree() {

        Iterable<Position<String>> positions = this.inorder();

        for (Position<String> pos : positions) {

            for (int i = 0; i < depth(pos); i++)
                    System.out.print(". ");
                
            System.out.println(pos.getElement());
        }
    }

    public String toString() {

        Iterable<String> strings = inorderString(); //inorderdan yola cikilan metot (624. satir)

        String str = "";

        for (String a: strings) {

            str += a + " ";
        }

        return str;
    }

    public double evaluate(int xvalue) { //ilk ) i bularak (ya kadar olan kismi ala ala islem yapar (yardimci metot 519. satir)

        String strVersion = this.toString();
        String[] strArray = strVersion.split(" ");

        LinkedStack<String> stack = new LinkedStack<>();

        for (int i = 0; i < strArray.length; i++) {

            if (strArray[i].equalsIgnoreCase("x")) 
                stack.push(xvalue + "");
            
            else if (strArray[i].equals(")"))
                evaluateHelper(stack);

            else 
                stack.push(strArray[i]);
        }

        return Double.parseDouble(stack.pop());
    }

    private void evaluateHelper(LinkedStack<String> stack) {

        int size = 0;

        LinkedStack<String> newStack = new LinkedStack<>();

        boolean sinOrCosOut = false; //parantezin disinda varsa sin veya cos varsa diye

        while (!stack.top().equals("(")) { //Stackteki ilk ")" karakterinden en yakin "("a kadar baska stacke aktarir.

            newStack.push(stack.pop());
            size++;
        }

        stack.pop(); //Burada "(" oldugunu biliyoruz. Altinda sin veya cos var mi diye bakiyorum.

        String sinOrCos = ""; //Parantez disinda sin(ifade) seklinde sin veya cos 
        
        if (!stack.isEmpty() && (stack.top().equals("sin") || stack.top().equals("cos"))) {

            sinOrCosOut = true;
            sinOrCos = stack.pop();
        }

        String first = newStack.pop();
        String second = newStack.pop();
        String third = newStack.pop();

        if (sinOrCosOut) { //Parantezlerin basinda bir cos veya sin ifadesi varsa

            if (size == 3)  //sin (a + b) gibi bir ifade
                stack.push(doOperation1(sinOrCos , doOperation2(first , second , third) + "") + ""); 

            else if (size == 4) { // sin(sin x + y) veya sin(y + sin x) seklinde
 
                if (first.equals("sin") || first.equals("cos"))
                    stack.push(doOperation1(sinOrCos , doOperation2(doOperation1(first , second) + "" , third , newStack.pop()) + "") + "");

                else //sin (x + sina ) seklinde
                    stack.push(doOperation1(sinOrCos , doOperation2(first , second , doOperation1(third , newStack.pop() + "") + "") + "") + "");
            }
            
            else //sin( sin x - cos b ) seklinde
                stack.push(doOperation1(sinOrCos , doOperation2(doOperation1(first , second) + "" , third , doOperation1(newStack.pop() , newStack.pop()) + "") + "") + "");
        }
        else {

            if (size == 3) //a + b seklinde
                stack.push(doOperation2(first , second , third) + "");

            else if (size == 4) { // sin x + y veya y + sin x seklinde
 
                if (first.equals("sin") || first.equals("cos")) { //sin x + b seklinde

                    stack.push(doOperation2(doOperation1(first , second) + "", third , newStack.pop()) + "");
                }
                else { //a + sin b seklinde

                    stack.push(doOperation2(first , second , doOperation1(third , newStack.pop()) + "") + "");
                }
            }
            else  //sin x - cos b seklinde
                stack.push(doOperation2(doOperation1(first , second) + "", third , doOperation1(newStack.pop() , newStack.pop()) + "") + "");
        }
    }
    
    private double doOperation1(String cosOrSin , String num) { //sin x gibi ifadeler icin

        if (cosOrSin.equals("sin"))
            return Math.sin(Double.parseDouble(num));
        return Math.cos(Double.parseDouble(num));
    }

    private double doOperation2(String firstNum , String op , String secondNum) { //a + b gibi ifadeler icin

        if (op.equals("+"))
            return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
        else if (op.equals("-"))
            return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
        return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
    }

    private void inorderHelper(Position<String> p, ArrayList<Position<String>> snapshot, ArrayList<String> elements) {

        if (left(p) != null && right(p) != null) //ikinci kisim yoktu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            elements.add(elements.size() , "(");

        if (p.getElement().equals("sin") || p.getElement().equals("cos")) {
            snapshot.add(snapshot.size() , p);
            elements.add(elements.size() , p.getElement());
        }
            
        if (left(p) != null)
            inorderHelper(left(p), snapshot , elements);
        if (!p.getElement().equals("sin") && !p.getElement().equals("cos")) {
            snapshot.add(snapshot.size() , p);
            elements.add(elements.size() , p.getElement());
        }
        if (right(p) != null) {
            inorderHelper(right(p), snapshot , elements);

            elements.add(elements.size() , ")");
        }
    }

    public Iterable<String> inorderString() {

        ArrayList<Position<String>> snapshot = new ArrayList<>();
        ArrayList<String> elements = new ArrayList<>();
        if (!isEmpty())
            inorderHelper(root(), snapshot , elements);   // fill the snapshot recursively
        return elements;
    }

    private boolean isOperation(String str) { //+ , - veya * ise true dondurur.
        return str.equals("+") || str.equals("-") || str.equals("*");
    }

}