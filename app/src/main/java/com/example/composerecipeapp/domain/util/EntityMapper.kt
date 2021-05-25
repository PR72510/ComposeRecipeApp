package com.example.composerecipeapp.domain.util

interface EntityMapper<Entity, DomainModel> {

    fun toEntity(model: DomainModel): Entity

    fun toDomainModel(entity: Entity): DomainModel
}