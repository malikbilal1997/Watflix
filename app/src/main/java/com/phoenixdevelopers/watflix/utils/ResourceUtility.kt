package com.phoenixdevelopers.watflix.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes

fun Resources.getRawDimensionInDp(@DimenRes dimenResId: Int): Float {

    val value = TypedValue()

    getValue(dimenResId, value, true)

    return TypedValue.complexToFloat(value.data)

}