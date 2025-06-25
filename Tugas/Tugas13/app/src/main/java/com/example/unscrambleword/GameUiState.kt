package com.example.unscrambleword

data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val userGuess: String = "",
    val score: Int = 0,
    val isGuessWrong: Boolean = false,
    val isGameOver: Boolean = false
)