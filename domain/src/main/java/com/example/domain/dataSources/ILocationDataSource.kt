package com.example.domain.dataSources

import com.example.domain.model.CordsModel

interface ILocationDataSource : IAndroidDataSource{
    fun setTargetLocation(cords: CordsModel)
}