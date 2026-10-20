import java.util.Scanner;
class Corredor{
    private String nome;
    private int hora;
    private int minuto;
    private int segundo;

    public Corredor(){}

    public Corredor(String nome, int hora, int minuto, int segundo){
        this.nome = nome;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }
    public String getNome(){
        return nome;
    }
    public int getHora(){
        return hora;
    }
    public int getMinuto(){
        return minuto;
    }
    public int getSegundo(){
        return segundo;
    }
}
class Insertion{
    public static void insercao(Corredor[] array, int n, int k){
        for(int i = 0; i < k; i++){
            for(int j = 1; j < n; j++){
                if(array[j].getHora() != array[i].getHora() && array[j].getHora() < array[i].getHora()){
                    Corredor tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
                if(array[j].getHora() == array[i].getHora() && array[j].getMinuto() != array[i].getMinuto()  && array[j].getMinuto() < array[i].getMinuto()){
                    Corredor tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
                if(array[j].getHora() == array[i].getHora()  && array[j].getMinuto() == array[i].getMinuto() && array[j].getSegundo() < array[i].getSegundo()){
                    Corredor tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }
}
class Corrida{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()){
            int n = sc.nextInt();
            int k = sc.nextInt();
            Corredor[] c = new Corredor[n];
            for(int i = 0; i < n; i++){
                String nome = sc.next();
                int hora = sc.nextInt();
                int minuto = sc.nextInt();
                int segundo = sc.nextInt();
                c[i] = new Corredor(nome, hora, minuto, segundo);
            }
            Insertion.insercao(c, n, k);
            for(int i = 0; i < k; i++){
                System.out.printf("%s %d %d %d\n", c[i].getNome(), c[i].getHora(), c[i].getMinuto(), c[i].getSegundo());
            }
        }
        sc.close();
    }
}
