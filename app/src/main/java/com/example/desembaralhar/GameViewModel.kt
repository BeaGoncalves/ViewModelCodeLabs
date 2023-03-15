package com.example.desembaralhar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _pontos = MutableLiveData(0)
    val pontos : LiveData<Int>
    get() = _pontos

    private val _contagemPalavraAtual = MutableLiveData(0)
    val contagemPalavraAtual : LiveData<Int>
    get() = _contagemPalavraAtual

    private val _palavraEmbaralhadaAtual = MutableLiveData<String>()
    val palavraEmbaralhadaAtual : LiveData<String>
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
            _palavraEmbaralhadaAtual.value = String(palavraTemporaria)
            _contagemPalavraAtual.value = (_contagemPalavraAtual.value)?.inc()
            listPalavras.add(palavraAtual)
        }
    }

     fun proximaPalavra() : Boolean {
        return if (_contagemPalavraAtual.value!! < MAX_NO_OF_WORDS){
            obterAProximaPalavra()
            true
        } else false
    }

    fun reinicializarDados(){
        _pontos.value = 0
        _contagemPalavraAtual.value = 0
        listPalavras.clear()
        obterAProximaPalavra()
    }

    fun aumentaPontuacao() {
        _pontos.value = (_pontos.value)?.plus(SCORE_INCREASE) }

    fun aPalavraDoUsuarioEstaCorreta(palavra : String): Boolean {
        if (palavra.equals(palavraAtual, true)) {
            aumentaPontuacao()
            return true
        }
        return false
    }
}