package com.muraguri.comicly.core.data.mapping

import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.domain.models.comics.IssueInfo
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.IssuesDTO

fun IssuesDTO.Issues.toIssuesDomain() : Issue {
    return Issue(
        id = id ?: 0,
        image = image.small.orEmpty(),
        siteLink = siteDetailUrl.orEmpty(),
        title = name.orEmpty()
    )
}

fun IssueInfoDTO.Issue.toIssueInfoDomain() : IssueInfo{
    return IssueInfo(
        id = id ?:0,
        name = name.orEmpty(),
        imageUrl = image.small.orEmpty(),
        deck = deck.orEmpty()
    )
}
