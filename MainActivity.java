package com.example.quiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // ... (nenhuma mudança aqui, todas as variáveis de antes)
    private TextView textViewContador, textViewPergunta, textViewFeedback;
    private Button buttonOpcao1, buttonOpcao2, buttonOpcao3, buttonOpcao4, buttonProximo;
    private List<Button> botoesOpcao;
    private List<Questao> listaQuestoes;
    private int indiceQuestaoAtual;
    private int pontuacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewContador = findViewById(R.id.textViewContador);
        textViewPergunta = findViewById(R.id.textViewPergunta);
        textViewFeedback = findViewById(R.id.textViewFeedback);
        buttonOpcao1 = findViewById(R.id.buttonOpcao1);
        buttonOpcao2 = findViewById(R.id.buttonOpcao2);
        buttonOpcao3 = findViewById(R.id.buttonOpcao3);
        buttonOpcao4 = findViewById(R.id.buttonOpcao4);
        buttonProximo = findViewById(R.id.buttonProximo);

        botoesOpcao = new ArrayList<>(Arrays.asList(buttonOpcao1, buttonOpcao2, buttonOpcao3, buttonOpcao4));

        for (Button botao : botoesOpcao) {
            botao.setOnClickListener(this);
        }
        buttonProximo.setOnClickListener(this);

        iniciarQuiz();
    }


    // O código abaixo é apenas para manter a classe completa, a única alteração real está no método inicializarListaDeQuestoes()

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (botoesOpcao.stream().anyMatch(b -> b.getId() == id)) {
            Button botaoPressionado = (Button) v;
            int indiceOpcaoSelecionada = botoesOpcao.indexOf(botaoPressionado);
            verificarResposta(indiceOpcaoSelecionada);
        } else if (id == R.id.buttonProximo) {
            if (buttonProximo.getText().toString().equals("Reiniciar Quiz")) {
                iniciarQuiz();
            } else {
                carregarProximaQuestao();
            }
        }
    }

    private void iniciarQuiz() {
        pontuacao = 0;
        indiceQuestaoAtual = 0;
        inicializarListaDeQuestoes();
        textViewPergunta.setVisibility(View.VISIBLE);
        textViewContador.setVisibility(View.VISIBLE);
        for (Button botao : botoesOpcao) {
            botao.setVisibility(View.VISIBLE);
        }
        carregarProximaQuestao();
    }

    private void carregarProximaQuestao() {
        textViewFeedback.setText("");
        buttonProximo.setVisibility(View.GONE);
        for (Button botao : botoesOpcao) {
            botao.setEnabled(true);
            botao.setBackgroundColor(Color.parseColor("#6200EE"));
        }
        Questao questao = listaQuestoes.get(indiceQuestaoAtual);
        textViewContador.setText("Questão " + (indiceQuestaoAtual + 1) + "/" + listaQuestoes.size());
        textViewPergunta.setText(questao.getEnunciado());
        // A mágica acontece aqui: as opções já vêm embaralhadas do objeto Questao
        buttonOpcao1.setText(questao.getOpcoes().get(0));
        buttonOpcao2.setText(questao.getOpcoes().get(1));
        buttonOpcao3.setText(questao.getOpcoes().get(2));
        buttonOpcao4.setText(questao.getOpcoes().get(3));
    }

    private void verificarResposta(int indiceOpcaoSelecionada) {
        for (Button botao : botoesOpcao) {
            botao.setEnabled(false);
        }
        Questao questao = listaQuestoes.get(indiceQuestaoAtual);
        int respostaCorreta = questao.getRespostaCorreta();
        if (indiceOpcaoSelecionada == respostaCorreta) {
            pontuacao++;
            textViewFeedback.setText("Resposta Correta!");
            textViewFeedback.setTextColor(Color.parseColor("#008000"));
            botoesOpcao.get(indiceOpcaoSelecionada).setBackgroundColor(Color.parseColor("#008000"));
        } else {
            textViewFeedback.setText("Incorreto! A resposta era a " + (questao.getOpcoes().get(respostaCorreta)));
            textViewFeedback.setTextColor(Color.RED);
            botoesOpcao.get(indiceOpcaoSelecionada).setBackgroundColor(Color.RED);
            botoesOpcao.get(respostaCorreta).setBackgroundColor(Color.parseColor("#008000"));
        }
        indiceQuestaoAtual++;
        if (indiceQuestaoAtual >= listaQuestoes.size()) {
            new android.os.Handler().postDelayed(this::exibirResultadoFinal, 1500);
        } else {
            buttonProximo.setText("Próxima Pergunta");
            buttonProximo.setVisibility(View.VISIBLE);
        }
    }

    private void exibirResultadoFinal() {
        textViewPergunta.setVisibility(View.GONE);
        textViewContador.setVisibility(View.GONE);
        for (Button botao : botoesOpcao) {
            botao.setVisibility(View.GONE);
        }
        textViewFeedback.setText("Pontuação Final: " + pontuacao + " de " + listaQuestoes.size());
        textViewFeedback.setTextColor(Color.BLACK);
        buttonProximo.setText("Reiniciar Quiz");
        buttonProximo.setVisibility(View.VISIBLE);
    }



    private void inicializarListaDeQuestoes() {
        listaQuestoes = new ArrayList<>();

        // Agora passamos o TEXTO da resposta correta, e não mais o seu índice.
        // Usamos "new ArrayList<>(Arrays.asList(...))" para criar uma lista que pode ser embaralhada.

        listaQuestoes.add(new Questao("Qual o nome da clave que define a posição da nota Sol na pauta?",
                new ArrayList<>(Arrays.asList("Clave de Fá", "Clave de Sol", "Clave de Dó", "Clave de Pauta")), "Clave de Sol"));

        listaQuestoes.add(new Questao("O que faz um sustenido (#)?",
                new ArrayList<>(Arrays.asList("Aumenta a altura em um tom", "Diminui a altura em meio tom", "Aumenta a altura em meio tom", "Anula um efeito")), "Aumenta a altura em meio tom"));

        listaQuestoes.add(new Questao("Quais as notas da escala de Dó Maior?",
                new ArrayList<>(Arrays.asList("Dó, Ré, Mi, Fá, Sol, Lá, Si", "Dó, Ré, Mi, Fá#, Sol, Lá, Si", "Dó, Ré#, Mi, Fá, Sol, Lá, Si", "Dó, Ré, Mi, Fá, Sol#, Lá, Si")), "Dó, Ré, Mi, Fá, Sol, Lá, Si"));

        listaQuestoes.add(new Questao("Em um compasso 4/4, quantos tempos vale uma semínima?",
                new ArrayList<>(Arrays.asList("2 tempos", "4 tempos", "1 tempo", "Meio tempo")), "1 tempo"));

        listaQuestoes.add(new Questao("O que é um intervalo musical?",
                new ArrayList<>(Arrays.asList("Distância entre duas notas", "Intensidade de uma nota", "Duração de uma nota", "Timbre de um instrumento")), "Distância entre duas notas"));

        listaQuestoes.add(new Questao("Qual a função do bequadro (♮)?",
                new ArrayList<>(Arrays.asList("Anula o efeito de qualquer alteração", "Aumenta a altura em um tom", "Diminui a altura em meio tom", "Repete a última nota tocada")), "Anula o efeito de qualquer alteração"));

        listaQuestoes.add(new Questao("Qual é a escala relativa menor de Dó Maior?",
                new ArrayList<>(Arrays.asList("Lá menor", "Mi menor", "Sol menor", "Ré menor")), "Lá menor"));

        listaQuestoes.add(new Questao("O que é uma pauta ou pentagrama?",
                new ArrayList<>(Arrays.asList("O conjunto de 5 linhas e 4 espaços", "A velocidade da música", "O volume da música", "A afinação dos instrumentos")), "O conjunto de 5 linhas e 4 espaços"));

        listaQuestoes.add(new Questao("O que indica um ponto de aumento ao lado de uma figura musical?",
                new ArrayList<>(Arrays.asList("Aumenta a duração em metade do seu valor", "Diminui a duração da nota", "Indica uma pausa", "Une duas notas de mesma altura")), "Aumenta a duração em metade do seu valor"));

        listaQuestoes.add(new Questao("Quais são os três elementos fundamentais da música?",
                new ArrayList<>(Arrays.asList("Melodia, Ritmo e Harmonia", "Andamento, Intensidade e Timbre", "Altura, Duração e Intensidade", "Pauta, Clave e Compasso")), "Melodia, Ritmo e Harmonia"));


        Collections.shuffle(listaQuestoes);
    }
}
