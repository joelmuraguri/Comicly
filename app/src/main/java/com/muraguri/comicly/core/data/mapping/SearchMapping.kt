package com.muraguri.comicly.core.data.mapping

import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.remote.models.SearchDTO

fun SearchDTO.Character.toSearchDomain() : Character{
    return Character(
        id = id ?: 0,
        alias = aliases.orEmpty(),
        image = image.small.orEmpty(),
        siteLink = siteDetailUrl.orEmpty(),
        name = name.orEmpty(),
        icon = image.icon.orEmpty()
    )
}