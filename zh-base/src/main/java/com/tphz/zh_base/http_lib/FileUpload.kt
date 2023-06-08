package com.tope.http_lib

data class FileUpload(
        val bucket: String,
        val callback: FileCallback,
        val defaultPath: String,
        val urlPrefix: String
)

