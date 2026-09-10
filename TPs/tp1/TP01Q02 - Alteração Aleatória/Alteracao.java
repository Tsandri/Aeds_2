import java.util.Scanner;
import java.util.Random;

class Alteracao{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        random.setSeed(4);
        String s = sc.nextLine();
        while(!isFim(s)){
            System.out.println(altera(s, random));
            s = sc.nextLine();
        }
        sc.close();
    }

    /*sorteia dois caracteres minusculos aleatorios, cria uma nova
     string e percorre a string original substituindo todas as
     ocorrencias do primeiro caractere sorteado pelo segundo*/
    public static String altera(String s, Random random){
        String s2 = "";
        char c1 = (char) ('a' + (Math.abs(random.nextInt()) % 26));
        char c2 = (char) ('a' + (Math.abs(random.nextInt()) % 26));
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != c1){
                s2 += s.charAt(i);
            }
            else{
                s2 += c2;
            }
        }
        return s2;
    }

    /*verifica se a string possui tamanho 3 e se e formada
     exatamente pelos caracteres F, I e M em sequencia*/
    public static boolean isFim(String s){
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }
}
