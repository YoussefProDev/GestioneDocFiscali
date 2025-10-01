package it.eforhum.GestioneFatture;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.Doc;

public class GestioneDocFiscali {
    HashMap<String, DocFiscale> docFiscali;
    HashMap<Integer, Integer> lastCodicePerAnno;

    public GestioneDocFiscali() {
        docFiscali = new HashMap<>();
        lastCodicePerAnno = new HashMap<>();
    }

    public void addDocFiscale(String idCliente,  String data, String descrizione, double importo) {
        LocalDate date = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);
        int lastId = getLastId(date.getYear());
        DocFiscale docFiscale = new DocFiscale(lastId, descrizione, date , importo, idCliente);
        DocFiscale docFiscale = new DocFiscale(lastId, descrizione, date , importo,idCliente);
        docFiscali.put(docFiscale.getId(), docFiscale);
        lastCodicePerAnno.put(date.getYear(), lastId + 1);
    }   
    
    public List<DocFiscale> getDocFiscaliByCliente(String idCliente) {
        List<DocFiscale> fattureCliente = docFiscali.values().stream()
                .filter(doc -> doc.getIdCliente().equals(idCliente))
                .sorted()
                .toList();
        return fattureCliente;
    }
    public void removeDocFiscale(String codice) {
        docFiscali.remove(codice);
    }
    public ArrayList<DocFiscale> getAllDocFiscali() {
        return new ArrayList<>(docFiscali.values());
    }
    public int getCount() {
        return docFiscali.size();
    }

    public boolean exists(String codice) {
        return docFiscali.containsKey(codice);
    }

    public double getImportoTotAnnuale(int anno) {
        double totale = 0.0;
        for (DocFiscale docFiscale : docFiscali.values()) {
            if (docFiscale.getId().endsWith(String.valueOf(anno))) {
                totale += docFiscale.getImporto();
            }
        }
        return totale;
    }

    public int getLastId(int anno) {
        return lastCodicePerAnno.getOrDefault(anno, 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DocFiscale docFiscale : docFiscali.values()) {
            sb.append(docFiscale.toString()).append("\n");
        }
        return sb.toString();
    }

}
