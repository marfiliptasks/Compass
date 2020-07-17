package com.example.domain.useCase.navigationUseCase

import com.example.domain.model.AddressModel
import com.example.domain.model.CordsModel
import com.example.domain.model.DegreesResultModel
import kotlinx.coroutines.flow.Flow

interface INavigationUseCase {
    fun setCoordinates(cords: CordsModel)
    suspend fun observePositionChanges(): Flow<DegreesResultModel>
    fun setupListeners(location: Boolean, position: Boolean): Boolean
    fun stopListeners()
}