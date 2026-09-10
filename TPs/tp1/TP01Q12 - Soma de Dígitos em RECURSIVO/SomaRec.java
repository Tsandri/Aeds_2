import java.util.Scanner;

class SomaRec{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n;

        while(sc.hasNextInt()){
            n = sc.nextInt();
            System.out.println(chamaSoma(n));
        }
        sc.close();
    }

    /*soma o ultimo digito (n % 10) e faz a chamada recursiva
     com o numero dividido por 10 ate restar apenas um digito*/
    public static int soma(int n){
        int resp;
        if(n / 10 == 0){
            resp = n;
        }
        else{
            resp = soma(n / 10) + n % 10;
        }
        return resp;
    }

    /*metodo que repassa o numero para o metodo recursivo*/
    public static int chamaSoma(int n){
        return soma(n);
    }
}
