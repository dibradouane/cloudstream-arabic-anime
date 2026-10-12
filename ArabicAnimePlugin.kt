package com.v18z1.arabic

import com.lagradost.cloudstream3.plugins.Plugin
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin

@CloudstreamPlugin
class ArabicAnimePlugin : Plugin() {
    override fun load() {
        registerMainAPI(ZoroArabic())
        registerMainAPI(AnimeXArabic())
        registerMainAPI(AnimeFireArabic())
        registerMainAPI(KaidoArabic())
        registerMainAPI(AnimaToonArabic())
    }
}
