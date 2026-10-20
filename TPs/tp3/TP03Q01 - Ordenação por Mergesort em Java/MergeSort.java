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

public class MergeSort {

    public static int comparar(Veiculo a, Veiculo b) {
        if (a.getConsumoCidade() != b.getConsumoCidade()) {
            return Double.compare(a.getConsumoCidade(), b.getConsumoCidade());
        }
        return a.getCategoria().compareTo(b.getCategoria());
    }

    public static void mergesort(Veiculo[] array, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
    }

    public static void intercalar(Veiculo[] array, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Veiculo[] esqArray = new Veiculo[n1];
        Veiculo[] dirArray = new Veiculo[n2];

        for (int i = 0; i < n1; i++) {
            esqArray[i] = array[esq + i];
        }
        for (int j = 0; j < n2; j++) {
            dirArray[j] = array[meio + 1 + j];
        }

        int i = 0, j = 0, k = esq;

        while (i < n1 && j < n2) {
            if (comparar(esqArray[i], dirArray[j]) <= 0) {
                array[k] = esqArray[i];
                i++;
            } else {
                array[k] = dirArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = esqArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = dirArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        Veiculo[] base = LeitorCsv.ler("/tmp/veiculos.csv");
        if (base.length == 0) base = LeitorCsv.ler("/tmp/VEICULOS.CSV");

        Scanner sc = new Scanner(System.in);
        Veiculo[] entrada = new Veiculo[5000];
        int n = 0;

        while (sc.hasNextInt()) {
            int idBusca = sc.nextInt();
            if (idBusca == -1) break;

            for (int i = 0; i < base.length; i++) {
                if (base[i] != null && base[i].getId() == idBusca) {
                    entrada[n++] = base[i];
                    break;
                }
            }
        }

        if (n > 0) {
            mergesort(entrada, 0, n - 1);
        }

        for (int i = 0; i < n; i++) {
            System.out.println(entrada[i].format());
        }

        sc.close();
    }
}
