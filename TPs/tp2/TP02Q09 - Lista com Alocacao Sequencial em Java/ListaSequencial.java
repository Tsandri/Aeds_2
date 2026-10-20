import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

class Data {
    private int ano, mes, dia;
    public Data() { this.ano = 0; this.mes = 0; this.dia = 0; }
    public Data(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }
    public static Data parseData(String s) {
        if (s == null || s.trim().isEmpty()) return new Data();
        String[] p = s.trim().split("-");
        if (p.length < 3) return new Data();
        return new Data(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }
    public String format() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Veiculo {
    private int id;
    private String marca, modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao, tracao;
    private double consumoCidade, consumoEstrada, co2;
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
        String[] c = s.split(",");
        v.setId(Integer.parseInt(c[0].trim()));
        v.setMarca(c[1].trim());
        v.setModelo(c[2].trim());
        v.setAno(Integer.parseInt(c[3].trim()));
        v.setCategoria(c[4].trim());
        String[] comb = c[5].trim().split(";");
        for (int i = 0; i < comb.length; i++) comb[i] = comb[i].trim();
        v.setCombustivel(comb);
        v.setCilindros(Integer.parseInt(c[6].trim()));
        v.setCilindrada(Double.parseDouble(c[7].trim()));
        v.setTransmissao(c[8].trim());
        v.setTracao(c[9].trim());
        v.setConsumoCidade(Double.parseDouble(c[10].trim()));
        v.setConsumoEstrada(Double.parseDouble(c[11].trim()));
        v.setCo2(Double.parseDouble(c[12].trim()));
        v.setTurbo(Boolean.parseBoolean(c[13].trim()));
        v.setDataRegistro(Data.parseData(c[14].trim()));
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
                if (!linha.trim().isEmpty()) temp[total++] = Veiculo.parseVeiculo(linha);
            }
            scanner.close();
        } catch (FileNotFoundException e) {}
        Veiculo[] res = new Veiculo[total];
        for (int i = 0; i < total; i++) res[i] = temp[i];
        return res;
    }
}

public class ListaSequencial {
    private Veiculo[] array;
    private int n;

    public ListaSequencial() { this(1000); }
    public ListaSequencial(int tamanho) {
        array = new Veiculo[tamanho];
        n = 0;
    }

    public void inserirInicio(Veiculo v) throws Exception {
        if (n >= array.length) throw new Exception("Erro ao inserir!");
        for (int i = n; i > 0; i--) array[i] = array[i - 1];
        array[0] = v;
        n++;
    }

    public void inserir(Veiculo v, int pos) throws Exception {
        if (n >= array.length || pos < 0 || pos > n) throw new Exception("Erro ao inserir!");
        for (int i = n; i > pos; i--) array[i] = array[i - 1];
        array[pos] = v;
        n++;
    }

    public void inserirFim(Veiculo v) throws Exception {
        if (n >= array.length) throw new Exception("Erro ao inserir!");
        array[n++] = v;
    }

    public Veiculo removerInicio() throws Exception {
        if (n == 0) throw new Exception("Erro ao remover!");
        Veiculo resp = array[0];
        n--;
        for (int i = 0; i < n; i++) array[i] = array[i + 1];
        return resp;
    }

    public Veiculo remover(int pos) throws Exception {
        if (n == 0 || pos < 0 || pos >= n) throw new Exception("Erro ao remover!");
        Veiculo resp = array[pos];
        n--;
        for (int i = pos; i < n; i++) array[i] = array[i + 1];
        return resp;
    }

    public Veiculo removerFim() throws Exception {
        if (n == 0) throw new Exception("Erro ao remover!");
        return array[--n];
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i].format());
        }
    }

    public static Veiculo buscarBase(Veiculo[] base, int id) {
        for (int i = 0; i < base.length; i++) {
            if (base[i] != null && base[i].getId() == id) return base[i];
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Veiculo[] base = LeitorCsv.ler("/tmp/veiculos.csv");
        if (base.length == 0) base = LeitorCsv.ler("/tmp/VEICULOS.CSV");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ListaSequencial lista = new ListaSequencial(5000);
        String linha;

        // Parte 1: Leitura ate -1
        while ((linha = reader.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            if (linha.equals("-1")) break;

            int id = Integer.parseInt(linha);
            Veiculo v = buscarBase(base, id);
            if (v != null) lista.inserirFim(v);
        }

        // Parte 2: Comandos
        linha = reader.readLine();
        if (linha != null) {
            int nComandos = Integer.parseInt(linha.trim());

            for (int i = 0; i < nComandos; i++) {
                linha = reader.readLine();
                if (linha == null) break;
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split("\\s+");
                String cmd = partes[0];
                Veiculo r = null;

                if (cmd.equals("II")) {
                    int id = Integer.parseInt(partes[1]);
                    lista.inserirInicio(buscarBase(base, id));
                } else if (cmd.equals("I") || cmd.equals("I*")) {
                    int pos = Integer.parseInt(partes[1]);
                    int id = Integer.parseInt(partes[2]);
                    lista.inserir(buscarBase(base, id), pos);
                } else if (cmd.equals("IF")) {
                    int id = Integer.parseInt(partes[1]);
                    lista.inserirFim(buscarBase(base, id));
                } else if (cmd.equals("RI")) {
                    r = lista.removerInicio();
                } else if (cmd.equals("R") || cmd.equals("R*")) {
                    int pos = Integer.parseInt(partes[1]);
                    r = lista.remover(pos);
                } else if (cmd.equals("RF")) {
                    r = lista.removerFim();
                }

                if (r != null) {
                    System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
                }
            }
        }

        lista.mostrar();
    }
}
