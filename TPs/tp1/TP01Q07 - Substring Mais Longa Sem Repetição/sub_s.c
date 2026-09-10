#include <stdio.h>
#include <stdlib.h>

int substringMaisLonga(char s[], int tamanho);
int isFim(char frase[], int tamanho);

/*testa todas as substrings possiveis marcando caracteres vistos
 em um vetor booleano e guarda o maior tamanho sem repeticao*/
int substringMaisLonga(char s[], int tamanho) {
    int max_len = 0;
    int resp = 0;

    for (int i = 0; i < tamanho; i++) {
        int visitado[256] = {0};
        int len_atual = 0;

        for (int j = i; j < tamanho; j++) {
            if (visitado[(unsigned char)s[j]] == 1) {
                break;
            }
            visitado[(unsigned char)s[j]] = 1;
            len_atual++;
        }

        if (len_atual > max_len) {
            max_len = len_atual;
        }
    }

    resp = max_len;
    return resp;
}

/*checa se a cadeia de entrada e FIM*/
int isFim(char frase[], int tamanho){
    int resp = 0;
    if(tamanho == 3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M'){
        resp = 1;
    }
    return resp;
}

int main() {
    char frase[500];
    int tamanho = 0;

    fgets(frase, 500, stdin);
    while (frase[tamanho] != '\0') {
        if (frase[tamanho] == '\n' || frase[tamanho] == '\r') {
            frase[tamanho] = '\0';
            break;
        }
        tamanho++;
    }

    while (isFim(frase, tamanho) == 0) {
        printf("%d\n", substringMaisLonga(frase, tamanho));

        tamanho = 0;
        fgets(frase, 500, stdin);
        while (frase[tamanho] != '\0') {
            if (frase[tamanho] == '\n' || frase[tamanho] == '\r') {
                frase[tamanho] = '\0';
                break;
            }
            tamanho++;
        }
    }

    return 0;
}
