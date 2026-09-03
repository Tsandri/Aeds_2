#include <stdio.h>

int is_fim(char nome[]);
int tamanho(char nome[]);

int main(){
    char nome[110];
    fgets(nome, 110, stdin);
    while(is_fim(nome) == 0){
        printf("%d\n", tamanho(nome));
        fgets(nome, 110, stdin);
    }
}

int tamanho(char nome[]){
    int i;
    for(i = 0; nome[i] != '\0' && nome[i] != '\n'; i++);
    return i;
}

int is_fim(char nome[]){
    if(tamanho(nome) == 3 && nome[0] == 'F' && nome[1] == 'I' && nome[2] == 'M'){
        return 1;
    }
    else{
        return 0;
    }
}
