package zadanie2;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Commander {

    public static void main(String args[]) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String pathName = null;
        while (true) {
            System.out.println("Podaj �cie�k� do interesuj�cego Ci� folderu: ");
            pathName = scanner.nextLine();
            if (doesDirectoryExist(pathName)) {
                break;
            } else {
                System.out.println("Folder nie istnieje, spr�buj jeszcze raz.");
            }
        }

        System.out.println("Wybierz jedn� z poni�szych opcji: \n" +
                "1 - listowanie plik�w proste \n" +
                "2 - listowanie plik�w szczeg�owe \n" +
                "3 - listowanie plik�w z rozszerzeniem \n" +
                "4 - wy�wietlanie drzewa katalog�w \n");

        while (true) {
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("PROSTA LISTA PLIK�W:");
                    printFilesSimple(pathName);
                    return;
                case 2:
                    System.out.println("LISTA PLIK�W ZE SZCZEGӣAMI:");
                    printFilesDetails(pathName);
                    return;
                case 3:
                    System.out.println("Podaj rozszerzenie np. .txt");
                    String extension = scanner.next();
                    System.out.println("LISTA PLIK�W Z ROZSZERZENIEM: " + extension);
                    printFiles(pathName, extension);
                    return;
                case 4:
                    System.out.println("DRZEWO KATALOG�W:");
                    printTree(pathName);
                    return;
                default:
                    System.out.println("Niepoprawny numer opcji, spr�buj ponownie");
                    break;
            }
        }


    }

    private static void printFilesSimple(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            System.out.println(file.getName());
        }
    }

    private static void printFilesDetails(String path) throws Exception {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            Path filePath = Paths.get(file.getAbsolutePath());
            BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            String line = String.format("%-30s", file.getName());
            if (basicFileAttributes.isDirectory()) {
                line += String.format("%-20s", "DIR");
            } else {
                line += String.format("%-20s", basicFileAttributes.size());
            }
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd mm:ss");
            line += df.format(basicFileAttributes.creationTime().toMillis());

            System.out.println(line);
        }


    }

    private static void printFiles(String path, String extensionFilter) {
        File folder = new File(path);

        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extensionFilter);
            }
        };

        File[] listOfFiles = folder.listFiles(filter);

        for (File file : listOfFiles) {
            System.out.println(file.getName());
        }
    }

    private static void printTree(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            System.out.println(path + "/" + file.getName());
            if (file.isDirectory()) {
                printTree(path + "/" + file.getName());
            }

        }
    }

    private static boolean doesDirectoryExist(String pathName) {
        File folder = new File(pathName);
        return folder.isDirectory();
    }

}
