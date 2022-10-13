package ergasia2;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

public class Ergasia2 {

    public static void main(String[] args) throws IOException {

        //Kaloume tin methodo Menu stin main gia na ksekinisei na doulevei oli i efarmogi
        Menu();

    }

    //Ftiaxnoume tin methodo Menu gia na mporesei o xristis na dialeksei ti na kanei
    public static void Menu() throws IOException {

        Scanner input = new Scanner(System.in);
        //Xrisimopoioume try catch block se periptosi pou o xristis dwsei xaraktira anti gia arithmo
        //Afto voithaei sto na min krasarei i efarmogi apo to lathos tou xristi
        try {
            System.out.println("\nChoose:");
            System.out.println("1)Read CSV file and store it in memory");
            System.out.println("2)Sort the names alphabetically and write them in a .txt file");
            System.out.println("3)Check the relations");
            System.out.println("4)Create the .dot file for the Graph");
            System.out.println("5)Exit from program");
            System.out.print("Input: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n***STORING CSV FILE IN THE MEMORY***\n");
                    Input.read_CSV();
                    Menu();
                    break;
                case 2:
                    Sort.alphabetically();
                    Output.writeNames();
                    Menu();
                    break;
                case 3:
                    Search.getName();
                    Input.setName();
                    System.out.println("\n***START OF RELATIONS***\n");
                    Search.father_mother();
                    Search.son_daughter();
                    Search.brother_sister();
                    Search.cousins();
                    Search.married();
                    Search.grandfather_grandmother();
                    Search.grandson_granddaughter();
                    Search.nephew_niece();
                    Search.uncle_aunt();
                    Search.notRelated();
                    Output.closeGraphVizFile();
                    System.out.println("\n***END OF RELATIONS***");
                    Menu();
                    break;
                case 4:
                    System.out.println("\n***CREATING THE GRAPH***\n");
                    Output.writeResult();
                    Output.create();
                    Menu();
                    break;
                case 5:
                    Exit();
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid choice, please try again!");
                    Menu();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println();
            System.out.println("Invalid choice, Please input a number from 1 to 5!!!");
            Menu();
        }
    }

    //Ftiaxnoume mia methodo Exit opou o xristeis apofasizei an thelei na vgei apo tin efarmogi i na sunexisei
    public static void Exit() throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to exit? (Y/N): ");
        char YN = input.next().toUpperCase().charAt(0);

        switch (YN) {
            //Stin periptosi pou dwsei Y kanoume print ena minima kai vgainei apo to programa
            case 'Y':
                System.out.println("Byee!");
                //methodos tis java gia na kanei exit apo to programma
                System.exit(0);
                break;
            //Stin periptosi pou dwsei N kaloume tin methodo Menu kai palli ola apo tin arxi
            case 'N':
                Menu();
                break;
            //An den dwsei tipota apo ta duo kaloume pali tin methodo exit gia na dwsei mia ekgiri timi
            default:
                System.out.println("Invalid choice, please try again!!");
                Exit();
        }
    }

    //Duo String metavlites gia na apothikefsoume to path tou csv kai tou dot
    public static String csvFile;
    public static String dotFile;
    //Ftiaxnoume mia LinkedList opou dexete Strings me onoma list opou tha apothikefsoume oli tin lista tou csv arxeiou
    public static LinkedList<String> list = new LinkedList<String>();
    //Ftiaxnoume mia LinkedList gia na apothikefsoume mono ta onomata
    public static LinkedList<String> nameList = new LinkedList<String>();
    //Ftiaxnoume mia LinkedList gia na apothikevoume onoma kai fulo
    public static LinkedList<String> genderNameList = new LinkedList<String>();
    //Ftiaxnoume enan pinaka me 2 theseis gia na apothiekftoun ta onomata pou edwse o xristis
    public static String[] inputedName = new String[2];
    // Olles oi doinates sisxetiseis se enan pinaka me Strings resultArr
    //Vazoume to aseniko fulo prwto kai to thiliko deftero se olla gia na mporesei na doulepsei to programa swsta simfwna me tin logiki pou trexei gia to fulo
    public static String[] resultArr = {"father,mother", "son,daughter", "brother,sister", "cousin,cousin", "husband,wife", "grandfather,grandmother", "grandson,granddaugther", "nephew,niece", "uncle,aunt"};
    //Mia metavliti boolean gia na doume an exoun sxesei ta onomata
    public static boolean related = false;
    //Mia boolean metavliti gia na doume an to arxeio .dot exei anoiksei
    public static boolean openedFile = false;
    //Xreiazomaste 3 komvous gia ton grafo
    public static int node = 3;

    //Ftiaxnoume tin klasi input
    public static class Input {

        //Ftiaxnoume tin methodo opou tha diavasei to csv file
        public static void read_CSV() throws IOException {

            Scanner input = new Scanner(System.in);
            //Tha mpei olo se ena try catch block epeidi mporei na valei lanthasmeno path o xristis
            try {
                //Tha zitisoume apo ton xristi na dwsei to path tou csv file kai tha apothikefthei se ena String
                System.out.print("Input the path of the CSV file and its name(.csv): ");
                csvFile = input.nextLine();
                //String csvFile = "C:\\Users\\bicik\\Documents\\NetBeansProjects\\ergasia2\\dentro.csv";

                BufferedReader br = new BufferedReader(new FileReader(csvFile));
                String str;

                //Oso uparxoun dedomena na apothikevontai stin LinkedList me tin methodo add
                while ((str = br.readLine()) != null) {
                    Ergasia2.list.add(str);
                }
                System.out.println("");
                System.out.println("CSV file stored in the memory successfully!!");

            } catch (FileNotFoundException e) { //Tha kanoume catch afto to exception kai tha petaksoume ena minima ston xristi oti den uparxei to file pou edwse
                System.out.println("File not found!");
            }
        }

        //Ftiaxnoume mia methodo opou o xristis tha dwsei duo onomata
        public static void setName() {

            Scanner input = new Scanner(System.in);
            System.out.print("Input first name: ");
            inputedName[0] = input.nextLine();

            //Me while loop elegxoume to onoma pou edwse
            while (checkNames(inputedName[0]) == false) {
                System.out.println("The name does not exists, Please try again!");
                System.out.print("Input the first name: ");
                inputedName[0] = input.nextLine();
            }

            System.out.print("Input second name: ");
            inputedName[1] = input.nextLine();

            while (checkNames(inputedName[1]) == false) {
                System.out.println("The name does not exists, Please try again!");
                System.out.print("Input the second name: ");
                inputedName[1] = input.nextLine();
            }
            //kaloume edw tin methodo afou o xristis exei kanei kai ta 2 input
            checkSameInput();

        }

        //Ftiaxnoume mia methodo gia na eleksoume an uparxei to onoma sto csv arxeio
        public static boolean checkNames(String name) {
            //Gia kathe onoma sto nameList vlepoume an einai iso me to onoma pou evale o xristis
            for (String str : nameList) {
                if (str.equals(name)) {
                    return true;
                }
            }
            return false;
        }

        //Ftiaxnoume mia methodo pou tha tsekarei an o xristis edwse to idio onoma
        public static void checkSameInput() {
            //An to if dwsei true tote petame ena minima ston xristi kai ksanakaloume tin methodo setName gia na ksanadwsei alla onomata
            if (inputedName[0].equals(inputedName[1])) {
                System.out.println();
                System.out.println("Please input different names!!");
                Input.setName();
            }
        }

    }

    //Ftiaxnoume mia klasi Sort stin opoia tha exei mesa tin methodo alphabetically
    public static class Sort {

        //Me aftin tin methodo tha taksinomisoume se leksografiki seira ta onomata mazi me to fulo
        public static void alphabetically() {

            for (String str : list) {
                if (str.contains(",male") || str.contains(",female")) {
                    String[] arr = str.split(",");
                    genderNameList.add(arr[0] + ", " + arr[1]);
                }
            }
            Collections.sort(genderNameList);
        }

    }

    //Ftiaxnoume mia klasi Search opou tha exei olles tis methodous me tis sxeseis 
    public static class Search {

        //Ftiaxnoume tin methodo getName gia na apothikefsoume se mia lista mono ta onomata kai na ta emfanisoume ston xristi gia na dialeksei
        public static void getName() {
            nameList.clear(); //Kanoume ena clear sto nameList stin periptosi pou o xristis dialeksei palli to 3, gia na min apothikevei parapanw apo mia fwra ta idia onomata
            for (String str : list) {
                if (str.contains(",male") || str.contains(",female")) {
                    String[] arr = str.split(",");
                    nameList.add(arr[0]);
                }
            }
            Collections.sort(nameList);
            System.out.println("\n" + nameList);
        }

        //Ftiaxnoume mia methodo opou tha tsekari to fulo tou onomatos pou o xristis edwse
        public static boolean checkGender(String name) {

            for (String str : list) {
                if (str.contains(name + ",male")) {
                    return true;
                }
            }
            return false;
        }

        //Ftiaxnoume me tin seira tous methodous me tis sxeseis tou oikogeniakou dentrou
        public static void father_mother() throws IOException {
            related = false; //Arxizoume me related = false epeidi mporei na einai true stin periptosi pou o xristis dialeksei pali to 3 gia na dei tis sxeseis tou dentrou
            //Tsekaroume an ta duo onomata exoun anamesa tous to father i mother sto csv arxeio
            for (String str : list) {
                if (str.trim().contains(inputedName[0]) && str.trim().contains("father") && str.trim().contains(inputedName[1])) {
                    String arr[] = str.split(",", 3);
                    Output.printResults(arr[0], arr[2], 0);
                    related = true;
                    break;
                } else if (str.trim().contains(inputedName[0]) && str.trim().contains("mother") && str.trim().contains(inputedName[1])) {
                    String arr[] = str.split(",", 3);
                    Output.printResults(arr[0], arr[2], 0);
                    related = true;
                    break;
                }
            }
        }

        public static void son_daughter() throws IOException {
            //Tsekaroume an ta duo onomata exoun anamesa tous to father i mother sto csv arxeio, an nai tote ta vazoume se alli sira kai leme oti einia o gios i i kori
            for (String str : list) {
                if (str.trim().contains(inputedName[0]) && str.trim().contains("father") && str.trim().contains(inputedName[1])) {
                    String arr[] = str.split(",", 3);
                    //Vazoume to arr[2] prwto sto opoio einai i triti thesi sto csv file gia na vgazei noima i prwtasi 
                    Output.printResults(arr[2], arr[0], 1);
                    related = true;
                    break;
                } else if (str.trim().contains(inputedName[0]) && str.trim().contains("mother") && str.trim().contains(inputedName[1])) {
                    String arr[] = str.split(",", 3);
                    Output.printResults(arr[2], arr[0], 1);
                    related = true;
                    break;
                }
            }
        }

        public static void brother_sister() throws IOException {
            //Olo afto tha mpei se ena try catch epeidi o pinakas parents mporei na lavei timi null apo tin stigmi pou ena paidi mporei na min exei enan gonio
            try {
                int i = 0;
                int k = 0;
                //Tha ftiaksoume 2 arrays gia na apothikefsoume tous goneis tou protou kai tou defterou
                //Tha exoun 2 theseis, ena gia ton patera kai 1 gia tin mitera 
                String[] parentsFirst = new String[2];
                String[] parentsSecond = new String[2];
                //Tha diavasoume olo to csv arxeio 
                for (String str : list) {
                    //Tha doume an to csv periexei father, i mother, kai to onoma pou edwse o xristis
                    if (str.contains("father," + inputedName[0]) || str.contains("mother," + inputedName[0])) {
                        //An uparxoun tote se aftin tin grammi pou to vrikame tha to apothikefsoume se ena arr me 3 thesis kai tha to xwrisoume opou exei koma
                        String[] arr = str.split(",", 3);
                        //Tha apothikefsoume tin prwti thesi tis grammis dioti ekei vriskete to onoma tou patera i tis miteras
                        parentsFirst[i] = arr[0];
                        i++;
                    } //To idio tha simvei kai gia to deftero onoma pou edwse o xristis
                    else if (str.contains("father," + inputedName[1]) || str.contains("mother," + inputedName[1])) {
                        String[] arr = str.split(",", 3);
                        parentsSecond[k] = arr[0];
                        k++;
                    }
                }
                //Edw tha tsekaroume an oi goneis to prwtou einai idioi me tous goneis tou defterou
                if (parentsFirst[0].equals(parentsSecond[0]) || parentsFirst[1].equals(parentsSecond[1]) || parentsFirst[0].equals(parentsSecond[1]) || parentsFirst[1].equals(parentsSecond[0])) {
                    Output.printResults(inputedName[0], inputedName[1], 2);
                    related = true;
                }
            } //Prepei na kanoume catch to NullPointerException epeidi den theloume na krasarei to programa otan ginei mia sigkrisei me tin timi null
            catch (NullPointerException e) {
                //Den vazoume tipota edw mesa epeidi den theloume na kanei kati to programa otan piasei to null para mono na doulepsei
            }

        }

        public static void cousins() throws IOException {

            try {
                int i = 0;
                int k = 0;
                String[] parentsFirst = new String[2];
                String[] parentsSecond = new String[2];

                //Tsekaroume me ton idio tropo tous goneis ton duo input
                for (String str : list) {
                    if (str.contains("father," + inputedName[0]) || str.contains("mother," + inputedName[0])) {
                        String[] arr = str.split(",", 3);
                        parentsFirst[i] = arr[0];
                        i++;
                    } else if (str.contains("father," + inputedName[1]) || str.contains("mother," + inputedName[1])) {
                        String[] arr = str.split(",", 3);
                        parentsSecond[k] = arr[0];
                        k++;
                    }
                }

                i = 0;
                k = 0;
                //Ftiaxnou 4 arrays apo 2 thesis gia na apothikefsoume tous goneis tou kathe goniou
                String[] parentsFirstF = new String[2]; //Oi goneis tou patera toy prwtou input
                String[] parentsFirstM = new String[2]; //Oi goneis tis miteras toy prwtou input
                String[] parentsSecondF = new String[2]; //Oi goneis tou patera tou defterou input
                String[] parentsSecondM = new String[2]; //Oi goneis tis miteras tou defterou input

                //Vriskoume tous goneis ton gonion ton input pou exei kanei o xristis
                for (String str : list) {
                    //apothikevoume tous goneis tou patera tou prwtou input
                    if (str.contains("father," + parentsFirst[0]) || str.contains("mother," + parentsFirst[0])) {
                        String[] arr = str.split(",", 3);
                        parentsFirstF[i] = arr[0];
                        i++;
                        //apothikevoume tous goneis tis miteras toy protu xristi
                    } else if (str.contains("father," + parentsFirst[1]) || str.contains("mother," + parentsFirst[1])) {
                        String[] arr = str.split(",", 3);
                        parentsFirstM[k] = arr[0];
                        k++;
                    }

                    i = 0;
                    k = 0;

                    //apothikevoume tous goneis tou patera tou defterou input
                    if (str.contains("father," + parentsSecond[0]) || str.contains("mother," + parentsSecond[0])) {
                        String[] arr = str.split(",", 3);
                        parentsSecondF[i] = arr[0];
                        i++;
                        //apothikevoume tous goneis tis miteras tou defterou input
                    } else if (str.contains("father," + parentsSecond[0]) || str.contains("mother," + parentsSecond[0])) {
                        String[] arr = str.split(",", 3);
                        parentsSecondM[k] = arr[0];
                        k++;
                    }
                }

                //tsekaroume an o pappous tou prwtou input einai idios me ton papou tou defterou input
                if (parentsFirstF[0].equals(parentsSecondF[0]) || parentsFirstF[1].equals(parentsSecondF[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 3);
                    related = true;
                    //tsekaroume an i giagia tou prwtou input einai idia me tin giagia tou defterou input
                } else if (parentsFirstM[0].equals(parentsSecondM[0]) || parentsFirstM[1].equals(parentsSecondM[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 3);
                    related = true;
                }

            } catch (NullPointerException e) {
            }

        }

        public static void married() throws IOException {
            //Tsekaroume an ta duo onomata pou edwse o xristis exoun anamesa tous to married sto csv arxeio
            for (String str : list) {
                if (str.trim().contains(inputedName[0]) && str.trim().contains("married") && str.trim().contains(inputedName[1])) {
                    String arr[] = str.split(",", 3);
                    Output.printResults(inputedName[0], inputedName[1], 4);
                    related = true;
                    break;
                }
            }
        }

        public static void grandfather_grandmother() throws IOException {

            try {
                int i = 0;
                String[] parentsFirst = new String[2];

                //Tha apothikefsoume tous goneis mono tou defterou input epeidi to prwto input tha einai i giagia i o papous
                for (String str : list) {
                    if (str.contains("father," + inputedName[1]) || str.contains("mother," + inputedName[1])) {
                        String[] arr = str.split(",", 3);
                        parentsFirst[i] = arr[0];
                        i++;
                    }
                }

                //Tha tsekaroume an enas apo tous goneis tou defterou input einai paidi tou prwtou input
                for (String str : list) {
                    if (str.contains(inputedName[0] + ",father," + parentsFirst[0]) || str.contains(inputedName[0] + ",mother," + parentsFirst[0])) {
                        Output.printResults(inputedName[0], inputedName[1], 5);
                        related = true;
                        break;
                    } else if (str.contains(inputedName[0] + ",father," + parentsFirst[1]) || str.contains(inputedName[0] + ",mother," + parentsFirst[1])) {
                        Output.printResults(inputedName[0], inputedName[1], 5);
                        related = true;
                        break;
                    }
                }

            } catch (NullPointerException e) {
            }

        }

        public static void grandson_granddaughter() throws IOException {

            try {
                int i = 0;
                String[] parentsFirst = new String[2];

                //Tha apothikefsoume tous goneis mono tou prwtou input epeidi to deftero input tha einai i giagia i o papous
                for (String str : list) {
                    if (str.contains("father," + inputedName[0]) || str.contains("mother," + inputedName[0])) {
                        String[] arr = str.split(",", 3);
                        parentsFirst[i] = arr[0];
                        i++;
                    }
                }

                //Tha tsekaroume an enas apo tous goneis tou protou input einai paidi tou defterou input
                for (String str : list) {
                    if (str.contains(inputedName[1] + ",father," + parentsFirst[0]) || str.contains(inputedName[1] + ",mother," + parentsFirst[0])) {
                        Output.printResults(inputedName[0], inputedName[1], 6);
                        related = true;
                        break;
                    } else if (str.contains(inputedName[1] + ",father," + parentsFirst[1]) || str.contains(inputedName[1] + ",mother," + parentsFirst[1])) {
                        Output.printResults(inputedName[0], inputedName[1], 6);
                        related = true;
                        break;
                    }
                }

            } catch (NullPointerException e) {
            }

        }

        public static void nephew_niece() throws IOException {

            try {
                int i = 0;
                int k = 0;
                String[] parentsFirst = new String[2];
                String[] parentsSecond = new String[2];
                //Vriskoume tous goneis tou prwtou input kai tou defterou
                for (String str : list) {
                    if (str.contains("father," + inputedName[0]) || str.contains("mother," + inputedName[0])) {
                        String[] arr = str.split(",", 3);
                        parentsFirst[i] = arr[0];
                        i++;
                    } else if (str.contains("father," + inputedName[1]) || str.contains("mother," + inputedName[1])) {
                        String[] arr = str.split(",", 3);
                        parentsSecond[k] = arr[0];
                        k++;
                    }
                }

                i = 0;
                k = 0;
                String[] parents_of_father = new String[2];
                String[] parents_of_mother = new String[2];
                //Vriskoume tous goneis tou goniou tou prwtou input apo tin stigmi pou tha einai o anipsios
                for (String str : list) {
                    if (str.contains("father," + parentsFirst[0]) || str.contains("mother," + parentsFirst[0])) {
                        String[] arr = str.split(",", 3);
                        parents_of_father[i] = arr[0];
                        i++;
                    } else if (str.contains("father," + parentsFirst[1]) || str.contains("mother," + parentsFirst[1])) {
                        String[] arr = str.split(",", 3);
                        parents_of_mother[k] = arr[0];
                        k++;
                    }
                }
                //Tsekaroume an oi goneis twn gwniwn tou prwtou einai idioi me tous goneis tou defterou
                if (parents_of_father[0].equals(parentsSecond[0]) || parents_of_father[1].equals(parentsSecond[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 7);
                    related = true;
                } else if (parents_of_mother[0].equals(parentsSecond[0]) || parents_of_mother[1].equals(parentsSecond[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 7);
                    related = true;
                }

            } catch (NullPointerException e) {
            }

        }

        public static void uncle_aunt() throws IOException {

            try {
                int i = 0;
                int k = 0;
                String[] parentsFirst = new String[2];
                String[] parentsSecond = new String[2];
                //Vriskoume tous goneis kai ton dyo
                for (String str : list) {
                    if (str.contains("father," + inputedName[0]) || str.contains("mother," + inputedName[0])) {
                        String[] arr = str.split(",", 3);
                        parentsFirst[i] = arr[0];
                        i++;
                    } else if (str.contains("father," + inputedName[1]) || str.contains("mother," + inputedName[1])) {
                        String[] arr = str.split(",", 3);
                        parentsSecond[k] = arr[0];
                        k++;
                    }
                }

                i = 0;
                k = 0;
                String[] parents_of_father = new String[2];
                String[] parents_of_mother = new String[2];
                //Vriskoume tous goneis twn gwniwn tou defterou input o opoios tha einia o eggonos
                for (String str : list) {
                    if (str.contains("father," + parentsSecond[0]) || str.contains("mother," + parentsSecond[0])) {
                        String[] arr = str.split(",", 3);
                        parents_of_father[i] = arr[0];
                        i++;
                    } else if (str.contains("father," + parentsSecond[1]) || str.contains("mother," + parentsSecond[1])) {
                        String[] arr = str.split(",", 3);
                        parents_of_mother[k] = arr[0];
                        k++;
                    }
                }
                //Tsekaroume an oi goneis tou prwtou input o opoios einai o thios, einai idioi me tous goneis toy gwnea toy defterou input
                if (parents_of_father[0].equals(parentsFirst[0]) || parents_of_father[1].equals(parentsFirst[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 8);
                    related = true;
                } else if (parents_of_mother[0].equals(parentsFirst[0]) || parents_of_mother[1].equals(parentsFirst[1])) {
                    Output.printResults(inputedName[0], inputedName[1], 8);
                    related = true;
                }

            } catch (NullPointerException e) {
            }

        }

        public static void notRelated() throws IOException {

            if (related == true) {
                System.out.println("They are related!");
            } else {
                System.out.println("They are not related!");
            }
        }
    }

    //Fftiaxnoume aftin tin klasi gia na kanoume output oti xreiazomaste me diaforetikous methodous
    public static class Output {

        //Ftiaxnoume aftin tin methodo gia na ftiaksoume ena txt file opou exei mesa ta onomata me to fulo taksinomimena
        public static void writeNames() throws FileNotFoundException, UnsupportedEncodingException, IOException {
            
            Scanner input = new Scanner(System.in);
            try{
            System.out.print("\nInput the path where you want to save it and the name of txt (C:/Documents/nameSorted.txt): ");
            String txtPath = input.nextLine();
            
            PrintWriter writer = new PrintWriter(txtPath, "UTF-8");
            for (String str : genderNameList) {
                writer.println(str);
            }
            writer.close();
            System.out.println();
            System.out.println("nameSorted.txt was created successfully! Check it in the folder!");
            } catch(FileNotFoundException e) {
                 System.out.println("\nPath does not exist, Try again!!!");
                //Tha ksanatreksoume aftin tin methodo gia na valei to swsto path
                writeNames();
            }
        }

        public static void writeResult() throws IOException {

            Scanner input = new Scanner(System.in);
            BufferedReader br = null;
            BufferedWriter bw = null;
            String line;
            String color = "";
            char icon = '\"';

            try {
                System.out.println("Input path to save the dot and the name like (C:/users/name/Desktop/dotCode) ");
                System.out.print("Input: ");
                dotFile = input.nextLine();

                bw = new BufferedWriter(new FileWriter(dotFile + ".dot"));
                br = new BufferedReader(new FileReader(csvFile));
                //Afou exei ftiaxtei o file writer tote tha kalesoume aftin tin methodo gia na gemisoume to dot file me tis prwtes grammes opou periexei kai ton pinaka
                WriteFirst(bw, icon);

                while ((line = br.readLine()) != null) {
                    String[] elem = line.split(",");

                    //An to megethos tou pinaka elem einai 3 tote exoume na kanoume me duo nodes pou exoun mia sxesi metaksi tous
                    if (elem.length == 3) {
                        //apothikevoume sto color afto pou tha mas kanei return i methodos CheckColor()
                        color = CheckColor(elem, color);

                        //edw tha kanoume replace ola ta kaina meroi me \n xrisimopoiontas to Matcher.quoteReplacement to opoio to vazei ws keimeno 
                        elem[0] = elem[0].replaceAll(" ", Matcher.quoteReplacement("\\n"));
                        elem[2] = elem[2].replaceAll(" ", Matcher.quoteReplacement("\\n"));

                        //Apothikevoume to keimeno se ena variable gia na mporesoume na to xrisimopoiisoume sto write()
                        String values = icon + elem[0] + icon + "->" + icon + elem[2] + icon + color + "\n";
                        bw.write(values);
                        //Tha tsekaroume an einai married gia na ta valoume sto idio rank kalontas tin methodo CheckRank()
                        CheckRank(elem, bw, icon);
                        //An to megethos tou pinaka einai 2 tote exoume na kanoume me to fulo opote kaloume tin methodo CheckGender()
                    } else if (elem.length == 2) {
                        elem[0] = elem[0].replaceAll(" ", Matcher.quoteReplacement("\\n"));
                        CheckGender(elem, bw, icon);
                    }
                }
                bw.write("}");

                //Afou ola einai ok kanoume print afto to minima
                System.out.println("\nThe dot file created successfully!!\n");

            } catch (FileNotFoundException e) { //An o xristis valei path pou den uparxei tha to kanoume catch gia na tou petaksoume ena minima
                System.out.println("\nPath does not exist, Try again!!!\n");
                //Tha ksanatreksoume aftin tin methodo gia na valei to swsto path
                writeResult();
            } catch (NullPointerException e) { //An o xristis dialeksei to option 4 prin to 1 tha uparxei null sto csvFile opote prepei na to kanoume catch kai na tou petaksoume ena minima
                System.out.println("\nThe dot file is empty because CSV is not stored in the memory, select option 1 to store the CSV!");
                //Tha kalesoume tin methodo Menu gia na mporesei na kanei to vima 1 prwta
                Menu();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        //Me aftin tin methodo tha ftiaxnete to png arxeio trexontas mia entoli sto cmd kai o xristis tha mporei na to onomazei afto to arxeio
        public static void create() throws IOException {

            Scanner input = new Scanner(System.in);
            Runtime r = Runtime.getRuntime();
            Process p = null;

            try {
                System.out.println("Input the path on where to save the png file like (C:/users/name/Desktop) ");
                System.out.print("Input: ");
                String pngPath = input.nextLine();
                File f = new File(pngPath);

                System.out.println("Input the name of the png file without (.png) ");
                System.out.print("Input: ");
                String pngFile = input.nextLine();
                p = r.exec("dot -Tpng " + dotFile + ".dot" + " -o " + pngFile + ".png", null, f);

                //Afou ola pane kala kanoume ena print oti dimiourgithike
                System.out.println("\nPng file created successfully!!");

            } catch (Exception e) { //An valei ena path pou den uparxei tha to kanoume catch gia na tou petaksoume ena minima
                System.out.println("\nPath does not exists, Please try again!!\n");
                //Tha kalesoume pali tin methodo create gia na dwsei to swsto path
                create();
            } finally {
                if (p != null) {
                    while (p.isAlive());
                    p.destroy();
                }
            }

        }

        //Se aftin tin methodo tha tsekarei tin sxesi father,mother,married kai analogos tha vazei xrwma
        public static String CheckColor(String[] arr, String c) {
            if (arr[1].contains("father")) {
                c = "[color=royalblue]";
            } else if (arr[1].contains("married")) {
                c = "[color=green][dir=\"both\"]";
            } else if (arr[1].contains("mother")) {
                c = "[color=plum2]";
            }
            return c;
        }

        //Edw tha tsekaroume an ta duo onomata einai married tote na mpoun sto idio rank diladi ena dipla apo to allo gia na fainetai pio wraio
        public static void CheckRank(String[] arr, BufferedWriter bw, char icon) {
            try {
                //An i thesi 1 exei to married tote tin thesi 0 me 2 na mpoun sto idio rank ena dipla apo to allo
                if (arr[1].contains("married")) {
                    bw.write("{rank=same " + icon + arr[0] + icon + " " + icon + arr[2] + icon + "}\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Tsekaroume an einai arseniko i thiliko kai analogos na ftiaksoume to node tou
        public static void CheckGender(String[] arr, BufferedWriter bw, char icon) {
            try {
                //An einai gunaika tha valoume kuklo se xrwma roz 
                if (arr[1].contains("female")) {
                    bw.write(icon + arr[0] + icon + "[shape=oval][color=plum2];\n");
                    //Alliws an einai antras orthogonia me mple xrwma
                } else if (arr[1].contains("male")) {
                    bw.write(icon + arr[0] + icon + "[shape=rectangle][color=royalblue];\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Grafoume tis prwtes grames tou dot file opou periexei kai ton pinaka
        public static void WriteFirst(BufferedWriter bw, char icon) {
            try {
                bw.write("digraph Family {\nsplines=true;\n");
                bw.write("rankdir=TB;\ncompound=true;\n");
                bw.write("size=\"10,5\"\nnode [shape = rectangle] [color=black];\n");
                bw.write("node [shape = component]\nc_node [\n label=<\n");
                bw.write("<table border=" + icon + 0 + icon + " cellborder=" + icon + 0 + icon + " cellspacing=" + icon + 1 + icon + ">\n");
                bw.write("<tr><td></td><td>Colors</td><td></td></tr>\n");
                bw.write("<tr><td>Married</td><td>-</td><td><font color=" + icon + "green" + icon + ">Green</font></td></tr>\n");
                bw.write("<tr><td>Father</td><td>-</td><td><font color=" + icon + "blue" + icon + ">Blue</font></td></tr>\n");
                bw.write("<tr><td>Mother</td><td>-</td><td><font color=" + icon + "pink" + icon + ">Pink</font></td></tr>\n");

                bw.write("</table>>\n];\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //Ftiaxnoume to arxeio ResultsOfInputedNames.dot 
        public static void write_Results_Of_InputedNames(String firstName, String secondName, String relation) throws IOException {
            if (openedFile == false) {
                PrintWriter writer = new PrintWriter("ResultsOfInputedNames.dot", "UTF-8");
                writer.println("digraph G {");
                writer.println("  rankdir=LR");
                writer.println("  node [label=\"" + firstName + "\", shape=box]");
                writer.println("  node1 [label=\"" + relation + "\", style=filled, fillcolor=green]");
                writer.println("  node2 [label=\"" + secondName + "\"]");
                writer.println("");
                writer.println("  node0 -> node1 -> node2");
                writer.close();
                openedFile = true;
            } else if (openedFile == true) {
                PrintWriter writer = new PrintWriter(new FileWriter("ResultsOfInputedNames.dot", true));
                writer.println("");
                writer.println("  node" + node + " [label=\"" + firstName + "\", shape=box]");
                writer.println("  node" + (node + 1) + " [label=\"" + relation + "\", style=filled, fillcolor=green]");
                writer.println("  node" + (node + 2) + " [label=\"" + secondName + "\"]");
                writer.println("");
                writer.println("  node" + node + " -> node" + (node + 1) + " -> node" + (node + 2));
                node = node + 3;
                writer.println("}");
                writer.close();
            }
            
        }
        
        //Kleinoume to arxeio ResultsOfInputedNames.dot kai amesws meta to trexoume stin cmd gia na ftiaksoume to png arxeio
        public static void closeGraphVizFile() throws IOException {
            PrintWriter writer = new PrintWriter(new FileWriter("ResultsOfInputedNames.dot", true));
            writer.println("}");
            writer.close();
            
            Runtime r1 = Runtime.getRuntime();
            Process p1 = null;
            File f1 = new File("C:\\Users\\bicik\\Documents\\NetBeansProjects\\ergasia2");
            try {
                p1 = r1.exec("dot -Tpng ResultsOfInputedNames.dot -o ResultsOfInputedNames.png", null, f1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (p1 != null) {
                    while (p1.isAlive());
                    p1.destroy();
                }
            }
        }

        //Afti i methodos tha pernei treis parametrous ta duo onomata kai tin thesi pou tha paei na parei ta dedomena apo ton pinaka resultArr
        public static void printResults(String firstName, String secondName, int functionNumber) throws IOException {
            //Analogos poia methodos trexei tha pernei ta dedomena apo afton ton pinaka ta opoia xwrizontai me koma kai exeis tis sxesis mesa tous 
            String[] arr = resultArr[functionNumber].split(",", 2);

            //An i methodos pou tsekarei to fulo dwsei true gia to prwto input tou xristi tote tha xrisimopoiisoume to arseniko fulo stin protasi
            if (Search.checkGender(firstName)) {
                //Xrisimopoioume afta ta dedomena tou pinaka resultArr analogos me to fulo
                System.out.println(firstName + " is the " + arr[0] + " of " + secondName);
                //Kaloume aftin tin methodo gia na ftiaksoume ton grafo me ta input tou xristi
                write_Results_Of_InputedNames(firstName, secondName, arr[0]);
                //Alliws to thiliko
            } else {
                System.out.println(firstName + " is the " + arr[1] + " of " + secondName);
                write_Results_Of_InputedNames(firstName, secondName, arr[1]);
            }
        }
    }
}

