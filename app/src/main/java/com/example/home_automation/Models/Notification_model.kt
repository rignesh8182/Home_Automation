package com.example.home_automation.Models

data class Notification_model(
    var id:Int?=0,
    var name:String?="",
    var img:String?="",
    var state: Boolean =false,
    var time:Long?= 0
)
