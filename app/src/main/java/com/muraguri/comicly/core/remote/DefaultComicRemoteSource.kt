package com.muraguri.comicly.core.remote

import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.remote.models.CharacterInfoDTO
import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.IssuesDTO
import com.muraguri.comicly.core.remote.models.MovieInfoDTO
import com.muraguri.comicly.core.remote.models.PublisherInfoDTO
import com.muraguri.comicly.core.remote.models.SearchDTO
import com.muraguri.comicly.core.remote.models.TeamInfoDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class DefaultComicRemoteSource(
    private val service: ComicsService,
    private val apiKey : String
) : ComicRemoteSource {

    override suspend fun fetchCharacters(offset : Int, limit :Int): CharactersDTO {
        return service.fetchCharacters(
            apiKey = apiKey,
            offset = offset,
            limit = limit
        )
    }
    override suspend fun fetchCharacterInfo(characterId: Int): CharacterInfoDTO {
        val response = service.fetchCharactersInfo(
            apiKey = apiKey,
            characterId = "4005-$characterId"
        )
        Timber.d("CHARACTER-RE", response)
        return response
    }

    override suspend fun fetchIssuesInfo(issueId: Int): IssueInfoDTO {
        val response =  service.fetchIssuesInfo(
            apiKey = apiKey,
            issueId = "4000-$issueId"
        )
        Timber.d("ISSUE-RE", response)
        return response
    }

    override suspend fun fetchPublisherInfo(publisherId: Int): PublisherInfoDTO {
        return service.fetchPublisherInfo(
            apiKey = apiKey,
            publisherId = "4010-$publisherId"
        )
    }

    override suspend fun fetchMovieInfo(movieId: Int): MovieInfoDTO {
        return service.fetchMovieInfo(
            apiKey = apiKey,
            movieId = "4025-$movieId"
        )
    }

    override suspend fun fetchTeamInfo(teamId: Int): TeamInfoDTO {
        return service.fetchTeamInfo(
            apiKey = apiKey,
            teamId = "4060-$teamId"
        )
    }

    override suspend fun fetchIssues(offset: Int, limit: Int): IssuesDTO {
        val response =  service.fetchIssues(
            apiKey = apiKey, offset = offset, limit = limit
        )
        Timber.d("ISSUES", response)
        return response
    }
    override suspend fun search(query: String,offset : Int, limit :Int): SearchDTO {
        return service.searchCharacter(
            apiKey = apiKey,
            query = query,
            offset = offset,
            limit = limit
        )
    }

    suspend fun mapCharacterInfo(characterDto: CharacterInfoDTO): CharacterInfo {
        return coroutineScope {
            // Fetch the publisher's image
            val publisherImageDeferred = async {
                characterDto.results?.publisher?.id?.let { fetchPublisherImage(it) } ?: ""
            }

//            // Fetch the images for movies in the character's list
//            val movieImagesDeferred = characterDto.results?.movies?.map { movie ->
//                async { fetchMovieImage(movie.id ?: 0) }
//            }
//
//            // Fetch the images for issues
//            val issueImagesDeferred = characterDto.results!!.issueCredits.map { issue ->
//                async { fetchIssueImage(issue.id ?: 0) }
//            }
//
//            // Fetch the images for allies
//            val alliesImagesDeferred = characterDto.results.characterFriends.map { friend ->
//                async { fetchAlliesImage(friend.id ?: 0) }
//            }
//            // Fetch the images for allies
//            val nemesisImagesDeferred = characterDto.results.characterEnemies.map { friend ->
//                async { fetchAlliesImage(friend.id ?: 0) }
//            }
//
//            // Fetch the images for team allies
//            val teamAlliesImagesDeferred = characterDto.results.teamFriends.map { team ->
//                async { fetchTeamImage(team.id ?: 0) }
//            }
//
//            // Fetch the images for team nemesis
//            val teamNemesisImagesDeferred = characterDto.results.teamEnemies.map { enemy ->
//                async { fetchTeamImage(enemy.id ?: 0) }
//            }
//            // Fetch the images for team nemesis
//            val teamsImagesDeferred = characterDto.results.teamEnemies.map { enemy ->
//                async { fetchTeamImage(enemy.id ?: 0) }
//            }

            // Map the DTO to the domain model using the fetched images
            CharacterInfo(
                id = characterDto.results!!.id ?: 0,
                name = characterDto.results.name.orEmpty(),
                realName = characterDto.results.realName.orEmpty(),
                deck = characterDto.results.deck.orEmpty(),
                overView = characterDto.results.description.orEmpty(),
                origin = characterDto.results.origin.name.orEmpty(),
                siteLink = characterDto.results.siteDetailUrl.orEmpty(),
                image = characterDto.results.image.small.orEmpty(),
                issueAppearance = characterDto.results.countOfIssueAppearances ?: 0,
                powers = characterDto.results.powers.map { power ->
                    CharacterInfo.Power(
                        id = power.id ?: -1,
                        name = power.name.orEmpty(),
                        siteLink = power.apiDetailUrl.orEmpty()
                    )
                },
//                movies = movieImagesDeferred!!.mapIndexed { index, deferred ->
//                    CharacterInfo.Movie(
//                        id = characterDto.results.movies[index].id ?: 0,
//                        name = characterDto.results.movies[index].name.orEmpty(),
//                        siteLink = characterDto.results.movies[index].apiDetailUrl.orEmpty(),
//                        image = deferred.await() // Movie image
//                    )
//                },
//                issues = issueImagesDeferred.mapIndexed { index, deferred ->
//                    CharacterInfo.Issues(
//                        id = characterDto.results.issueCredits[index].id ?: 0,
//                        title = characterDto.results.issueCredits[index].name.orEmpty(),
//                        siteLink = characterDto.results.issueCredits[index].apiDetailUrl.orEmpty(),
//                        image = deferred.await() // Issue image
//                    )
//                },
//                allies = alliesImagesDeferred.mapIndexed { index, deferred ->
//                    CharacterInfo.Allies(
//                        id = characterDto.results.characterFriends[index].id ?: 0,
//                        title = characterDto.results.characterFriends[index].name.orEmpty(),
//                        siteLink = characterDto.results.characterFriends[index].apiDetailUrl.orEmpty(),
//                        image = deferred.await() // Ally image
//                    )
//                },
//                teamAllies = teamAlliesImagesDeferred.mapIndexed { index, deferred ->
//                    CharacterInfo.AlliesTeam(
//                        id = characterDto.results.teamFriends[index].id ?: 0,
//                        title = characterDto.results.teamFriends[index].name.orEmpty(),
//                        siteLink = characterDto.results.teamFriends[index].apiDetailUrl.orEmpty(),
//                        image = deferred.await() // Team ally image
//                    )
//                },
//                teamNemesis = teamNemesisImagesDeferred.mapIndexed { index, deferred ->
//                    CharacterInfo.NemesisTeam(
//                        id = characterDto.results.teamEnemies[index].id ?: 0,
//                        title = characterDto.results.teamEnemies[index].name.orEmpty(),
//                        siteLink = characterDto.results.teamEnemies[index].apiDetailUrl.orEmpty(),
//                        image = deferred.await() // Team nemesis image
//                    )
//                },
                publisher = CharacterInfo.Publisher(
                    id = characterDto.results.publisher?.id ?: 0,
                    title = characterDto.results.publisher?.name.orEmpty(),
                    siteLink = characterDto.results.publisher?.apiDetailUrl.orEmpty(),
                    image = publisherImageDeferred.await() // Publisher image
                ),
//                nemesis = nemesisImagesDeferred.mapIndexed { index, nemesis ->
//                    CharacterInfo.Nemesis(
//                        id = characterDto.results.characterEnemies[index].id ?: 0,
//                        title = characterDto.results.characterEnemies[index].name.orEmpty(),
//                        siteLink = characterDto.results.characterEnemies[index].apiDetailUrl.orEmpty(),
//                        image = nemesis.await() // Team nemesis image
//                    )
//                },
//                teams = teamsImagesDeferred.mapIndexed { index, team ->
//                    CharacterInfo.Team(
//                        id = characterDto.results.teams[index].id ?: 0,
//                        title = characterDto.results.teams[index].name.orEmpty(),
//                        siteLink = characterDto.results.teams[index].siteDetailUrl.orEmpty(),
//                        image = team.await()
//                    )
//                }
            )
        }
    }

    private suspend fun fetchPublisherImage(publisherId: Int): String {
        val publisherData = service.fetchPublisherInfo(apiKey = apiKey, publisherId = "4010-$publisherId")
        return publisherData.results?.image?.icon.orEmpty()
    }
    private suspend fun fetchMovieImage(movieId: Int): String {
        val movieData = service.fetchMovieInfo(apiKey = apiKey, movieId = "4025-$movieId")
        return movieData.results?.image?.small.orEmpty()
    }
    private suspend fun fetchIssueImage(issueId: Int): String {
        val issueData = service.fetchIssuesInfo(apiKey = apiKey, issueId = "4000-$issueId")
        return issueData.results?.image?.small.orEmpty()
    }
    private suspend fun fetchAlliesImage(characterId: Int): String {
        val alliesData = service.fetchCharactersInfo(apiKey = apiKey, characterId = "4005-$characterId")
        return alliesData.results?.image?.small.orEmpty()
    }
    private suspend fun fetchTeamImage(teamId: Int): String {
        val teamData = service.fetchTeamInfo(apiKey = apiKey, teamId = "4060-$teamId")
        return teamData.results?.image?.original.orEmpty()
    }

}