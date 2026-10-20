#include <stdio.h>
//quick_sort
void trocar(int *a, int *b){
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int particionar(int array[], int inicio, int fim){
    int p = array[fim];
    int i = inicio - 1;
    for(int j = inicio; j < fim; j++){
        if(array[j] <= p){
            i++;
            trocar(&array[j], &array[i]);
        }
    }
    troca(&array[i + 1], array[j]);
    return i + 1;
}

void quick_sort(int array[], int inicio, int fim){
    if(inicio < fim){
        p = particionar(array, inicio, fim);
    }
    quick_sort(array, inicio, p - 1);
    quick_sort(array, p + 1, fim);
}
