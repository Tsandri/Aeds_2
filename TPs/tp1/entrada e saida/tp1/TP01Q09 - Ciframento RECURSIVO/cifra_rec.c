#include <stdio.h>

int length(char s[]);
int is_fim(char s[]);
void cifra_rec(char s[], int i);
void cifra(char s[]);

int main(){
    char s[1000];
    fgets(s, 1000, stdin);
    while(!is_fim(s)){
        cifra(s);
        printf("%s\n", s);
        fgets(s, 1000, stdin);
    }
    return 0;
}

/*percorre a cadeia tratando o \n e retorna o tamanho da string*/
int length(char s[]){
    int tam = 0;
    while(s[tam] != '\0'){
        if(s[tam] == '\n'){
            s[tam] = '\0';
            break;
        }
        tam++;
    }
    return tam;
}

/*verifica se a string recebida e FIM*/
int is_fim(char s[]){
    return length(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M';
}

/*percorre a string recursivamente somando 3 ao codigo ASCII
 de cada caractere ate encontrar o final da cadeia*/
void cifra_rec(char s[], int i){
    if(s[i] != '\0'){
        s[i] += 3;
        cifra_rec(s, i + 1);
    }
}

/*funcao que inicia a chamada recursiva a partir do indice zero*/
void cifra(char s[]){
    cifra_rec(s, 0);
}
