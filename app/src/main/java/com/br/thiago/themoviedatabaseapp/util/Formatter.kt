package com.br.thiago.themoviedatabaseapp.util

import java.text.NumberFormat
import java.util.*

class Formatter {

    companion object {
        fun getMoneyFormat(value: Number): String {
            return NumberFormat.getNumberInstance(Locale.US).format(value)
        }
    }

}