package it.eforhum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import it.eforhum.GestioneFatture.GestioneDocFiscali;

class Fattura {
    private String cliente;
    private String descrizione;
    private double importo;

    public Fattura(String cliente, String descrizione, double importo) {
        this.cliente = cliente;
        this.descrizione = descrizione;
        this.importo = importo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getImporto() {
        return importo;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + ", Descrizione: " + descrizione + ", Importo: €" + importo;
    }
}

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        GestioneDocFiscali gdf = new GestioneDocFiscali();
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
        String cliente = stringInput("Inserisci il nome del cliente: ");
        if (cliente == null)
            return;
        String descrizione = stringInput("Inserisci la descrizione della fattura: ");
        if (descrizione == null)
            return;
        double importo = doubleInput("Inserisci l'importo della fattura: ");
        fatture.add(new Fattura(cliente, descrizione, importo));
        System.out.println("Fattura registrata correttamente!");
    }

    private static void visualizzaFatture() {
        System.out.println("\n--- Elenco fatture ---");
        if (fatture.isEmpty()) {
            System.out.println("Nessuna fattura registrata.");
            return;
        }
        for (Fattura f : fatture) {
            System.out.println(f);
        }
    }

    private static void calcolaTotaleFatture() {
        double totale = 0;
        for (Fattura f : fatture) {
            totale += f.getImporto();
        }
        System.out.println("Totale fatture: €" + totale);
    }

    private static void fatturePerCliente() {
        String cliente = stringInput("Inserisci il nome del cliente: ");
        if (cliente == null)
            return;
        System.out.println("\n--- Fatture del cliente " + cliente + " ---");
        boolean trovato = false;
        for (Fattura f : fatture) {
            if (f.getCliente().equalsIgnoreCase(cliente)) {
                System.out.println(f);
                trovato = true;
            }
        }
        if (!trovato)
            System.out.println("Nessuna fattura trovata per questo cliente.");
    }

    private static void elencoClienti() {
        Set<String> clienti = new HashSet<>();
        for (Fattura f : fatture) {
            clienti.add(f.getCliente());
        }
        System.out.println("\n--- Elenco clienti ---");
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente registrato.");
        } else {
            for (String c : clienti) {
                System.out.println(c);
            }
        }
    }

    // --- METODI DI INPUT ---
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
