#include <stdio.h>

int verifica(int n);

int main(){
    int n, num;
    scanf("%d", &n);

    for(int i = 0; i < n; i++){
        scanf("%d", &num);
        if(verifica(num)){
            printf("PAR\n");
        }
        else{
            printf("IMPAR\n");
        }
    }
    return 0;
}

int verifica(int n){
    if(n % 2 == 0){
        return 1;
    }
    else{
        return 0;
    }
}
