package it.eforhum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import it.eforhum.GestioneClienti.Cliente;
import it.eforhum.GestioneClienti.GestioneClienti;
import it.eforhum.GestioneFatture.DocFiscale;
import it.eforhum.GestioneFatture.GestioneDocFiscali;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static final String DELIMITER = "|--|";
    protected static GestioneDocFiscali gdf = new GestioneDocFiscali();
    protected static GestioneClienti gc = new GestioneClienti();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Menu Gestione Fatture ---");
            System.out.println("1. Registra nuova fattura");
            System.out.println("2. Elenco di tutte le fatture");
            System.out.println("3. Calcolo totale fatture");
            System.out.println("4. Lista fatture di un cliente");
            System.out.println("5. Elenco clienti");
            System.out.println("0. Esci");

            int scelta = numberInput("Seleziona un'opzione: ", 0, 5);
            switch (scelta) {
                case 1 -> registraFattura();
                case 2 -> visualizzaFatture();
                case 3 -> calcolaTotaleFatture();
                case 4 -> fatturePerCliente();
                case 5 -> elencoClienti();
                case 0 -> exit = true;
            }
        }
    }

    private static void registraFattura() {
        System.out.println("\n--- Registrazione nuova fattura ---");
        String cliente = gc.chooseCliente();
        if (cliente == null)
            return;
        String data = dateInput("Inserisci la data della fattura (gg/mm/aaaa): ");
        if (data == null)
            return;
        String descrizione = stringInput("Inserisci la descrizione della fattura: ");
        if (descrizione == null)
            return;
        double importo = doubleInput("Inserisci l'importo della fattura: ");
        gdf.addDocFiscale(cliente, data, descrizione, importo);
        System.out.println("Fattura registrata correttamente!");
    }

    private static void visualizzaFatture() {
        System.out.println("\n--- Elenco fatture ---");
        ArrayList<DocFiscale> docFiscali = gdf.getAllDocFiscali();
        if (docFiscali.isEmpty()) {
            System.out.println("Nessuna fattura registrata.");
            return;
        }
        for (DocFiscale docF : docFiscali) {
            System.out.println(docF.toString());
            System.out.println("--------------------");
        }
    }

    private static void calcolaTotaleFatture() {
        int anno = numberInput("Inserisci l'anno per il calcolo del totale fatture: ");
        double totale = gdf.getImportoTotAnnuale(anno);

        System.out.println("Totale fatture: €" + totale);
    }

    private static void fatturePerCliente() {
        String cliente = gc.chooseCliente();
        if (cliente == null)
            return;
        System.out.println("\n--- Fatture del cliente " + cliente + " ---");
        boolean trovato = false;
        ArrayList<DocFiscale> docFiscali = gdf.getDocFiscaliByCliente(cliente);

        docFiscali.stream().forEach((DocFiscale doc) -> doc.toString());
    }

    private static void elencoClienti() {
        ArrayList<Cliente> clienti = gc.getallClienti();
        System.out.println("\n--- Elenco clienti ---");
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente registrato.");
        } else {
            for (Cliente c : clienti) {
                System.out.println(c.toString());
                System.out.println("--------------------");
            }
        }
    }

    // --- METODI DI INPUT ---
    private static int numberInput(String text) {
        while (true) {
            System.out.print(text);
            try {
                int selection = Integer.parseInt(SCANNER.nextLine());
                return selection;
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, inserisci un numero.");
            }
        }
    }

    private static int numberInput(String text, int min, int max) {
        while (true) {
            System.out.print(text);
            try {
                int selection = Integer.parseInt(SCANNER.nextLine());
                if (selection >= min && selection <= max)
                    return selection;
                else
                    System.out.println("Numero fuori dal range, riprova.");
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, inserisci un numero.");
            }
        }
    }

    private static String dateInput(String text) {
        while (true) {
            System.out.print(text);
            String input = SCANNER.nextLine().trim().replace("-", "/");
            try {
                LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("d/M/yyyy"));
                return date.format(DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida, formato corretto: gg/MM/yyyy.");
            }
        }
    }

    private static double doubleInput(String text) {
        while (true) {
            System.out.print(text);
            try {
                return Double.parseDouble(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, inserisci un numero.");
            }
        }
    }

    private static String stringInput(String text) {
        System.out.print(text);
        String input = SCANNER.nextLine().trim();
        if (input.equalsIgnoreCase("exit"))
            return null;
        return input;
    }
}
