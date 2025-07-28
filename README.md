# Projeto 2 de Estruturas de Dados: Geolocalizador

## Grupo:

#### Caio Eduardo de Aguiar
#### Arthur Felaço Matos de Souza

## Estruturas de Dados Utilizadas

### Tabela Hash (**`TabHash`**)

- **Finalidade:** Armazenar as localidades (país, cidade) associadas a cada identificador GeoNameID.
- **Motivo da escolha:** A tabela hash permite buscas rápidas (O(1) na média) por chave, essencial para obter a localidade associada a um bloco de IP (como era chave e valor pensamos em usar pois cumpre exatamente o que propoe).
- **Implementação:** Usado para guardar chave e valor de país e cidade.

### Árvore Binária de Pesquisa (**`APB`**)

- **Finalidade:** Armazenar os blocos de IP (faixas), permitindo a busca eficiente do bloco que contém um determinado IP.
- **Motivo da escolha:** A árvore binária balanceada permite buscas logarítmicas (O(log n)), o que é fundamental dado o grande volume de faixas de IP (milhões de registros).
- **Balanceamento:** A árvore é construída de forma balanceada a partir dos dados ordenados, garantindo desempenho ótimo nas consultas.

## Justificativas das Escolhas

- **Eficiência:** Dado o alto volume de dados (milhões de faixas de IP e centenas de milhares de localidades), estruturas eficientes são necessárias para garantir tempos de resposta adequados, tentamos usar com listaEncadeada antes de mudarmos para listaSequencial, porém era tão lento que tentei rodar por 1 hora e não houve resultado.
- **Escalabilidade:** A escolha por uma árvore binária balanceada e tabela hash permite que o sistema escale para grandes volumes de dados sem degradação significativa de desempenho e por cumprir exatamente o que precisávamos (faixa e chave:valor).

### Diagrama de Classes

```mermaid
classDiagram
    direction LR

    subgraph app
        class App {
            -IPV4_BLOCKS: String
            -CITY_LOCATIONS: String
            -faixaIP: APB~FaixaIP~
            -localizacoes: TabHash~Integer, Localidade~
            +App()
            -encontrarFaixaIP(): FaixaIP
            -encontrarFaixaIPRec(): FaixaIP
            +busca_localidade(ip: String): Localidade
        }

        class FaixaIP {
            -inicioIP: long
            -fimIP: long
            -idGeoname: int
            +faixaIP(inicioIP: long, fimIP: long, idGeoname: int)
            +obtemInicioIP(): long
            +obtemFimIP(): long
            +obtemidGeoname(): int
            +contains(ip: long): boolean
            +compareTo(other: faixaIP): int
            +toString(): String
        }

        class IPUtil {
            +static ipParaLongo(ipAddress: String): long
            +static obtemFimIpDeCidr(startIP: long, cidrMask: int): long
            +static cidrParaFaixa(cidr: String, idGeoname: int): FaixaIP
            +static longoParaIp(ip: long): String
        }

        class Localidade {
            <<record>>
            +pais: String
            +local: String
        }
    end

    subgraph esd
        class TabHash~K, T~ {
            -tab: ListaSequencial~ListaSequencial~Par~~
            -len: int
            -fatorCarga: double
            -defcap: int
            -linhas: int
            +TabHash()
            -calc_hash(chave: K): int
            -expande(): void
            +obtem_linha(chave: K): ListaSequencial~Par~
            +obtem(chave: K): T
            +adiciona(chave: K, valor: T): void
            +remove(chave: K): void
            +contem(chave: K): boolean
            +esta_vazia(): boolean
            +comprimento(): int *
        }

        class APB~T~ {
            -raiz: NodoAPB~T~
            -len: int
            +adiciona(val: T)
            +remove(val: T)
            +procura(val: T): NodoAPB~T~
            +obtem_raiz(): T
            +esta_vazia(): boolean
            +menores_que(val: T): ListaSequencial~T~
            +maiores_que(val: T): ListaSequencial~T~
            +altura(): int
            +tamanho(): int
            +limpa()
            +balanceia()
            +constroiDeListaOrdenada(elementos: ListaSequencial~T~)
            +faixa(min: T, max: T): ListaSequencial~T~
        }
        class NodoAPB~T~ {
            +valor: T
            +esq: NodoAPB~T~
            +dir: NodoAPB~T~
            +pai: NodoAPB~T~
            <<static>>
        }
        class ListaSequencial~T~ {
            +adiciona(elemento: T)
            +obtem(indice: int): T
            +substitui(indice: int, valor: T)
            +comprimento(): int
            +esta_vazia(): boolean
            +limpa()
            +ordena()
        }

        class ListaSequencial~T~ {
            -area: T[]
            -len: int
            -defcap: int
            +ListaSequencial()
            +expande(): void
            +esta_vazia(): boolean
            +capacidade(): int
            +adiciona(elemento: T): void
            +insere(indice: int, elemento: T): void
            +remove(indice: int): void
            +procura(valor: T): int
            +obtem(indice: int): T
            +substitui(indice: int, valor: T): void
            +comprimento(): int
            +limpa(): void
        }
    end

    %% Relações
    App ..> ListaSequencial
    App ..> TabHash
    App ..> faixaIP
    App ..> Localidade
    App ..> IPUtil
    APB~T~ --> NodoAPB~T~

    TabHash ..> ListaSequencial
    APB ..> ListaSequencial
```