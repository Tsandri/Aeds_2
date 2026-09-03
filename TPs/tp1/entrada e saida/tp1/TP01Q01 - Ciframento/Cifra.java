import java.util.Scanner;

class Cifra{
    public static boolean isFim(String s){
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }
    public static String codifica(String s){
        /*recebe a string e cria outra, depois precorre a
         string recebida e guarda caractere por caractere
         na nova string, sempre somando 3, de acordo com
         a cifra*/
        String codificada = "";
        for(int i = 0;i < s.length();i++){
            codificada += (char)(s.charAt(i) + 3);
        }
        return codificada;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = "";
        s = sc.nextLine();
        while(!isFim(s)){
            System.out.println(codifica(s));
            s = sc.nextLine();
        }
        sc.close();
    }
}
