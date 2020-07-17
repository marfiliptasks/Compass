package com.mf.domain.useCase.navigationUseCase

import com.mf.domain.model.CordsModel
import com.mf.domain.model.DegreesResultModel
import kotlinx.coroutines.flow.Flow

interface INavigationUseCase {
    fun setCoordinates(cords: CordsModel)
    suspend fun observePositionChanges(): Flow<DegreesResultModel>
    fun setupListeners(location: Boolean, position: Boolean): Boolean
    fun stopListeners()
}