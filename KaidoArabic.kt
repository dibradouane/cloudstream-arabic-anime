package com.v18z1.arabic

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class KaidoArabic : MainAPI() {
    override var mainUrl = "https://kaido.to"
    override var name = "Kaido"
    override val lang = "ar"
    override val hasMainPage = false
    override val supportedTypes = setOf(TvType.Anime)

    override suspend fun search(query: String): List<SearchResponse> {
        val doc = app.get(mainUrl + "/search?keyword=" + query.encodeURL() + (if ("/search?keyword=".contains("?")) "&page=1" else "?page=1")).document
        return doc.select("a[href]").mapNotNull { a ->
            val href = a.absUrl("href").takeIf { it.startsWith(mainUrl) } ?: return@mapNotNull null
            val title = (a.selectFirst(".film-name, h3, .dynamic-name")?.text() ?: a.text()).trim()
            if (title.isBlank() || href == mainUrl || href.contains("/search")) return@mapNotNull null
            val poster = a.selectFirst("img")?.let { img -> img.absUrl("data-src").ifBlank { img.absUrl("src") }.ifBlank { null } }
            newAnimeSearchResponse(title, href, TvType.Anime) { posterUrl = poster }
        }.distinctBy { it.url }.take(50)
    }

    override suspend fun load(url: String): LoadResponse? {
        val doc = app.get(url).document
        val title = doc.selectFirst("h1, .film-name, .anime-title")?.text()?.trim()?.takeIf { it.isNotBlank() } ?: return null
        val poster = doc.selectFirst(".film-poster img, .poster img, .cover img, img")?.let { img -> img.absUrl("data-src").ifBlank { img.absUrl("src") }.ifBlank { null } }
        val plot = doc.selectFirst(".synopsis, .description, .story, .film-description")?.text()?.trim()
        val episodes = doc.select(".episodes a[href]").mapIndexedNotNull { i, a ->
            val epUrl = a.absUrl("href").takeIf { it.startsWith("http") } ?: return@mapIndexedNotNull null
            newEpisode(epUrl) { name = a.text().trim().ifBlank { "الحلقة ${i + 1}" }; episode = i + 1 }
        }.distinctBy { it.data }
        if (episodes.isNotEmpty()) return newAnimeLoadResponse(title, url, TvType.Anime, episodes) { posterUrl = poster; this.plot = plot }
        return newMovieLoadResponse(title, url, TvType.Movie, url) { posterUrl = poster; this.plot = plot }
    }

    override suspend fun loadLinks(data: String, isCasting: Boolean, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit): Boolean {
        val doc = app.get(data, referer = mainUrl).document
        val links = doc.select("iframe[src], video source[src], a[href*='.m3u8'], a[href*='.mp4']").mapNotNull { el ->
            val link = if (el.tagName() == "iframe" || el.tagName() == "source") el.absUrl("src") else el.absUrl("href")
            link.takeIf { it.startsWith("http") }
        }.distinct()
        links.forEach { link ->
            if (link.contains(".m3u8", true) || link.contains(".mp4", true)) callback(newExtractorLink(name, name, link, if (link.contains(".m3u8", true)) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO) { referer = mainUrl })
            else loadExtractor(link, mainUrl, subtitleCallback, callback)
        }
        return links.isNotEmpty()
    }
}
