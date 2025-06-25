package com.example.unscrambleword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userGuess = guessedWord,
                isGuessWrong = false
            )
        }
    }

    fun checkUserGuess() {
        if (uiState.value.userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessWrong = true)
            }
        }
        updateUserGuess("")
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(word, ignoreCase = true)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
}

const val MAX_NO_OF_WORDS = 10
const val SCORE_INCREASE = 20

// Word list for the game
val allWords: Set<String> = setOf(
    "animal", "auto", "anagram", "android", "compose", "computer", "default", "function",
    "happy", "kotlin", "language", "movie", "music", "password", "pizza", "program",
    "science", "study", "symbol", "system", "tablet", "train", "tree", "update",
    "variable", "website", "window", "wonder", "work", "world", "apple", "beach",
    "chair", "dance", "earth", "flame", "grape", "house", "image", "juice",
    "keyboard", "light", "mountain", "ocean", "plant", "queen", "river", "stone",
    "tiger", "umbrella", "voice", "water", "youth", "zebra"
)