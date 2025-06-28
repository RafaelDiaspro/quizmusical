package com.example.quiz;

import java.util.Collections;
import java.util.List;

public class Questao {
    private String enunciado;
    private List<String> opcoes;
    private int respostaCorreta; // O índice da resposta correta APÓS o embaralhamento

    /**
     * Construtor atualizado para embaralhar as respostas.
     * @param enunciado O texto da pergunta.
     * @param opcoes A lista de todas as opções de resposta.
     * @param textoRespostaCorreta O TEXTO exato da resposta correta.
     */
    public Questao(String enunciado, List<String> opcoes, String textoRespostaCorreta) {
        this.enunciado = enunciado;
        this.opcoes = opcoes;

        // Embaralha a lista de opções de forma aleatória
        Collections.shuffle(this.opcoes);

        // Encontra o novo índice da resposta correta na lista embaralhada
        this.respostaCorreta = this.opcoes.indexOf(textoRespostaCorreta);
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }
}
