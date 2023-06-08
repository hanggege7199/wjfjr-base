package com.tope.http_lib

data class FileCallback(
    val AccessKeySecret: String,
    val accessid: String,
    val callback: String,
    val dir: String,
    val expire: String,
    val host: String,
    val policy: String,
    val signature: String
)