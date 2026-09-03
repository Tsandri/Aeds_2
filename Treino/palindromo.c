#include <stdio.h>
int is_fim(char f[]);
int tamanho(char f[]);
int is_palindromo(char f[]);

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
int tamanho(char f[]){
    int tamanho = 0;
    for(int i = 0;f[i] != '\n' && f[i] != '\0' && f[i] != '\r';i++){
        tamanho++;
    }
    return tamanho;
}
int is_fim(char f[]){
    return tamanho(f) == 3 && f[0] == 'F' && f[1] == 'I' && f[2] == 'M';
}
int is_palindromo(char f[]){
    int tam = tamanho(f);
    for(int i = 0;i < tam / 2;i++){
        if(f[i] != f[tam - 1 - i]){
            return 0;
        }
    }
    return 1;
}
