package com.muraguri.comicly.core.data.mapping

import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.core.remote.models.CharactersDTO

fun CharactersDTO.Character.toCharacterDomain() : Character{
    return Character(
        id = id ?: 0,
        alias = aliases.orEmpty(),
        image = image.small.orEmpty(),
        icon = image.icon.orEmpty(),
        siteLink = siteDetailUrl.orEmpty(),
        name = name.orEmpty()
    )
}

fun Character.toFavouriteCharacterEntity() : FavCharacter{
    return FavCharacter(
        id = id,
        name = name,
        icon = icon,
        image = image
    )
}