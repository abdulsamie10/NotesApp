package com.example.firstnotesapplication

class AdsModel(
    var id: String,
    var banner_g: String,
    var openapp_g: String,
    var native_g: String,
    var reward_g: String,
    var interstitial_g: String,
    var adsStatus: Boolean
)
{
    // Default constructor
    constructor() : this(
        id = "",
        banner_g = "",
        openapp_g = "",
        native_g = "",
        reward_g = "",
        interstitial_g = "",
        adsStatus = false
    )
}
