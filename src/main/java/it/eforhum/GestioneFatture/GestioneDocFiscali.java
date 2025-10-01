package it.eforhum.GestioneFatture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.Doc;
import static it.eforhum.App.DELIMITER;
public class GestioneDocFiscali {
    HashMap<String, DocFiscale> docFiscali;
    HashMap<Integer, Integer> lastCodicePerAnno;

    public GestioneDocFiscali() throws FileNotFoundException {
        docFiscali = new HashMap<>();
        lastCodicePerAnno = new HashMap<>();
        riempiDaFile("docfiscali.txt");
    }

    public void addDocFiscale(String idCliente,  String data, String descrizione, double importo) {
        LocalDate date = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);
        int lastId = getLastId(date.getYear());
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

    public void riempiDaFile(String filename) throws FileNotFoundException {
    File file = new File(filename);
        if (!file.exists()){
           // throw FileNotFoundException; 
           return;
        }
          

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
               
                DocFiscale docFiscale = new DocFiscale(line);
                this.docFiscali.put(docFiscale.getId(), docFiscale);
                int anno = docFiscale.getData().getYear();
                this.lastCodicePerAnno.put(anno, this.lastCodicePerAnno.getOrDefault(anno, 0) + 1);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scriviFile(String filename) {
        File file = new File(filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (DocFiscale docFiscale : docFiscali.values()) {
                bw.write(docFiscale.prepare());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
