import java.util.Scanner;
import java.io.*;

public class DNADatabase {

    public static void main(String[] args) {

        String fileName = args[0];
        Scanner directiveFile = null;

        DNATreeExtended<String> DNATree = new DNATreeExtended<>();

        try {

            directiveFile = new Scanner(new File(fileName));

        } catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
            System.exit(0);
        }

        String line = "";

        while (directiveFile.hasNextLine()) {

            line = directiveFile.nextLine();

            String[] directive = line.split(" ");

            if (directive[0].equals("insert")) {

                DNATree.insert(directive[1]);
                System.out.println();
            }

            else if (directive[0].equals("remove")) {

                DNATree.remove(directive[1]);
                System.out.println();;
            }

            else if (directive[0].equals("display")) {

                DNATree.display();
                System.out.println();
            }

            else if (directive[0].equals("display-lengths")) {

                DNATree.displayLengths();
                System.out.println();
            }

            else if (directive[0].equals("search")) {

                DNATree.search(directive[1]);
                System.out.println();
            }
        }

        directiveFile.close();
    }
}