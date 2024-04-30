package com.muraguri.comicly.core.data.mapping

import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.remote.models.IssuesDTO

fun IssuesDTO.Issues.toIssuesDomain() : Issue {
    return Issue(
        id = id ?: 0,
        image = image?.small.orEmpty(),
        siteLink = siteDetailUrl.orEmpty(),
        title = name.orEmpty()
    )
}