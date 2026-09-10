#include <stdio.h>

int soma(int n);

int main(){
    int n;
    while(scanf("%d", &n) != EOF){
        printf("%d\n", soma(n));
    }
    return 0;
}

/*acumula o resto da divisao por 10 e divide o numero por 10
 a cada iteracao somando todos os seus digitos*/
int soma(int n){
    int soma = 0;
    while(n != 0){
        soma += n % 10;
        n /= 10;
    }
    return soma;
}
