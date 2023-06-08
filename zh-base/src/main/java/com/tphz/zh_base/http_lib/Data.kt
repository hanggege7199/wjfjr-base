package com.tope.http_lib

class Data<T> {

    var data: T? = null
    var code = 0
    var message: String? = null

    constructor() {

    }

    constructor(code: Int, message: String) {
        this.code = code;
        this.message = message;
    }

    override fun toString(): String {
        return "Data(date=$data, code=$code, message=$message)"
    }

}