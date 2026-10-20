import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data() { this.ano = 0; this.mes = 0; this.dia = 0; }
    public Data(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }

    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }

    public static Data parseData(String s) {
        if (s == null || s.trim().isEmpty()) return new Data();
        String[] partes = s.trim().split("-");
        if (partes.length < 3) return new Data();
        return new Data(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }

    public String format() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    public Veiculo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String[] getCombustivel() { return combustivel; }
    public void setCombustivel(String[] combustivel) { this.combustivel = combustivel; }
    public int getCilindros() { return cilindros; }
    public void setCilindros(int cilindros) { this.cilindros = cilindros; }
    public double getCilindrada() { return cilindrada; }
    public void setCilindrada(double cilindrada) { this.cilindrada = cilindrada; }
    public String getTransmissao() { return transmissao; }
    public void setTransmissao(String transmissao) { this.transmissao = transmissao; }
    public String getTracao() { return tracao; }
    public void setTracao(String tracao) { this.tracao = tracao; }
    public double getConsumoCidade() { return consumoCidade; }
    public void setConsumoCidade(double consumoCidade) { this.consumoCidade = consumoCidade; }
    public double getConsumoEstrada() { return consumoEstrada; }
    public void setConsumoEstrada(double consumoEstrada) { this.consumoEstrada = consumoEstrada; }
    public double getCo2() { return co2; }
    public void setCo2(double co2) { this.co2 = co2; }
    public boolean isTurbo() { return turbo; }
    public void setTurbo(boolean turbo) { this.turbo = turbo; }
    public Data getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(Data dataRegistro) { this.dataRegistro = dataRegistro; }

    public static Veiculo parseVeiculo(String s) {
        Veiculo v = new Veiculo();
        String[] campos = s.split(",");

        v.setId(Integer.parseInt(campos[0].trim()));
        v.setMarca(campos[1].trim());
        v.setModelo(campos[2].trim());
        v.setAno(Integer.parseInt(campos[3].trim()));
        v.setCategoria(campos[4].trim());

        String[] comb = campos[5].trim().split(";");
        for (int i = 0; i < comb.length; i++) comb[i] = comb[i].trim();
        v.setCombustivel(comb);

        v.setCilindros(Integer.parseInt(campos[6].trim()));
        v.setCilindrada(Double.parseDouble(campos[7].trim()));
        v.setTransmissao(campos[8].trim());
        v.setTracao(campos[9].trim());
        v.setConsumoCidade(Double.parseDouble(campos[10].trim()));
        v.setConsumoEstrada(Double.parseDouble(campos[11].trim()));
        v.setCo2(Double.parseDouble(campos[12].trim()));
        v.setTurbo(Boolean.parseBoolean(campos[13].trim()));
        v.setDataRegistro(Data.parseData(campos[14].trim()));

        return v;
    }

    public String format() {
        String combStr = "[";
        if (combustivel != null) {
            for (int i = 0; i < combustivel.length; i++) {
                combStr += combustivel[i];
                if (i < combustivel.length - 1) combStr += ",";
            }
        }
        combStr += "]";

        String dataFormatted = (dataRegistro != null) ? dataRegistro.format() : "";

        return String.format(java.util.Locale.US,
            "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]",
            id, marca, modelo, ano, categoria, combStr, cilindros,
            cilindrada, transmissao, tracao, consumoCidade, consumoEstrada,
            co2, turbo, dataFormatted
        );
    }
}

class LeitorCsv {
    public static Veiculo[] ler(String caminhoArquivo) {
        Veiculo[] temp = new Veiculo[20000];
        int total = 0;
        try {
            File arq = new File(caminhoArquivo);
            Scanner scanner = new Scanner(arq);
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.trim().isEmpty()) {
                    temp[total++] = Veiculo.parseVeiculo(linha);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {}

        Veiculo[] resultado = new Veiculo[total];
        for (int i = 0; i < total; i++) resultado[i] = temp[i];
        return resultado;
    }
}

// Celula Dupla
class CelulaDupla {
    public Veiculo elemento;
    public CelulaDupla ant;
    public CelulaDupla prox;

    public CelulaDupla() {
        this(null);
    }

    public CelulaDupla(Veiculo elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.prox = null;
    }
}

// Lista Duplamente Encadeada
class ListaDupla {
    public CelulaDupla primeiro;
    public CelulaDupla ultimo;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    public void inserirFim(Veiculo x) {
        ultimo.prox = new CelulaDupla(x);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public void mostrar() {
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(i.elemento.format());
        }
    }
}

public class QuickSortListaDupla {

    // Comparacao por consumoEstrada e desempate por marca
    public static int comparar(Veiculo a, Veiculo b) {
        if (a.getConsumoEstrada() != b.getConsumoEstrada()) {
            return Double.compare(a.getConsumoEstrada(), b.getConsumoEstrada());
        }
        return a.getMarca().compareTo(b.getMarca());
    }

    public static void troca(CelulaDupla i, CelulaDupla j) {
        Veiculo tmp = i.elemento;
        i.elemento = j.elemento;
        j.elemento = tmp;
    }

    // Quicksort em Lista Duplamente Encadeada
    public static void quicksort(CelulaDupla esq, CelulaDupla dir) {
        if (esq != null && dir != null && esq != dir && esq.ant != dir) {
            CelulaDupla pivo = dir;
            CelulaDupla i = esq.ant;

            for (CelulaDupla j = esq; j != dir; j = j.prox) {
                if (comparar(j.elemento, pivo.elemento) <= 0) {
                    i = (i == null) ? esq : i.prox;
                    troca(i, j);
                }
            }

            i = (i == null) ? esq : i.prox;
            troca(i, dir);

            if (i.ant != null && esq != i) {
                quicksort(esq, i.ant);
            }
            if (i.prox != null && dir != i) {
                quicksort(i.prox, dir);
            }
        }
    }

    public static void main(String[] args) {
        Veiculo[] base = LeitorCsv.ler("/tmp/veiculos.csv");
        if (base.length == 0) base = LeitorCsv.ler("/tmp/VEICULOS.CSV");

        Scanner sc = new Scanner(System.in);
        ListaDupla lista = new ListaDupla();

        while (sc.hasNextInt()) {
            int idBusca = sc.nextInt();
            if (idBusca == -1) break;

            for (int i = 0; i < base.length; i++) {
                if (base[i] != null && base[i].getId() == idBusca) {
                    lista.inserirFim(base[i]);
                    break;
                }
            }
        }

        if (lista.primeiro.prox != null) {
            quicksort(lista.primeiro.prox, lista.ultimo);
        }

        lista.mostrar();
        sc.close();
    }
}
