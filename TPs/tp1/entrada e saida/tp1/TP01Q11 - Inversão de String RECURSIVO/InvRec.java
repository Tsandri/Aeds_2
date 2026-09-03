import java.util.Scanner;

class InvRec{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!isFim(s)){
            System.out.println(inverte(s));
            s = sc.nextLine();
        }
        sc.close();
    }

    /*checa se a string recebida e FIM*/
    public static boolean isFim(String s){
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }

    /*chama o metodo recursivo passando o indice inicial 0*/
    public static String inverte(String s){
        return inverte(s, 0);
    }

    /*percorre a string recursivamente ate o final e vai concatenando
     os caracteres de tras para frente no retorno da pilha*/
    public static String inverte(String s, int i){
        String resp;
        if(i == s.length()){
            resp = "";
        }
        else{
            resp = inverte(s, i + 1) + s.charAt(i);
        }
        return resp;
    }
}
