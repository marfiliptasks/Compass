package com.mf.domain.dataSources

import com.mf.domain.model.CordsModel

interface ILocationDataSource : IAndroidDataSource{
    fun setTargetLocation(cords: CordsModel)
}