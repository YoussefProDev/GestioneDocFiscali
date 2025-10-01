package it.eforhum.GestioneClienti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static it.eforhum.App.DELIMITER;
public class GestioneClienti {
  
    private ArrayList<Cliente> allClienti;
    private static final Scanner SCANNER = new Scanner(System.in);

    public GestioneClienti() {
        this.allClienti = new ArrayList<>();
        readallClienti();
    }

    public Cliente getClienteById(String id) {
        for (Cliente cliente : allClienti) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null; // Ritorna null se il cliente non viene trovato
    }

    public void printallClienti() {
        System.out.println("Lista Clienti:");
        allClienti.forEach(v -> System.out.println(
                v.getId() + ") " + v.getDenominazione() + " - " + v.getPartitaIva() + " - " + v.getIndirizzo()));
    }

    public String chooseCliente() {
        System.out.println("Scegli il Cliente:");
        System.out.println("0) Nuovo Cliente");
        printallClienti();
        Integer selection = numberInput("Scegliere Cliente: ", 0, allClienti.size());
        Cliente Cliente;
        if (selection == 0) {
            Cliente = this.createCliente();
        } else {
            Cliente = allClienti.get(selection - 1);
        }
        System.out.println("Cliente scelto: " + Cliente.getDenominazione());
        return Cliente.getId();
    }

    private Cliente createCliente() {
        try {
            File myObj = new File("Clientes.txt");
            if (myObj.createNewFile()) {
                System.out.println("File creato: " + myObj.getName());
            } else {
                System.out.println("File già esistente.");
            }

            FileWriter myWriter = new FileWriter("Clienti.txt", true);
            String ClienteId = String.valueOf(allClienti.size() + 1);
            boolean partitaIvaExist = false;
            String denominazione = stringInput("Inserire la Denominazione: ");
            String partitaIva;
            do {
                if (partitaIvaExist)
                    System.out.println("Denominazione già esistente.");
                partitaIvaExist = false;
                partitaIva = stringInput("Inserire la Partita IVA: ");
                for (Cliente Cliente : this.allClienti) {
                    if (Cliente.getPartitaIva().equals(partitaIva))
                        partitaIvaExist = true;
                }
            } while (partitaIvaExist);

            String indirizzo = stringInput("Inserire l'Indirizzo: ");
            Cliente Cliente = new Cliente(ClienteId, denominazione, partitaIva, indirizzo);
            this.allClienti.add(Cliente);
            myWriter.write(
                    Cliente.getDenominazione() + DELIMITER + Cliente.getPartitaIva() + DELIMITER + Cliente.getIndirizzo());
            myWriter.close();
            return Cliente;

        } catch (IOException e) {
            System.out.println("Si è verificato un errore.");
            return this.createCliente();
        }
    }

    public ArrayList<Cliente> getallClienti() {
        return allClienti;
    }

    protected void readallClienti() {
        ArrayList<Cliente> Clienti = new ArrayList<>();
        try {
            File myObj = new File("Clienti.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(DELIMITER);
                Clienti.add(new Cliente(data[0], data[1], data[2], data[3]));
            }
            myReader.close();
            this.allClienti = Clienti;
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato.");
        }
    }

    // --- METODI DI INPUT ---
    private int numberInput(String text, int min, int max) {
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

    private String stringInput(String text) {
        System.out.print(text);
        return SCANNER.nextLine().trim();
    }

    private String stringInput(String text, String[] options) {
        while (true) {
            System.out.print(text);
            String input = SCANNER.nextLine().trim();
            for (String option : options) {
                if (input.equalsIgnoreCase(option))
                    return input;
            }
            System.out.println("Valore non valido. Opzioni valide: " + String.join(", ", options));
        }
    }
}
