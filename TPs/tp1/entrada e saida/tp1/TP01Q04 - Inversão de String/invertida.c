#include <stdio.h>

void inverte(char s[], int tamanho);
int is_fim(char s[], int tamanho);
int tamanho(char s[]);

int main(){
    char s[300];
    fgets(s, 300, stdin);
    int tam = tamanho(s);
    while(!is_fim(s, tam)){
        inverte(s, tam);
        printf("%s", s);
        fgets(s, 300, stdin);
        tam = tamanho(s);
    }
    return 0;
}

/*percorre o vetor ate achar quebra de linha ou fim de string
 e retorna a quantidade de caracteres*/
int tamanho(char s[]){
    int tamanho = 0;
    while(s[tamanho] != '\n' && s[tamanho] != '\0'){
        tamanho++;
    }
    return tamanho;
}

/*verifica se a string possui 3 posicoes e equivale a FIM*/
int is_fim(char s[], int tamanho){
    return tamanho == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M';
}

/*usa dois ponteiros (um no inicio e outro no fim) e troca
 os caracteres de lugar ate se cruzarem no meio*/
void inverte(char s[], int tamanho){
    int inicio = 0;
    int fim = tamanho - 1;
    while(inicio < fim){
        char temp = s[inicio];
        s[inicio] = s[fim];
        s[fim] = temp;
        inicio++;
        fim--;
    }
}
