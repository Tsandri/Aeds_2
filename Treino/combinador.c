#include <stdio.h>
int length(char s[]);
char[] combinator(char s[]);

int main(){
    char s[300];
    while(fgets(s, 300, stdin) != EOF){
        printf("%s", combinator(s));    
    }
       
}
int length(char s[]){
    int len = 0;
    while(s[len] != '\n' && s[len] != '\0'){
        len++;
    }
}
char[] combinator(char s[]){
    int espaco = 1;
    char new_s[300];
    while(frase[espaco] != ' '){
        espaco++;
    }
    for(int i = 0; i <= tamanho(s); i++){
        new_s[i] = s[i];
        
    }
}
