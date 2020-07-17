package com.mf.domain.model

data class AddressModel(
    val city: String?,
    val address: String?,
    val state: String?,
    val country: String?
) {
    override fun toString(): String =
        "City: ${city.orEmpty()}, " +
                "Address: ${address.orEmpty()}, " +
                "State: ${state.orEmpty()}," +
                " Country: ${country.orEmpty()}"
}