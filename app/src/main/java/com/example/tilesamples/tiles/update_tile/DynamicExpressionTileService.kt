package com.example.tilesamples.tiles.update_tile

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders.Resources
import androidx.wear.protolayout.TimelineBuilders.Timeline
import androidx.wear.protolayout.TimelineBuilders.TimelineEntry
import androidx.wear.protolayout.TypeBuilders
import androidx.wear.protolayout.expression.DynamicBuilders.DynamicInstant
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tiles.tooling.preview.TilePreviewHelper
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import java.time.Instant

private const val RESOURCES_VERSION = "0"

@OptIn(ExperimentalHorologistApi::class)
class DynamicExpressionTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: ResourcesRequest
    ): Resources {
        return Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: TileRequest
    ): TileBuilders.Tile {
        val singleTileTimeline = Timeline.Builder().addTimelineEntry(
            TimelineEntry.Builder().setLayout(
                Layout.Builder().setRoot(tileLayout(this, requestParams.deviceConfiguration)).build()
            ).build()
        ).build()

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }
}

private fun tileLayout(context: Context, deviceConfiguration: DeviceParameters): LayoutElement {
    val time = DynamicInstant.withSecondsPrecision(Instant.EPOCH)
        .durationUntil(DynamicInstant.platformTimeWithSecondsPrecision())
        .secondsPart
        .asFloat()

    return PrimaryLayout.Builder(deviceConfiguration)
        .setContent(
            Text.Builder(
                context,
                TypeBuilders.StringProp.Builder("00")
                    .setDynamicValue(time.format())
                    .build(),
                TypeBuilders.StringLayoutConstraint.Builder("00")
                    .build()
            ).build()
        ).build()
}

@Preview
private fun dynamicExpressionTilePreview(context: Context): TilePreviewData = TilePreviewData { tileRequest ->
    TilePreviewHelper.singleTimelineEntryTileBuilder(
        tileLayout(context, tileRequest.deviceConfiguration)
    ).build()
}
