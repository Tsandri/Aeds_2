import java.io.File;
import java.util.Scanner;

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data() {}

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public int getDia() { return dia; }
    public void setDia(int dia) { this.dia = dia; }

    public static Data parseData(String s) {
        if (s == null || s.length() == 0) return null;
        String[] partes = s.split("-");
        int a = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        int d = Integer.parseInt(partes[2]);
        return new Data(a, m, d);
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

        v.setId(Integer.parseInt(campos[0]));
        v.setMarca(campos[1]);
        v.setModelo(campos[2]);
        v.setAno(Integer.parseInt(campos[3]));
        v.setCategoria(campos[4]);

        String[] comb = campos[5].split(";");
        v.setCombustivel(comb);

        v.setCilindros(Integer.parseInt(campos[6]));
        v.setCilindrada(Double.parseDouble(campos[7]));
        v.setTransmissao(campos[8]);
        v.setTracao(campos[9]);
        v.setConsumoCidade(Double.parseDouble(campos[10]));
        v.setConsumoEstrada(Double.parseDouble(campos[11]));
        v.setCo2(Double.parseDouble(campos[12]));
        v.setTurbo(Boolean.parseBoolean(campos[13]));
        v.setDataRegistro(Data.parseData(campos[14]));

        return v;
    }

    public String format() {
        String combStr = "[";
        if (combustivel != null) {
            for (int i = 0; i < combustivel.length; i++) {
                combStr += combustivel[i];
                if (i < combustivel.length - 1) {
                    combStr += ", ";
                }
            }
        }
        combStr += "]";

        return "[" + id + " ## " + marca + " ## " + modelo + " ## " + ano + " ## " +
               categoria + " ## " + combStr + " ## " + cilindros + " ## " +
               cilindrada + " ## " + transmissao + " ## " + tracao + " ## " +
               consumoCidade + " ## " + consumoEstrada + " ## " + co2 + " ## " +
               turbo + " ## " + (dataRegistro != null ? dataRegistro.format() : "") + "]";
    }
}

class LeitorCsv {
    public static Veiculo[] ler(String caminhoArquivo) {
        Veiculo[] veiculos = new Veiculo[20000];
        int count = 0;

        try {
            File arq = new File(caminhoArquivo);
            Scanner scanner = new Scanner(arq);

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.length() > 0) {
                    veiculos[count++] = Veiculo.parseVeiculo(linha);
                }
            }
            scanner.close();
        } catch (Exception e) {
            // tratamento de arquivo nao encontrado
        }

        Veiculo[] resultado = new Veiculo[count];
        for (int i = 0; i < count; i++) {
            resultado[i] = veiculos[i];
        }
        return resultado;
    }
}

public class Insercao {

    // Algoritmo de Insercao pela marca
    public static void insercaoMarca(Veiculo[] v, int n) {
        for (int i = 1; i < n; i++) {
            Veiculo tmp = v[i];
            int j = i - 1;

            // Se as marcas forem iguais, desempatamos pelo ID
            while (j >= 0 && (v[j].getMarca().compareTo(tmp.getMarca()) > 0 || 
                  (v[j].getMarca().equals(tmp.getMarca()) && v[j].getId() > tmp.getId()))) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = tmp;
        }
    }

    public static void main(String[] args) {
        Veiculo[] todosVeiculos = LeitorCsv.ler("/tmp/VEICULOS.CSV");

        Veiculo[] selecionados = new Veiculo[20000];
        int numSelecionados = 0;

        Scanner sc = new Scanner(System.in);

        // Le os IDs ate encontrar -1
        while (sc.hasNextInt()) {
            int idBusca = sc.nextInt();
            if (idBusca == -1) break;

            for (int i = 0; i < todosVeiculos.length; i++) {
                if (todosVeiculos[i] != null && todosVeiculos[i].getId() == idBusca) {
                    selecionados[numSelecionados++] = todosVeiculos[i];
                    break;
                }
            }
        }

        // Ordenacao por Insercao
        insercaoMarca(selecionados, numSelecionados);

        // Imprime o resultado ordenado
        for (int i = 0; i < numSelecionados; i++) {
            System.out.println(selecionados[i].format());
        }

        sc.close();
    }
}