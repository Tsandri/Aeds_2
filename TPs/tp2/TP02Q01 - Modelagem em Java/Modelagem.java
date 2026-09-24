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

    // Separa a String da data AAAA-MM-DD
    public static Data parseData(String s) {
        if (s == null || s.length() == 0) return null;

        String[] partes = s.split("-");
        int a = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        int d = Integer.parseInt(partes[2]);

        return new Data(a, m, d);
    }

    // Retorna a data no formato DD/MM/YYYY
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

    // Preenche o objeto com a linha do CSV
    public static Veiculo parseVeiculo(String s) {
        Veiculo v = new Veiculo();
        String[] campos = s.split(",");

        v.setId(Integer.parseInt(campos[0]));
        v.setMarca(campos[1]);
        v.setModelo(campos[2]);
        v.setAno(Integer.parseInt(campos[3]));
        v.setCategoria(campos[4]);

        // Separando os combustiveis por ;
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

    // Saida no padrao do Verde da PUC
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

            return String.format(java.util.Locale.US,"[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]",
        id, marca, modelo, ano, categoria, combStr, cilindros,
        cilindrada, transmissao, tracao, consumoCidade, consumoEstrada,
        co2, turbo, (dataRegistro != null ? dataRegistro.format() : ""));
    }
}

class LeitorCsv {
    public static Veiculo[] ler(String caminhoArquivo) {
        Veiculo[] veiculos = new Veiculo[20000];
        int count = 0;

        try {
            File arq = new File(caminhoArquivo);
            Scanner scanner = new Scanner(arq);

            // Ignora a primeira linha do cabecalho
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Le cada linha do CSV
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.length() > 0) {
                    veiculos[count++] = Veiculo.parseVeiculo(linha);
                }
            }
            scanner.close();
        } catch (Exception e) {
            // Trata arquivo nao encontrado localmente
        }

        Veiculo[] resultado = new Veiculo[count];
        for (int i = 0; i < count; i++) {
            resultado[i] = veiculos[i];
        }
        return resultado;
    }
}

public class Modelagem {
    public static void main(String[] args) {
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        Scanner sc = new Scanner(System.in);

        // Pesquisa ate a entrada receber -1
        while (sc.hasNextInt()) {
            int idBusca = sc.nextInt();
            if (idBusca == -1) break;

            for (int i = 0; i < veiculos.length; i++) {
                if (veiculos[i] != null && veiculos[i].getId() == idBusca) {
                    System.out.println(veiculos[i].format());
                    break;
                }
            }
        }
        sc.close();
    }
}
