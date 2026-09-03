import java.util.Scanner;

class Maiuscula{
    public static boolean isFim(String frase){
        return frase.length() == 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M';
    }
    public static int verifica(String frase){
        int maiuscula = 0;

        for(int i = 0;i < frase.length();i++){
            if(frase.charAt(i) >= 'A' && frase.charAt(i) <= 'Z'){
                maiuscula++;
            }
        }
        return maiuscula;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine();

        while(!isFim(frase)){
            System.out.println(verifica(frase));   
            frase = sc.nextLine();
        }
        sc.close();
    }
}
