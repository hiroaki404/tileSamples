package com.example.tilesamples.tiles.update_tile

import android.content.Context
import android.util.Log
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders.Resources
import androidx.wear.protolayout.TimelineBuilders.Timeline
import androidx.wear.protolayout.TimelineBuilders.TimelineEntry
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tiles.tooling.preview.TilePreviewHelper
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import java.util.Calendar

private const val RESOURCES_VERSION = "0"

@OptIn(ExperimentalHorologistApi::class)
class RefreshTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: ResourcesRequest
    ): Resources {
        return Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: TileRequest
    ): TileBuilders.Tile {
        Log.d("RefreshTileService", "tileRequest")
        val singleTileTimeline = Timeline.Builder().addTimelineEntry(
            TimelineEntry.Builder().setLayout(
                Layout.Builder().setRoot(tileLayout(this, requestParams.deviceConfiguration))
                    .build()
            ).build()
        ).build()

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline)
            // you should not update your tile more frequently than once a minute
            // If you specify an interval of less than 1 minute,
            // updates may occur within that timeframe, but the actual update interval may be longer than the specified value.
            .setFreshnessIntervalMillis(60 * 1000)
            .build()
    }
}

private fun tileLayout(context: Context, deviceConfiguration: DeviceParameters): LayoutElement {
    val calendar = Calendar.getInstance()

    val hourPart = calendar.get(Calendar.HOUR_OF_DAY)
    val minutePart = calendar.get(Calendar.MINUTE)
    val secondPart = calendar.get(Calendar.SECOND)

    return PrimaryLayout.Builder(deviceConfiguration)
        .setContent(
            Text.Builder(context, "now $hourPart:$minutePart:$secondPart")
                .setColor(argb(Colors.DEFAULT.onSurface))
                .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                .build()
        ).build()
}

@Preview
private fun tilePreview(context: Context) = TilePreviewData { tileRequest ->
    TilePreviewHelper.singleTimelineEntryTileBuilder(
        tileLayout(context, tileRequest.deviceConfiguration)
    ).build()
}
