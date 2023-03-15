package com.example.desembaralhar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.desembaralhar.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.exit

class GameFragment : Fragment() {

    private val viewModel : GameViewModel by viewModels()
    private lateinit var binding : FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submit.setOnClickListener { enviarPalavra() }
        binding.skip.setOnClickListener { aoPularPalavra() }

        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        binding.lifecycleOwner = viewLifecycleOwner


        // esses dois observers não são mais necessários logo que o componente acima
        //já cuida da vinculação do liveData ao layout
//        viewModel.pontos.observe(viewLifecycleOwner) { pontos ->
//            binding.score.text = getString(R.string.score, pontos)
//        }
//
//        viewModel.contagemPalavraAtual.observe(viewLifecycleOwner){ contador ->
//            binding.wordCount.text = getString(R.string.word_count, contador, MAX_NO_OF_WORDS)
//        }

    }


    private fun aoPularPalavra() {
        if (viewModel.proximaPalavra()){
            configuraErroCaixaDeTexto(false)
        } else {
            mostraPontuacaoFinalDialog()
        }
    }

    private fun enviarPalavra() {
        val respostaDoJogador = binding.textInputEditText.text.toString()
        if (viewModel.aPalavraDoUsuarioEstaCorreta(respostaDoJogador)){
            configuraErroCaixaDeTexto(false)
            if (!viewModel.proximaPalavra()){
                mostraPontuacaoFinalDialog()
            }
        } else {
            configuraErroCaixaDeTexto(true)
        }
    }

    private fun configuraErroCaixaDeTexto(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    private fun sairDoJogo() {
        activity?.finish()
    }

    private fun mostraPontuacaoFinalDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.parabens)
            .setMessage(getString(R.string.sua_pontuacao, viewModel.pontos.value))
            /** evita que a caixa de dialogo seja cancelada caso o usuario pressione o botao voltar */
            .setCancelable(false)
            .setNegativeButton(getString(R.string.sair)) { _, _ ->
                sairDoJogo()
            }
            .setPositiveButton(getString(R.string.jogar_novamente)) { _, _ ->
                reinicializarJogo()
            }
            .show()
    }

    private fun reinicializarJogo() {
        viewModel.reinicializarDados()
        configuraErroCaixaDeTexto(false)
    }

}