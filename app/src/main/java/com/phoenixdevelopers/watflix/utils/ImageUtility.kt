package com.phoenixdevelopers.watflix.utils

import timber.log.Timber

fun getImageUrl(url: String): String {

    Timber.d("Url -> ${IMAGE_URL + getUrlLastPart(url)}")

    return IMAGE_URL + getUrlLastPart(url)

}

fun getUrlLastPart(url: String): String {

    return url.replaceFirst(".*/([^/?]+).*".toRegex(), "$1")

}

