package com.tope.http_lib

class Data<T> {

    var data: T? = null
    var code = 0
    var msg: String? = null

    constructor() {

    }

    constructor(code: Int, msg: String) {
        this.code = code;
        this.msg = msg;
    }

    override fun toString(): String {
        return "Data(date=$data, code=$code, message=$msg)"
    }

}