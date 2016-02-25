package com.outbrain



class AmplifyApiClient() {

    fun marketers(): Marketers {
        return Marketers()
    }

}

class Marketers {
    fun id(id: String): MarketerId {
        return MarketerId()
    }
}

class MarketerId {
    fun get(): Marketer {
        return Marketer("name")
    }

}

data class Marketer(val name: String) {

}


