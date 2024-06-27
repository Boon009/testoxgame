package com.example.oxcompose.activity.mainactivity

class Model {
    data class ValuaOnTurn(
        var index: Int,
        val clicked: String,
    )

    data class ImageItem(
        var resId: Int,
        var backgroundId: Int,
        var clicked: Boolean,
    )

}