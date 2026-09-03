#include <stdio.h>
int inverte(int n);

int main(){
    int n;
    scanf("%d", &n);
    while(n != 0){
        printf("%d", inverte(n));
        n /= 10;
    }
    printf("\n");
    return 0;
}

int inverte(int n){
    return n % 10;
}
