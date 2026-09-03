import java.util.Scanner;

class Senha{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!isFim(s)){
            System.out.println(validaSenha(s) ? "SIM" : "NAO");
            s = sc.nextLine();
        }
        sc.close();
    }

    /*verifica se a string de entrada e FIM*/
    public static boolean isFim(String s){
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }

    /*checa se a senha atende ao tamanho minimo de 8 e a todos os 4 requisitos*/
    public static boolean validaSenha(String s){
        if(s.length() >= 8 && maiuscula(s) && minuscula(s) && numero(s) && especial(s)){
            return true;
        }
        return false;
    }

    /*percorre a string buscando ao menos uma letra maiuscula*/
    public static boolean maiuscula(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
                return true;
            }
        }
        return false;
    }

    /*percorre a string buscando ao menos uma letra minuscula*/
    public static boolean minuscula(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z'){
                return true;
            }
        }
        return false;
    }

    /*percorre a string buscando ao menos um digito numerico*/
    public static boolean numero(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                return true;
            }
        }
        return false;
    }

    /*percorre a string checando se existe algum caractere especial ASCII*/
    public static boolean especial(String s){
        for(int i = 0; i < s.length(); i++){
            if((s.charAt(i) >= 32 && s.charAt(i) <= 47) || (s.charAt(i) >= 58 && s.charAt(i) <= 64) || (s.charAt(i) >= 91 && s.charAt(i) <= 96) || (s.charAt(i) >= 123 && s.charAt(i) <= 126)){
                return true;
            }
        }
        return false;
    }
}
