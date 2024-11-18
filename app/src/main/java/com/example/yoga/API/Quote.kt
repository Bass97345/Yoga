package com.example.yoga.API

data class QuoteResponse(val success :Boolean, val data:List<Quote>)
data class Quote(val id: Int,
                 val title: String,
                 val image: String,
                 val description: String)
