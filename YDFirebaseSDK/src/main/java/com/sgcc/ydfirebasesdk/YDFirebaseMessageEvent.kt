package com.sgcc.ydfirebasesdk

class YDFirebaseMessageEvent {

    var messageBody: String? = null
    var messageData: Map<String, Any>? = null

    constructor() {}

    constructor(msgBody: String, msgData: Map<String, Any>?) {
        this.messageBody = msgBody
        this.messageData = msgData
    }

}