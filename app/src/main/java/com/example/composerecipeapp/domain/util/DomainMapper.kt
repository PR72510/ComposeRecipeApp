package com.example.composerecipeapp.domain.util

interface DomainMapper<T, DomainModel> {

    fun mapFromDomainModel(model: DomainModel): T

    fun mapToDomainModel(model: T): DomainModel
}