import java.util.Scanner;

class Is{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!isFim(s)){
            System.out.print(isVogal(s) ? "SIM " : "NAO ");
            System.out.print(isConsoante(s) ? "SIM " : "NAO ");
            System.out.print(isInt(s) ? "SIM " : "NAO ");
            System.out.println(isReal(s) ? "SIM" : "NAO");
            s = sc.nextLine();
        }
        sc.close();
    }

    /*verifica se a string e a condicao de parada FIM*/
    public static boolean isFim(String s){
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }

    /*percorre a string e retorna false se encontrar qualquer
     caractere que nao seja uma vogal maiuscula ou minuscula*/
    public static boolean isVogal(String s){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            boolean vogal = c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
            if(!vogal){
                return false;
            }
        }
        return true;
    }

    /*percorre a string checando se todos os caracteres sao letras
     e garante que nenhum deles seja uma vogal*/
    public static boolean isConsoante(String s){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            boolean letra = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
            boolean vogal = c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
            if(!letra || vogal){
                return false;
            }
        }
        return true;
    }

    /*percorre a string e verifica se todos os caracteres estao
     entre 0 e 9 na tabela ASCII*/
    public static boolean isInt(String s){
        for(int i = 0;i < s.length(); i++){
            if(s.charAt(i) < '0' || s.charAt(i) > '9'){
                return false;
            }
        }
        return true;
    }

    /*percorre a string contando separadores decimais (ponto ou virgula)
     e retorna true se houver no maximo um separador e apenas digitos*/
    public static boolean isReal(String s){
        int ponto = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '.' || s.charAt(i) == ','){
                ponto++;
            }
            else if(s.charAt(i) < '0' || s.charAt(i) > '9'){
                return false;
            }
        }
        return ponto <= 1;
    }
}
