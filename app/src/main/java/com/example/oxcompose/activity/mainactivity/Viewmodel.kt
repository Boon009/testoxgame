package com.example.oxcompose.activity.mainactivity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.oxcompose.R

class GameViewModel : ViewModel() {
    var turn by mutableStateOf(0)
    var currentPlayer = mutableStateOf("O")
    var winnerString = mutableStateOf("")
    var selectedImageIndex = mutableStateOf(-1)

    var valuaOnTurn: MutableList<Model.ValuaOnTurn>
         = mutableListOf(
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
            Model.ValuaOnTurn(9, "z"),
        )
//        get() =TODO()
        set(value) = TODO()

    var imageList = mutableStateListOf(
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
        Model.ImageItem(R.drawable.whitesquare, R.color.white, false),
    )

    fun resetGame() {
        for (i in imageList.indices) {
            imageList[i] = Model.ImageItem(R.drawable.whitesquare, R.color.white, false)
        }
        for (i in imageList.indices) {
            valuaOnTurn[i] = Model.ValuaOnTurn(9, "z")
        }
        turn = 0
        winnerString.value = ""
        currentPlayer.value = "O"
    }

    fun handleClick(index: Int) {
        var ox = R.drawable.oxcircle
        if (turn % 2 == 1) {
            ox = R.drawable.oxcross
            valuaOnTurn[turn] = Model.ValuaOnTurn(index, "x")
            currentPlayer.value = "O"
        } else {
            ox = R.drawable.oxcircle
            valuaOnTurn[turn] = Model.ValuaOnTurn(index, "o")
            currentPlayer.value = "X"
        }
        turn++
        imageList[index] = imageList[index].copy(resId = ox, clicked = true)
        selectedImageIndex.value = index

        val result = createMeatix(valuaOnTurn, index)
        if (turn == 9) {
            winnerString.value = "No winner."
        }
        if (result == "o" || result == "x") {
            for (index2 in 0 until imageList.size) {
                imageList[index2].clicked = true
            }
            winnerString.value = "Winner : $result is winner."
        }
    }

    fun createMeatix(valuaOnTurn: MutableList<Model.ValuaOnTurn>, turn: Int): String {
        var winner = "No Winner"
        var oMretix = arrayOf(
            intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0)
        )

        var xMretix = arrayOf(
            intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0)
        )
        for (i in 0 until 9) {
            if (valuaOnTurn[i].clicked == "o") {
                oMretix[valuaOnTurn[i].index / 3][valuaOnTurn[i].index % 3] = 1
                logMatrix(oMretix, "oMretix")
            }
            if (valuaOnTurn[i].clicked == "x") {
                xMretix[valuaOnTurn[i].index / 3][valuaOnTurn[i].index % 3] = 1
                logMatrix(xMretix, "xMretix")
            }
        }
        if (checkWin(xMretix)) {
            winner = "x"
        } else if (checkWin(oMretix)) {
            winner = "o"
        }

        return winner
    }

    fun checkWin(board: Array<IntArray>): Boolean {
        // Check rows
        for (i in 0 until 3) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true
            }
        }

        // Check columns
        for (j in 0 until 3) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true
        }

        return false
    }

    fun logMatrix(matrix: Array<IntArray>, name: String) {
        Log.d("Matrix", "name : $name")
        for (row in matrix) {
            Log.d("Matrix", row.joinToString(", "))
        }
    }
}
