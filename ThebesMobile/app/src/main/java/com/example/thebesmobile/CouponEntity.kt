package com.example.thebesmobile

public class CouponEntity {
    data class data(
        val id: String,
        val name: String,
        val description: String,
        val expiration: String
    )

    companion object {
        var id = ""
        var name = "Hello"
        var decsription = ""
        var expiration = ""
    }

}
