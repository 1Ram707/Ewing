package com.example.ewing20.map.bird.birdVariables

class Bird(
    id: String,
    name: String,
    rarity: Int,
    note: String,
    image: ByteArray?,
    latLng: String?,
    address: String?,
    date: String
) {

    var id: String = id
        private set
    var name: String = name
        private set
    var rarity: Int = rarity
        private set
    var note: String = note
        private set
    var image: ByteArray? = image
        private set
    var latLng: String? = latLng
        private set
    var address: String? = address
        private set
    var date: String = date
        private set
}
