import java.io.*;
import java.util.Scanner;

public class DictionaryDriver {
    public static void main(String[] args) {

        Scanner dictionary = null;

        try { //dictionary.txt okundu

            dictionary = new Scanner(new File("dictionary.txt"));

        } catch (FileNotFoundException exception) {

            System.out.println(exception.getMessage());
            System.exit(0);
        }
        
        ProbeHashMap<String,Integer> dictionaryMap = new ProbeHashMap<>();

        String word = "";
        int valueForWord = 0; //her loopta 1 artacak
        int numOfWords = 0;
        int maximumProbe = 0;
        double averageProbe = 0; //toplam probu buluyor once en sonda kelime sayisina boluyorum

        while (dictionary.hasNext()) { //her kelimeyi key olarak mapime yerlestirdim

            word = dictionary.next();
            numOfWords++;

            dictionaryMap.put(word , valueForWord); //dictionary'deki yerine koydum

            valueForWord++;

            //getProbe() metodu AbstractHashMap'in 20. satirinda ve helper metodu ProbeHashMap'in 10. satirinda
            int probeOfWord = dictionaryMap.getProbe(word); //probe'unu hesapladim
            averageProbe += probeOfWord; //averageProb kelime sayisina bolene kadar tum kelimelerin toplam probe'u
            maximumProbe = Math.max(maximumProbe , probeOfWord);
        }

        dictionary.close();

        double loadFactor = dictionaryMap.getLoadFactor(); //bu metot AbstractHashMap'in 13. satirinda

        averageProbe = averageProbe / numOfWords;

        System.out.println(averageProbe);
        System.out.println(maximumProbe);
        System.out.println(loadFactor);
        System.out.println();

        //search.txt'yi islemeye basliyorum.

        Scanner search = null;

        try { //search.txt okundu

            search = new Scanner(new File("search.txt"));

        } catch (FileNotFoundException exception) {

            System.out.println(exception.getMessage());
            System.exit(0);
        }

        word = "";
        averageProbe = 0; //sadece bulunabilen kelimeler icin
        maximumProbe = 0;
        numOfWords = 0;

        while (search.hasNext()) {

            word = search.next(); //search.txt dosyasindaki yanlis olabilecek kelimeler icin variable
            ArrayList<String> possibleWords = new ArrayList<>();
            String lowerCaseWord = ""; //key'in hem kucuk harfli hem de harflerinin yer degistirilmis halleri map'te varsa bu variable'la kontrol ediyorum.

            if (dictionaryMap.get(word) != null) { //bu kelime mapte bulunuyorsa search.txt'deki diger kelimeye gecer
                
                averageProbe += 1; //direkt buldugumuz icin
                numOfWords++;
                maximumProbe = Math.max(dictionaryMap.getProbe(word) , maximumProbe);
                continue;
            }

            if (dictionaryMap.get(word.toLowerCase()) != null) { //kelimenin kucuk harfli versiyonu map'te varsa
                
                lowerCaseWord = word.toLowerCase();
                possibleWords.add(possibleWords.size() , lowerCaseWord); //bu versiyonu olasi kelimelere eklerim
                numOfWords++;
                averageProbe += dictionaryMap.getProbe(lowerCaseWord);
                maximumProbe = Math.max(dictionaryMap.getProbe(lowerCaseWord) , maximumProbe);
            }

            for (int i = 0; i < word.length() - 1; i++) { //n harfli bir kelimede 3 tane art arda olan harf cifti var 
            //Her bir kelimenin tum art arda harflerini degistirerek kelime olusturur
                String wordAdjusted = "";

                if (i == 0) { //ilk indexteyse 

                    if (word.length() > 2) //ancak kelimenin harf sayisi 2'den fazlaysa ilk ikiyi degistirip kalani substring'le alabilir
                            wordAdjusted = word.charAt(1) + "" + word.charAt(0) + word.substring(2, word.length());
                    else //harf sayisi 2 ise sadece iki karakterin yerlerini degistiririm
                        wordAdjusted = word.charAt(1) + "" + word.charAt(0);                       
                }

                else if (i == word.length() - 2) //bakilacak son indexteysek sonda substring almam
                    wordAdjusted = word.substring(0 , i) + word.charAt(i + 1) + word.charAt(i);

                else //bastan ve sondan substring alip aradaki (indexe gore) harflerin yerlerini degistiririm.
                    wordAdjusted = word.substring(0 , i) + word.charAt(i + 1) + "" + word.charAt(i) + word.substring(i + 2 , word.length());

                Integer value = dictionaryMap.get(wordAdjusted.toLowerCase()); //her olasi kelimenin value'sunu dondum

                if (value == null) { //boyle bir kelime map'imde yoksa probe'unu ekleyip diger kelimeye gecerim
                    
                    averageProbe += dictionaryMap.getProbe(wordAdjusted.toLowerCase());
                    numOfWords++;
                    maximumProbe = Math.max(maximumProbe , dictionaryMap.getProbe(wordAdjusted.toLowerCase()));
                    continue;
                }

                else { //map'imde boyle bir kelime varsa
                    
                    if (lowerCaseWord.equals(wordAdjusted.toLowerCase())) //ornek: Aadm kelimesinin dogrusunu aadm diye 2 kere almamasi icin
                        continue;

                    maximumProbe = Math.max(maximumProbe , dictionaryMap.getProbe(wordAdjusted.toLowerCase()));
                    averageProbe += dictionaryMap.getProbe(wordAdjusted.toLowerCase());
                    numOfWords++;

                    possibleWords.add(possibleWords.size() , wordAdjusted.toLowerCase()); //olasi dogru kelimelere eklerim
                }
            }

            System.out.println(word + ": " + possibleWords); //ArrayList toString metodu araya virgul koyacak sekilde
        }  

        search.close();

        averageProbe = averageProbe / numOfWords;

        System.out.println("\n" + averageProbe);
        System.out.println(maximumProbe);
    }
}