package it.eforhum.GestioneClienti;

public class Cliente {
    private String id;
    private String denominazione;
    private String partitaIva;
    private String indirizzo;

    public Cliente(String id, String denominazione, String partitaIva, String indirizzo) {
        this.id = id;
        this.denominazione = denominazione;
        this.partitaIva = partitaIva;
        this.indirizzo = indirizzo;
    }

    public String getId() {
        return id;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Denominazione: " + denominazione + "\n" +
                "Partita IVA: " + partitaIva + "\n" +
                "Indirizzo: " + indirizzo;
    }
}
