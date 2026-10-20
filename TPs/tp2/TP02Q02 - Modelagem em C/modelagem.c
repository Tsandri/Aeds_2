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

// Remove espacos e quebras de linha das pontas
void trim(char* str) {
    if (str == NULL || strlen(str) == 0) return;

    int i = 0;
    while (str[i] == ' ' || str[i] == '\t' || str[i] == '\r' || str[i] == '\n') {
        i++;
    }

    if (i > 0) {
        int j = 0;
        while (str[i] != '\0') {
            str[j++] = str[i++];
        }
        str[j] = '\0';
    }

    int len = strlen(str);
    while (len > 0 && (str[len - 1] == ' ' || str[len - 1] == '\t' || str[len - 1] == '\r' || str[len - 1] == '\n')) {
        str[len - 1] = '\0';
        len--;
    }
}

// Parse de data no formato AAAA-MM-DD
Data parseData(char* s) {
    Data d = {0, 0, 0};
    if (s != NULL && strlen(s) >= 8) {
        sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    }
    return d;
}

// Formata data em DD/MM/YYYY
void formatData(Data d, char* buffer) {
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

// Separa campos por virgula tratando colunas vazias de forma segura
int separarCampos(char* linha, char campos[15][200]) {
    int col = 0;
    int pos = 0;
    int len = strlen(linha);

    for (int i = 0; i <= len && col < 15; i++) {
        if (linha[i] == ',' || linha[i] == '\0' || linha[i] == '\n' || linha[i] == '\r') {
            campos[col][pos] = '\0';
            trim(campos[col]);
            col++;
            pos = 0;
            if (linha[i] == '\0' || linha[i] == '\n' || linha[i] == '\r') break;
        } else {
            if (pos < 199) {
                campos[col][pos++] = linha[i];
            }
        }
    }
    return col;
}

// Parse da linha inteira do CSV para struct Veiculo
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

    // Trata o campo multivalorado de combustiveis separado por ';'
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

    if (strcmp(campos[13], "true") == 0 || strcmp(campos[13], "1") == 0) {
        v.turbo = true;
    } else {
        v.turbo = false;
    }

    v.dataRegistro = parseData(campos[14]);

    return v;
}

// Formata o veiculo
void formatVeiculo(Veiculo v, char* buffer) {
    char combStr[500];
    int pos = 0;

    combStr[pos++] = '[';

    for (int i = 0; i < v.numCombustiveis; i++) {
        // Copia o nome do combustível caractere por caractere
        for (int j = 0; v.combustivel[i][j] != '\0'; j++) {
            combStr[pos++] = v.combustivel[i][j];
        }

        // Adiciona vírgula entre os elementos (sem espaço)
        if (i < v.numCombustiveis - 1) {
            combStr[pos++] = ',';
        }
    }

    combStr[pos++] = ']';
    combStr[pos] = '\0'; // Finaliza a string

    char dataStr[50];
    formatData(v.dataRegistro, dataStr);

    // Formata a string final diretamente
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
            v.id, v.marca, v.modelo, v.ano, v.categoria, combStr, v.cilindros,
            v.cilindrada, v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada,
            v.co2, v.turbo ? "true" : "false", dataStr);
}

Veiculo* lerCsv(const char* caminhoArquivo, int* total) {
    FILE* arq = fopen(caminhoArquivo, "r");
    if (arq == NULL) {
        *total = 0;
        return NULL;
    }

    Veiculo* veiculos = (Veiculo*) malloc(20000 * sizeof(Veiculo));
    int count = 0;
    char linha[2048];

    // Descarta cabecalho
    if (fgets(linha, sizeof(linha), arq) != NULL) {}

    while (fgets(linha, sizeof(linha), arq) != NULL) {
        trim(linha);
        if (strlen(linha) > 0) {
            veiculos[count++] = parseVeiculo(linha);
        }
    }

    fclose(arq);
    *total = count;
    return veiculos;
}

int main() {
    int totalVeiculos = 0;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &totalVeiculos);
    if (veiculos == NULL || totalVeiculos == 0) {
        veiculos = lerCsv("/tmp/VEICULOS.CSV", &totalVeiculos);
    }

    int idBusca;
    char bufferOut[3000];

    while (scanf("%d", &idBusca) == 1 && idBusca != -1) {
        for (int i = 0; i < totalVeiculos; i++) {
            if (veiculos[i].id == idBusca) {
                formatVeiculo(veiculos[i], bufferOut);
                printf("%s\n", bufferOut);
                break;
            }
        }
    }

    if (veiculos != NULL) {
        free(veiculos);
    }

    return 0;
}
