package com.jina.clonespotify.utils

fun coverUrl(md5: String, size: Int = 500): String {
    return "https://cdn-images.dzcdn.net/images/cover/$md5/${size}x$size.jpg"
}