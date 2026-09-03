#include <stdio.h>

int length(char s[]);
int is_fim(char s[]);
int is_anagrama(char s[]);
char minuscula(char c);

int main(){
    char s[300];
    fgets(s, 300, stdin);
    while(!is_fim(s)){
        is_anagrama(s) ? printf("SIM\n") : printf("NAO\n");
        fgets(s, 300, stdin);
    }
    return 0;
}

/*calcula e retorna o numero de caracteres da string lida*/
int length(char s[]){
    int tamanho = 0;
    while(s[tamanho] != '\0' && s[tamanho] != '\n'){
        tamanho++;
    }
    return tamanho;
}

/*checa se a string e a palavra FIM*/
int is_fim(char s[]){
    return length(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M';
}

/*separa a entrada em duas palavras pelo espaco, compara o tamanho
 e usa um vetor de frequencia para checar se possuem as mesmas letras*/
int is_anagrama(char s[]){
    char s1[150];
    char s2[150];
    int i_s1 = 0;
    for(int i = 0; s[i] != ' '; i++){
        s1[i] = s[i];
        i_s1++;
    }
    s1[i_s1] = '\0';
    int segunda = i_s1 + 1;
    int i_s2 = 0;
    while(s[segunda] != '\n' && s[segunda] != '\0'){
        s2[i_s2] = s[segunda];
        segunda++;
        i_s2++;
    }
    s2[i_s2] = '\0';
    if(length(s1) != length(s2)){
        return 0;
    }
    int count[256] = {0};
    for(int i = 0; i < length(s1); i++){
        count[minuscula(s1[i])]++;
        count[minuscula(s2[i])]--;
    }
    for(int i = 0; i < 256; i++){
        if(count[i] != 0){
            return 0;
        }
    }
    return 1;
}

/*converte letras maiusculas para minusculas somando 32 no ASCII*/
char minuscula(char c){
    if(c >= 'A' && c<= 'Z'){
        return c + 32;
    }
    return c;
}
