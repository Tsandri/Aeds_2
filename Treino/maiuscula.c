#include <stdio.h>

int is_fim(char frase[]);

int verifica(char frase[]);

int main(){
    char frase[255];
    fgets(frase, 255, stdin);
    while(!is_fim(frase)){
        printf("%d\n", verifica(frase));
        fgets(frase, 255, stdin);   
    }
    return 0;
}
int is_fim(char frase[]){
    int tamanho = 0;
    for(int i = 0;frase[i] != '\0' && frase[i] != '\n';i++){
        tamanho++;
    }
    return tamanho == 3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M';
}
int verifica(char frase[]){
   int maiuscula = 0;
   for(int i = 0;frase[i] != '\0' && frase[i] != '\n';i++){
        if(frase[i] >= 'A' && frase[i] <= 'Z'){
            maiuscula++;
        }
   }
   return maiuscula;

}
