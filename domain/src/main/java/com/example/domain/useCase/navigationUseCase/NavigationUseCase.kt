package com.example.domain.useCase.navigationUseCase

import com.example.domain.converters.DegreeConverter.convertToDegrees
import com.example.domain.model.*
import com.example.domain.repository.INavigationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NavigationUseCase @Inject constructor(
    private val navigationRepository: INavigationRepository
) : INavigationUseCase {

    override fun setCoordinates(cords: CordsModel) =
        navigationRepository.setCoordinates(cords)

    override suspend fun observePositionChanges(): Flow<DegreesResultModel> =
        navigationRepository.observePosition().map { convertToDegrees(it) }

    override fun setupListeners(location: Boolean, position: Boolean): Boolean =
        navigationRepository.registerListeners(location, position)

    override fun stopListeners() = navigationRepository.unregisterListeners()
}
