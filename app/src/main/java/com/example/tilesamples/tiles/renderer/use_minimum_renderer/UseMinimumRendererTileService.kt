package com.example.tilesamples.tiles.renderer.use_minimum_renderer

import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService

@OptIn(ExperimentalHorologistApi::class)
class UseMinimumRendererTileService : SuspendingTileService() {
    private lateinit var renderer: SimpleTileRenderer

    override fun onCreate() {
        super.onCreate()
        renderer = SimpleTileRenderer(this)
    }

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return renderer.produceRequestedResources(Unit, requestParams)
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val state = SimpleTileState("hello world")
        return renderer.renderTimeline(state, requestParams)
    }
}
