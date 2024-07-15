package com.example.tilesamples.tiles.update_tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
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
                Layout.Builder().setRoot(tileLayout(this)).build()
            ).build()
        ).build()

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }
}

private fun tileLayout(context: Context): LayoutElement {
    val time = DynamicInstant.withSecondsPrecision(Instant.EPOCH)
        .durationUntil(DynamicInstant.platformTimeWithSecondsPrecision())
        .secondsPart
        .asFloat()

    return PrimaryLayout.Builder(buildDeviceParameters(context.resources))
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

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun DynamicExpressionTilePreview() {
    LayoutRootPreview(root = tileLayout(LocalContext.current))
}
