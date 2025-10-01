package it.eforhum.GestioneFatture;

import static it.eforhum.App.DELIMITER;

import java.time.LocalDate;

public class DocFiscale implements Comparable<DocFiscale> {
    private String descrizione, id, idCliente;
    private LocalDate data;
    private double importo;
    private int numeroFattura;

    // Costruttore da record
    DocFiscale(String record) {
        String[] parts = record.split(DELIMITER);
        this.id = parts[0];
        this.idCliente = parts[1];
        this.importo = Double.parseDouble(parts[2]);
        this.descrizione = parts[3];
        this.data = LocalDate.parse(parts[4]);
        this.id =  this.numeroFattura + "_" + this.data.getYear();
    }

    // Costruttore completo
    public DocFiscale(int numeroFattura, String descrizione, LocalDate data, double importo, String idCliente) {
        this.id = numeroFattura + "_" + data.getYear();
        this.numeroFattura = numeroFattura;
        this.descrizione = descrizione;
        this.data = data;
        this.importo = importo;
        this.idCliente = idCliente;
    }

    // Metodo per serializzare l'oggetto
    public String prepare() {
        return id + DELIMITER +
               idCliente + DELIMITER +
               importo + DELIMITER +
               descrizione + DELIMITER +
               data;
    }

    @Override
    public String toString() {
        return "DocFiscale{" +
               "id='" + id + '\'' +
               ", numero fattura='" + numeroFattura + '\'' +
               ", descrizione='" + descrizione + '\'' +
               ", data=" + data +
               ", importo=" + importo +
               ", idCliente=" + idCliente +
               '}';
    }

    // Implementazione di compareTo (ordine per data)
    @Override
    public int compareTo(DocFiscale doc) {
        return this.data.compareTo(doc.getData());
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
