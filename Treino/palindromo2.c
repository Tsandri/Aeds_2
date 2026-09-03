#include <stdio.h>
int tamanho(char frase[]);
int is_fim(char frase[]);
int is_palindromo(char frase[]);

int main(){
    char frase[1000];
    fgets(frase, 1000, stdin);
    while(!is_fim(frase)){
        if(is_palindromo(frase)){
            printf("SIM\n");
        }
        else{
            printf("NAO\n");
        }
        fgets(frase, 1000, stdin);
    }
    return 0;
}
int tamanho(char frase[]){
    int tamanho = 0;
    while(frase[tamanho] != '\n' && frase[tamanho] != '\0'){
        tamanho++;
    }
    return tamanho;
}
int is_fim(char frase[]){
    return tamanho(frase) == 3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M';
}
int is_palindromo(char frase[]){
    int palindromo = 1;
    int tam = tamanho(frase);
    for(int i = 0; i < tam / 2; i++){
        if(frase[i] != frase[tam - 1 - i]){
            palindromo = 0;
            i = tam;
        }
    }
    return palindromo;
}
