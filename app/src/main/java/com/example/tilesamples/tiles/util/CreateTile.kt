package com.example.tilesamples.tiles.util

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.tiles.TileBuilders

private const val RESOURCES_VERSION = "0"

fun createTile(
    context: Context,
    deviceParameters: DeviceParametersBuilders.DeviceParameters,
    layout: (Context, DeviceParametersBuilders.DeviceParameters) -> LayoutElementBuilders.LayoutElement
): TileBuilders.Tile {
    val timeline = TimelineBuilders.Timeline.Builder().addTimelineEntry(
        TimelineBuilders.TimelineEntry.Builder().setLayout(
            LayoutElementBuilders.Layout.Builder().setRoot(layout(context, deviceParameters)).build()
        ).build()
    ).build()

    return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
        .setTileTimeline(timeline).build()
}
