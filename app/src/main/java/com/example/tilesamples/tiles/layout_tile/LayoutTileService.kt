package com.example.tilesamples.tiles.layout_tile

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders.Tile
import com.example.tilesamples.tiles.layout_tile.layout_catalog.edgeContentLayout
import com.example.tilesamples.tiles.layout_tile.layout_catalog.mergedLayout
import com.example.tilesamples.tiles.layout_tile.layout_catalog.multiButtonLayout
import com.example.tilesamples.tiles.layout_tile.layout_catalog.multiSlotLayout
import com.example.tilesamples.tiles.layout_tile.layout_catalog.simpleLayout
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService

private const val RESOURCES_VERSION = "0"
internal const val SIMPLE_LAYOUT = "simpleLayout"
internal const val MULTI_SLOT_LAYOUT = "multiSlotLayout"
internal const val MULTI_BUTTON_LAYOUT = "multiButtonLayout"
internal const val MERGED_LAYOUT = "mergedLayout"
internal const val EDGE_CONTENT_LAYOUT = "edgeContentLayout"

@OptIn(ExperimentalHorologistApi::class)
class LayoutTileService : SuspendingTileService() {
    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): Tile {
        val deviceConfiguration = requestParams.deviceConfiguration
        return when (requestParams.currentState.lastClickableId) {
            MULTI_SLOT_LAYOUT -> createTile(this, deviceConfiguration, multiSlotLayout)
            MULTI_BUTTON_LAYOUT -> createTile(this, deviceConfiguration, multiButtonLayout)
            MERGED_LAYOUT -> createTile(this, deviceConfiguration, mergedLayout)
            EDGE_CONTENT_LAYOUT -> createTile(this, deviceConfiguration, edgeContentLayout)
            else -> createTile(this, deviceConfiguration, simpleLayout)
        }
    }
}

fun createTile(
    context: Context,
    deviceParameters: DeviceParameters,
    layout: (Context, DeviceParameters) -> LayoutElement
): Tile {
    val timeline = TimelineBuilders.Timeline.Builder().addTimelineEntry(
        TimelineBuilders.TimelineEntry.Builder().setLayout(
            Layout.Builder().setRoot(layout(context, deviceParameters)).build()
        ).build()
    ).build()

    return Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
        .setTileTimeline(timeline).build()
}
