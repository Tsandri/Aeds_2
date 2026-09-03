#include <stdio.h>

int is_vog(char c);
int is_vog_rec(char s[], int i);
int is_conso(char c);
int is_conso_rec(char s[], int i);
int is_int(char c);
int is_int_rec(char s[], int i);
int is_real_rec(char s[], int i);
int is_real_rec_aux(char s[], int i, int pontos, int digitos);
int is_fim(char s[]);
int length(char s[]);

int main(){
    char s[1000];
    while(!is_fim(fgets(s, 1000, stdin))){
        is_vog_rec(s, 0) ? printf("SIM ") : printf("NAO ");
        is_conso_rec(s, 0) ? printf("SIM ") : printf("NAO ");
        is_int_rec(s, 0) ? printf("SIM ") : printf("NAO ");
        is_real_rec(s, 0) ? printf("SIM\n") : printf("NAO\n");
    }
    return 0;
}

/*percorre a cadeia ate encontrar quebra de linha ou nulo e retorna o total*/
int length(char s[]){
    int len = 0;
    while(s[len] != '\n' && s[len] != '\r' && s[len] != '\0'){
        len++;
    }
    return len;
}

/*verifica se a string possui 3 posicoes e corresponde a FIM*/
int is_fim(char s[]){
    return length(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M';
}

/*checa se o caractere atual e uma vogal maiuscula ou minuscula*/
int is_vog(char c){
    return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
           c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
}

/*checa se o caractere e uma letra e nao e vogal*/
int is_conso(char c){
    return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) && !is_vog(c);
}

/*verifica se o caractere e um digito numerico entre 0 e 9*/
int is_int(char c){
    int valor = c - '0';
    return valor >= 0 && valor <= 9;
}

/*percorre a string recursivamente garantindo que todos os caracteres sao vogais*/
int is_vog_rec(char s[], int i){
    int resp;
    if(length(s) == 0){
        resp = 0;
    }
    else if(i == length(s)){
        resp = 1;
    }
    else if(!is_vog(s[i])){
        resp = 0;
    }
    else{
        resp = is_vog_rec(s, i + 1);
    }
    return resp;
}

/*percorre a string recursivamente garantindo que todos os caracteres sao consoantes*/
int is_conso_rec(char s[], int i){
    int resp;
    if(length(s) == 0){
        resp = 0;
    }
    else if(i == length(s)){
        resp = 1;
    }
    else if(!is_conso(s[i])){
        resp = 0;
    }
    else{
        resp = is_conso_rec(s, i + 1);
    }
    return resp;
}

/*percorre a string recursivamente checando se todos os caracteres sao digitos*/
int is_int_rec(char s[], int i){
    int resp;
    if(length(s) == 0){
        resp = 0;
    }
    else if(i == length(s)){
        resp = 1;
    }
    else if(!is_int(s[i])){
        resp = 0;
    }
    else{
        resp = is_int_rec(s, i + 1);
    }
    return resp;
}

/*funcao recursiva auxiliar que valida digitos, conta separadores e exige ao menos um digito*/
int is_real_rec_aux(char s[], int i, int pontos, int digitos){
    int resp;
    if(pontos > 1){
        resp = 0;
    }
    else if(i == length(s)){
        resp = (digitos > 0) ? 1 : 0;
    }
    else if(s[i] == '.' || s[i] == ','){
        resp = is_real_rec_aux(s, i + 1, pontos + 1, digitos);
    }
    else if(is_int(s[i])){
        resp = is_real_rec_aux(s, i + 1, pontos, digitos + 1);
    }
    else{
        resp = 0;
    }
    return resp;
}

/*inicia a validacao de numero real com contadores zerados*/
int is_real_rec(char s[], int i){
    int resp;
    if(length(s) == 0){
        resp = 0;
    }
    else{
        resp = is_real_rec_aux(s, i, 0, 0);
    }
    return resp;
}