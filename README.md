Robô Aspirador

Exercício de Strategy da disciplina de Programação Orientada a Objetos.

Nesse projeto um robô aspirador limpa uma sala com sofá, mesa, rack, estante e poltrona. Ele tem bateria para 300 passos e, a cada passo, decide para onde ir. Antes, os modos de limpeza (Aleatória e Ricochete) estavam escritos dentro da classe Robo, num if atrás do outro. Eu tirei esses modos de dentro do robô e transformei cada um em uma rota (padrão Strategy), que o robô recebe pronta e pode trocar a qualquer momento. Também criei o modo novo, o Zigue-zague.

Como rodar
Abra o projeto na IDE (usei o VS Code).
Rode a classe RoboApplication (ou ./mvnw spring-boot:run no terminal).
Acesse http://localhost:8080.

Escolha a rota, clique em Ligar e veja o robô trabalhar. O botão Ranking coloca cada rota numa sala nova, com a bateria cheia, e compara quanto cada uma limpou. A tela carrega o three.js da internet, então precisa de conexão.

O que eu fiz
1. Interface Rota

Criei a interface com dois métodos: getNome(), que aparece no menu e no ranking, e proximaDirecao(Sala sala, Posicao atual), que diz para onde o robô vai no próximo passo.

2. Classes Aleatoria e Ricochete

Levei o código que estava dentro do Robo para cada uma:

Aleatória: sorteia uma direção a cada passo.
Ricochete: segue reto e, quando bate, sorteia outra direção. Ela guarda a direção atual como atributo da própria classe.
3. Classe Robo
o atributo String modo virou Rota rota;
o construtor recebe uma Rota;
setModo(String) virou setRota(Rota);
o andar() pergunta a direção para a rota;
o proximaDirecao(), o sortearDirecao() e todos os ifs saíram do Robo;
o getNomeDaRota() devolve o nome que vem da rota.
4. Classe Simulador
o modos() virou rotas(), que devolve uma lista de objetos Rota;
o nomesDasRotas() monta a lista de nomes a partir das rotas;
o escolherRota() procura a rota pelo nome e entrega para o robô;
o ranking() testa cada rota numa sala nova;
o reiniciar() cria uma rota nova com o mesmo nome, para o estado dela voltar do zero.

O rotas() cria objetos novos a cada chamada, de propósito (explico no "Para pensar").

5. Rota ZigueZague

Ela anda na horizontal enquanto tiver caminho livre. Quando bate, inverte o sentido horizontal (direita ↔ esquerda) e muda de linha: desce, e se não der para descer, sobe (e vice-versa). Para isso ela guarda três coisas: o sentido horizontal, o sentido vertical e um boolean que avisa que acabou de mudar de linha (assim, no passo seguinte, ela volta a andar na horizontal).

Adicionei ela na lista do Simulador e não precisei mexer no Robo. Ela aparece sozinha no menu e no ranking.

Estrutura (pacote model)
Classe	Para que serve
Direcao	CIMA, BAIXO, ESQUERDA, DIREITA
Posicao	Uma célula da sala. posicao.mover(direcao) devolve a célula vizinha.
Sala	Móveis e sujeira. sala.livre(posicao) diz se dá para ir até a célula.
Rota	Interface da estratégia de limpeza.
Aleatoria	Rota que sorteia uma direção a cada passo.
Ricochete	Rota que segue reto e sorteia outra direção ao bater.
ZigueZague	Rota que vai e volta pelas linhas da sala.
Robo	Anda, limpa, conta passos e batidas. Não tem mais if de modo.
Simulador	Liga o robô à tela e monta o ranking.

Não mexi em nada de controller, templates e static.

Resultados

Rodei o ranking várias vezes. O Zigue-zague deu sempre o mesmo resultado, porque ele não usa sorteio. As outras duas variam a cada rodada.

Rota	Sala limpa (300 passos)
Aleatória	entre 27% e 57%
Ricochete	entre 31% e 49%
Zigue-zague	84% (só 2 batidas)

O Zigue-zague não chega a 100% porque a mesa divide a sala: ele acaba subindo e descendo só pelo lado direito e nunca passa pela área à esquerda da mesa.

Como saber se terminou
 O Robo não tem nenhum if sobre o modo de limpeza.
 O menu mostra Aleatória, Ricochete e Zigue-zague.
 Dá para trocar a rota com o robô ligado, no meio da limpeza.
 No ranking, o Zigue-zague limpa bem mais que as outras duas.
Desafio

Não fiz o desafio. Uma ideia para o futuro seria uma rota que prefere as células ainda sujas (sala.estaSuja(posicao)), que poderia resolver a parte da sala que o Zigue-zague não alcança.

Para pensar

O que mudou no Robo quando você criou o ZigueZague? Nada. Só criei a classe nova e coloquei na lista do Simulador. Esse é o ganho do Strategy: o Robo não precisa saber quais rotas existem, ele só pede a próxima direção para a rota que recebeu. Antes, cada modo novo era mais um if dentro dele.

Por que o Ricochete guarda a direção dentro dele, e não no Robo? Porque essa informação faz parte da estratégia do Ricochete (seguir reto até bater). Se ficasse no Robo, ele teria que cuidar de detalhes de cada rota, e rotas novas (como o Zigue-zague, que guarda outras coisas) teriam que se encaixar nele. Do jeito atual, cada rota cuida do próprio estado.

O que aconteceria no ranking se rotas() devolvesse sempre os mesmos objetos? As rotas guardam estado (a direção do Ricochete, o sentido e a mudança de linha do Zigue-zague). Se os objetos fossem sempre os mesmos, cada teste do ranking começaria de onde a rodada anterior parou, e o robô que está na tela e o do ranking dividiriam a mesma rota. O resultado ficaria enviesado e diferente a cada vez. Criando objetos novos, cada teste começa do zero.

1. O que mudou no `Robo` quando você criou o `ZigueZague`?
2. Por que o `Ricochete` guarda a direção dentro dele, e não no `Robo`?
3. O que aconteceria no ranking se `rotas()` devolvesse sempre os mesmos objetos?
