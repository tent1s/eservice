package ru.omsu.eservice.ui.screen.services.model

import kotlin.random.Random

data class ServiceItem(
    val type: Services,
    val imageRes: Int,
    val title: Int,
    val description: Int,
    val moreDescription: Int,
    val id: Int = Random.nextInt(),
    var showMore: Boolean = false
)
