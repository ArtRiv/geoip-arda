# Projeto 2 de Estruturas de Dados: Geolocalizador

No projeto 2 sua equipe deve implementar um geolocalizador IP, conforme descrito no Moodle:
* [Descrição do projeto](https://moodle.ifsc.edu.br/mod/page/view.php?id=257580)
  
## O geolocalizador como uma aplicação web

Neste repositório você tem uma aplicação web para acessar seu geolocalizador. Assim, o geolocalizador funcionará com uma API web, a qual recebe consultas na forma de requisições HTTP contendo o endereço IP a ser localizado, e responde com conteúdos JSON informando a localidade encontrada. 

Inicie a implementação pela classe App (_src/java/main/app/App.java_). Essa classe deve implementar o seguinte método para realizar a operação básica do geolocalizador 

| Método                          | Operação                                                        |
|---------------------------------|-----------------------------------------------------------------|
| busca_localidade(String ip)     | Obtém a localidade geográfica associado ao endereço ip          |

Leia os comentários escritos no código esqueleto dessa classe ... eles podem ajudar no desenvolvimento. Respeite as assinaturas dos métodos ali existentes: não modifique os parâmetros nem o tipo retornado por cada método. Note que no construtor da classe já há o código para acessar o arquivo _classes.csv_ (localizado em _src/java/resources_), o qual deve conter as descrições das classes de atendimento.

A classe _Localidade_ está predefinida, e deve ser usada para retornar o resultado do método _busca_localidade_. Ela não pode ser modificada !

Você pode criar novas classes que achar necessárias, as quais devem ficar na package _app_ (junto da classe _App_).

Coloque suas estruturas de dados na package _esd_ (_src/java/main/esd_). 

## Os arquivos de dados GeoLite

Os arquivos GeoLite devem ser copiados para a pasta _src/main/resources_. Certifique-se de que os seguintes arquivos estão lá:
* GeoLite2-City-Blocks-IPv4.csv
* GeoLite2-City-Locations-pt-BR.csv

Maiores informações sobre os conteúdos desses arquivos podem ser obtidas na [descrição do projeto](https://moodle.ifsc.edu.br/mod/page/view.php?id=257580).

**ATENÇÃO**: como esses arquivos são muito grandes, não é possível armazená-los no github !

O seguinte arquivo compactado contém esses arquivos de dados:
* [GeoLite Database](https://moodle.ifsc.edu.br/pluginfile.php/550385/mod_page/content/55/geoip.tgz)

## Testando seu geolocalizador

Para testar seu programa, execute-o e então acesse o seguinte link:
* [Meu GeoIP](http://localhost:8080/)
