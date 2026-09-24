#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int id;
    char marca[100];
    char modelo[100];
    int ano;
    char categoria[100];
    char combustivel[10][50]; // Suporta ate 10 tipos de combustivel
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

// Remove espacos e quebras de linha das pontas
void limparEspacos(char* dest, const char* src) {
    if (src == NULL) {
        dest[0] = '\0';
        return;
    }

    int inicio = 0;
    int fim = strlen(src) - 1;

    while (inicio <= fim && (src[inicio] <= ' ' || src[inicio] == '\r' || src[inicio] == '\n')) {
        inicio++;
    }
    while (fim >= inicio && (src[fim] <= ' ' || src[fim] == '\r' || src[fim] == '\n')) {
        fim--;
    }

    int k = 0;
    for (int i = inicio; i <= fim; i++) {
        dest[k++] = src[i];
    }
    dest[k] = '\0';
}

// Converte a String "AAAA-MM-DD" para struct Data
Data parseData(char* s) {
    Data d;
    d.ano = 0;
    d.mes = 0;
    d.dia = 0;

    char temp[100];
    limparEspacos(temp, s);

    if (strlen(temp) > 0) {
        sscanf(temp, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    }

    return d;
}

// Formata a data para DD/MM/YYYY
void formatData(Data d, char* buffer) {
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

// Parse da linha do CSV para a struct Veiculo
Veiculo parseVeiculo(char* s) {
    Veiculo v;
    v.numCombustiveis = 0;

    char tempLine[2048];
    strcpy(tempLine, s);

    // Array para guardar os campos separados por virgula
    char campos[15][200];
    int col = 0;

    char* token = strtok(tempLine, ",");
    while (token != NULL && col < 15) {
        limparEspacos(campos[col], token);
        col++;
        token = strtok(NULL, ",");
    }

    // Preenche os campos primitivos
    v.id = atoi(campos[0]);
    strcpy(v.marca, campos[1]);
    strcpy(v.modelo, campos[2]);
    v.ano = atoi(campos[3]);
    strcpy(v.categoria, campos[4]);

    // Trata campo multivalorado de combustivel (separado por ';')
    char combTemp[200];
    strcpy(combTemp, campos[5]);

    char* subToken = strtok(combTemp, ";");
    while (subToken != NULL && v.numCombustiveis < 10) {
        limparEspacos(v.combustivel[v.numCombustiveis], subToken);
        v.numCombustiveis++;
        subToken = strtok(NULL, ";");
    }

    v.cilindros = atoi(campos[6]);
    v.cilindrada = atof(campos[7]);
    strcpy(v.transmissao, campos[8]);
    strcpy(v.tracao, campos[9]);
    v.consumoCidade = atof(campos[10]);
    v.consumoEstrada = atof(campos[11]);
    v.co2 = atof(campos[12]);

    if (strcmp(campos[13], "true") == 0 || strcmp(campos[13], "1") == 0) {
        v.turbo = true;
    } else {
        v.turbo = false;
    }

    v.dataRegistro = parseData(campos[14]);

    return v;
}

// Formata a saida conforme a especificacao da PUC
void formatVeiculo(Veiculo v, char* buffer) {
    char combStr[500];
    strcpy(combStr, "[");

    for (int i = 0; i < v.numCombustiveis; i++) {
        strcat(combStr, v.combustivel[i]);
        if (i < v.numCombustiveis - 1) {
            strcat(combStr, ", ");
        }
    }
    strcat(combStr, "]");

    char dataStr[50];
    formatData(v.dataRegistro, dataStr);

    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## g ## %s ## %s ## g ## g ## g ## %s ## %s]",
            v.id, v.marca, v.modelo, v.ano, v.categoria, combStr, v.cilindros,
            v.transmissao, v.tracao, v.turbo ? "true" : "false", dataStr);

    // Ajusta os doubles formatando corretamente
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %g ## %s ## %s ## %g ## %g ## %g ## %s ## %s]",
            v.id, v.marca, v.modelo, v.ano, v.categoria, combStr, v.cilindros,
            v.cilindrada, v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada,
            v.co2, v.turbo ? "true" : "false", dataStr);
}

Veiculo* lerCsv(const char* caminhoArquivo, int* n) {
    FILE* arq = fopen(caminhoArquivo, "r");
    if (arq == NULL) {
        *n = 0;
        return NULL;
    }

    Veiculo* veiculos = (Veiculo*) malloc(20000 * sizeof(Veiculo));
    int count = 0;
    char linha[2048];

    // Pula o cabecalho
    if (fgets(linha, sizeof(linha), arq) != NULL) {
        // Ignorado
    }

    // Le as linhas ate o fim
    while (fgets(linha, sizeof(linha), arq) != NULL) {
        char temp[2048];
        limparEspacos(temp, linha);
        if (strlen(temp) > 0) {
            veiculos[count++] = parseVeiculo(linha);
        }
    }

    fclose(arq);
    *n = count;
    return veiculos;
}

int main() {
    int totalVeiculos = 0;
    Veiculo* veiculos = lerCsv("/tmp/VEICULOS.CSV", &totalVeiculos);

    int idBusca;
    char bufferOut[3000];

    // Leitura da entrada padrao ate receber -1
    while (scanf("%d", &idBusca) == 1 && idBusca != -1) {
        for (int i = 0; i < totalVeiculos; i++) {
            if (veiculos[i].id == idBusca) {
                formatVeiculo(veiculos[i], bufferOut);
                printf("%s\n", bufferOut);
                break;
            }
        }
    }

    // Libera a memoria alocada
    if (veiculos != NULL) {
        free(veiculos);
    }

    return 0;
}