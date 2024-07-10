package com.example.tilesamples.tiles.layout_tile

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders.Tile
import com.example.tilesamples.tiles.layout_tile.layout_catalog.simpleLayout
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService

private const val RESOURCES_VERSION = "0"

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
        return createTile(this, requestParams.deviceConfiguration, simpleLayout)
//        return createTile(this, requestParams.deviceConfiguration, multiSlotLayout)
//        return createTile(this, requestParams.deviceConfiguration, multiButtonLayout)
//        return createTile(this, requestParams.deviceConfiguration, mergedLayout)
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
