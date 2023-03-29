package com.example.home_automation.Models

data class itemModel(
    var time:Long?=0,
    var name: String? = "",
    var img:String? = "",
    var place:String?="",
    var state: Boolean? = false
)
