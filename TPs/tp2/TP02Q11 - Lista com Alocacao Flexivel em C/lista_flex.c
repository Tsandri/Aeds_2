#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct { int ano; int mes; int dia; } Data;

typedef struct {
    int id;
    char marca[100];
    char modelo[100];
    int ano;
    char categoria[100];
    char combustivel[10][50];
    int numCombustiveis;
    int cilindros;
    double cilindrada;
    char transmissao[100];
    char tracao[100];
    double consumoCidade;
    double consumoEstrada;
    double co2;
    bool turbo;
    Data dataRegistro;
} Veiculo;

typedef struct Celula {
    Veiculo elemento;
    struct Celula* prox;
} Celula;

Celula* novaCelula(Veiculo elemento) {
    Celula* nova = (Celula*) malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

void trim(char* str) {
    if (str == NULL || strlen(str) == 0) return;
    int i = 0;
    while (str[i] == ' ' || str[i] == '\t' || str[i] == '\r' || str[i] == '\n') i++;
    if (i > 0) {
        int j = 0;
        while (str[i] != '\0') str[j++] = str[i++];
        str[j] = '\0';
    }
    int len = strlen(str);
    while (len > 0 && (str[len - 1] == ' ' || str[len - 1] == '\t' || str[len - 1] == '\r' || str[len - 1] == '\n')) {
        str[len - 1] = '\0';
        len--;
    }
}

Data parseData(char* s) {
    Data d = {0, 0, 0};
    if (s != NULL && strlen(s) >= 8) sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatData(Data d, char* buffer) {
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

int separarCampos(char* linha, char campos[15][200]) {
    int col = 0, pos = 0, len = strlen(linha);
    for (int i = 0; i <= len && col < 15; i++) {
        if (linha[i] == ',' || linha[i] == '\0' || linha[i] == '\n' || linha[i] == '\r') {
            campos[col][pos] = '\0';
            trim(campos[col]);
            col++;
            pos = 0;
            if (linha[i] == '\0' || linha[i] == '\n' || linha[i] == '\r') break;
        } else {
            if (pos < 199) campos[col][pos++] = linha[i];
        }
    }
    return col;
}

Veiculo parseVeiculo(char* s) {
    Veiculo v;
    v.numCombustiveis = 0;
    char campos[15][200];
    separarCampos(s, campos);

    v.id = atoi(campos[0]);
    strcpy(v.marca, campos[1]);
    strcpy(v.modelo, campos[2]);
    v.ano = atoi(campos[3]);
    strcpy(v.categoria, campos[4]);

    char tempComb[200];
    strcpy(tempComb, campos[5]);
    char* token = strtok(tempComb, ";");
    while (token != NULL && v.numCombustiveis < 10) {
        trim(token);
        strcpy(v.combustivel[v.numCombustiveis++], token);
        token = strtok(NULL, ";");
    }

    v.cilindros = atoi(campos[6]);
    v.cilindrada = atof(campos[7]);
    strcpy(v.transmissao, campos[8]);
    strcpy(v.tracao, campos[9]);
    v.consumoCidade = atof(campos[10]);
    v.consumoEstrada = atof(campos[11]);
    v.co2 = atof(campos[12]);
    v.turbo = (strcmp(campos[13], "true") == 0 || strcmp(campos[13], "1") == 0);
    v.dataRegistro = parseData(campos[14]);

    return v;
}

void formatVeiculo(Veiculo v, char* buffer) {
    char combStr[500];
    int pos = 0;
    combStr[pos++] = '[';
    for (int i = 0; i < v.numCombustiveis; i++) {
        for (int j = 0; v.combustivel[i][j] != '\0'; j++) combStr[pos++] = v.combustivel[i][j];
        if (i < v.numCombustiveis - 1) combStr[pos++] = ',';
    }
    combStr[pos++] = ']';
    combStr[pos] = '\0';

    char dataStr[50];
    formatData(v.dataRegistro, dataStr);

    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
            v.id, v.marca, v.modelo, v.ano, v.categoria, combStr, v.cilindros,
            v.cilindrada, v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada,
            v.co2, v.turbo ? "true" : "false", dataStr);
}

Veiculo* lerCsv(const char* caminhoArquivo, int* total) {
    FILE* arq = fopen(caminhoArquivo, "r");
    if (arq == NULL) { *total = 0; return NULL; }

    Veiculo* veiculos = (Veiculo*) malloc(20000 * sizeof(Veiculo));
    int count = 0;
    char linha[2048];

    if (fgets(linha, sizeof(linha), arq) != NULL) {}

    while (fgets(linha, sizeof(linha), arq) != NULL) {
        trim(linha);
        if (strlen(linha) > 0) veiculos[count++] = parseVeiculo(linha);
    }
    fclose(arq);
    *total = count;
    return veiculos;
}

Celula* primeiro;
Celula* ultimo;

void startLista() {
    Veiculo vVazia;
    primeiro = novaCelula(vVazia);
    ultimo = primeiro;
}

void inserirInicio(Veiculo v) {
    Celula* tmp = novaCelula(v);
    tmp->prox = primeiro->prox;
    primeiro->prox = tmp;
    if (primeiro == ultimo) ultimo = tmp;
}

void inserirFim(Veiculo v) {
    ultimo->prox = novaCelula(v);
    ultimo = ultimo->prox;
}

void inserir(Veiculo v, int pos) {
    Celula* i = primeiro;
    for (int j = 0; j < pos; j++, i = i->prox);
    Celula* tmp = novaCelula(v);
    tmp->prox = i->prox;
    i->prox = tmp;
    if (i == ultimo) ultimo = tmp;
}

Veiculo removerInicio() {
    Celula* tmp = primeiro->prox;
    Veiculo resp = tmp->elemento;
    primeiro->prox = tmp->prox;
    if (tmp == ultimo) ultimo = primeiro;
    free(tmp);
    return resp;
}

Veiculo removerFim() {
    Celula* i = primeiro;
    for (; i->prox != ultimo; i = i->prox);
    Veiculo resp = ultimo->elemento;
    free(ultimo);
    ultimo = i;
    ultimo->prox = NULL;
    return resp;
}

Veiculo remover(int pos) {
    Celula* i = primeiro;
    for (int j = 0; j < pos; j++, i = i->prox);
    Celula* tmp = i->prox;
    Veiculo resp = tmp->elemento;
    i->prox = tmp->prox;
    if (tmp == ultimo) ultimo = i;
    free(tmp);
    return resp;
}

void mostrarLista() {
    char buffer[3000];
    for (Celula* i = primeiro->prox; i != NULL; i = i->prox) {
        formatVeiculo(i->elemento, buffer);
        printf("%s\n", buffer);
    }
}

Veiculo* buscarBase(Veiculo* base, int total, int id) {
    for (int i = 0; i < total; i++) {
        if (base[i].id == id) return &base[i];
    }
    return NULL;
}

int main() {
    startLista();
    int totalBase = 0;
    Veiculo* base = lerCsv("/tmp/veiculos.csv", &totalBase);
    if (base == NULL || totalBase == 0) base = lerCsv("/tmp/VEICULOS.CSV", &totalBase);

    int idBusca;

    // Parte 1
    while (scanf("%d", &idBusca) == 1 && idBusca != -1) {
        Veiculo* v = buscarBase(base, totalBase, idBusca);
        if (v != NULL) inserirFim(*v);
    }

    // Parte 2
    int nComandos;
    if (scanf("%d", &nComandos) == 1) {
        for (int i = 0; i < nComandos; i++) {
            char cmd[10];
            scanf("%s", cmd);

            if (strcmp(cmd, "II") == 0) {
                int id; scanf("%d", &id);
                Veiculo* v = buscarBase(base, totalBase, id);
                if (v != NULL) inserirInicio(*v);
            } else if (strcmp(cmd, "I") == 0 || strcmp(cmd, "I*") == 0) {
                int pos, id; scanf("%d %d", &pos, &id);
                Veiculo* v = buscarBase(base, totalBase, id);
                if (v != NULL) inserir(*v, pos);
            } else if (strcmp(cmd, "IF") == 0) {
                int id; scanf("%d", &id);
                Veiculo* v = buscarBase(base, totalBase, id);
                if (v != NULL) inserirFim(*v);
            } else if (strcmp(cmd, "RI") == 0) {
                Veiculo r = removerInicio();
                printf("(R)%s %s\n", r.marca, r.modelo);
            } else if (strcmp(cmd, "R") == 0 || strcmp(cmd, "R*") == 0) {
                int pos; scanf("%d", &pos);
                Veiculo r = remover(pos);
                printf("(R)%s %s\n", r.marca, r.modelo);
            } else if (strcmp(cmd, "RF") == 0) {
                Veiculo r = removerFim();
                printf("(R)%s %s\n", r.marca, r.modelo);
            }
        }
    }

    mostrarLista();

    if (base != NULL) free(base);
    return 0;
}
