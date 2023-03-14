package com.example.desembaralhar

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _pontos = 0
    val pontos : Int
    get() = _pontos

    private var _contagemPalavraAtual = 0
    val contagemPalavraAtual : Int
    get() = _contagemPalavraAtual

    private lateinit var _palavraEmbaralhadaAtual : String
    val palavraEmbaralhadaAtual : String
    get() = _palavraEmbaralhadaAtual

    private var listPalavras : MutableList<String> = mutableListOf()
    private lateinit var palavraAtual : String

    init {
        obterAProximaPalavra()
    }

    private fun obterAProximaPalavra() {
        palavraAtual = allWordsList.random()
        val palavraTemporaria = palavraAtual.toCharArray()
        palavraTemporaria.shuffle()

        while (String(palavraTemporaria).equals(palavraAtual, false)) {
            palavraTemporaria.shuffle()
        }
        if (listPalavras.contains(palavraAtual)){
            proximaPalavra()
        } else {
            _palavraEmbaralhadaAtual = String(palavraTemporaria)
            ++_contagemPalavraAtual
            listPalavras.add(palavraAtual)
        }
    }

     fun proximaPalavra() : Boolean {
        return if (_contagemPalavraAtual < MAX_NO_OF_WORDS){
            obterAProximaPalavra()
            true
        } else false
    }

    fun reinicializarDados(){
        _pontos = 0
        _contagemPalavraAtual = 0
        listPalavras.clear()
        obterAProximaPalavra()
    }

    fun aumentaPontuacao() { _pontos += SCORE_INCREASE }

    fun aPalavraDoUsuarioEstaCorreta(palavra : String): Boolean {
        if (palavra.equals(palavraAtual, true)) {
            aumentaPontuacao()
            return true
        }
        return false
    }
}