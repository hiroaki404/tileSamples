package com.example.tilesamples.tiles.update_tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders.Column
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders.Resources
import androidx.wear.protolayout.TimelineBuilders.TimeInterval
import androidx.wear.protolayout.TimelineBuilders.Timeline
import androidx.wear.protolayout.TimelineBuilders.TimelineEntry.Builder
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
import com.google.android.horologist.tiles.SuspendingTileService

private const val RESOURCES_VERSION = "0"

@OptIn(ExperimentalHorologistApi::class)
class PeriodicUpdateTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: ResourcesRequest
    ): Resources {
        return Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: TileRequest
    ): TileBuilders.Tile {
        val currentTime = System.currentTimeMillis()

        // you should place at least one timeline entry with no validity
        val tileTimeline = Timeline.Builder().addTimelineEntry(
            Builder().setLayout(
                dummyLayout(this)
            ).build()
        )

        (0..10).forEach { index ->
            tileTimeline.addTimelineEntry(
                Builder().setLayout(
                    tileLayout(this, index)
                ).setValidity(
                    // Validity settings should be spaced at least one minute apart. Settings with intervals of less than one minute will be ignored by the system.
                    TimeInterval.Builder()
                        .setEndMillis(currentTime + (index + 1) * 60 * 1000)
                        .build()
                ).build()
            )
        }

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(tileTimeline.build()).build()
    }
}

private fun dummyLayout(context: Context): Layout {
    return Layout.Builder()
        .setRoot(
            PrimaryLayout.Builder(buildDeviceParameters(context.resources))
                .setContent(
                    Text.Builder(context, "no entry")
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                        .build()
                )
                .build()
        ).build()
}


private fun tileLayout(context: Context, lineSize: Int? = null): Layout {
    return Layout.Builder().setRoot(
        tileLayoutElement(context, lineSize)
    ).build()
}

private fun tileLayoutElement(
    context: Context,
    lineSize: Int? = null
): LayoutElement {
    val layoutBuilder = Column.Builder()
    layoutBuilder.addContent(
        Text.Builder(context, "Periodic Update Tile")
            .setColor(argb(Colors.DEFAULT.onSurface))
            .setTypography(Typography.TYPOGRAPHY_CAPTION1)
            .build()
    )

    repeat(lineSize ?: 0) { index ->
        layoutBuilder.addContent(
            Text.Builder(context, "${index + 1} minutes passed")
                .setColor(argb(Colors.DEFAULT.onSurface))
                .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                .build()
        )
    }

    return PrimaryLayout.Builder(buildDeviceParameters(context.resources))
        .setContent(layoutBuilder.build())
        .build()
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun PeriodicUpdateTilePreview() {
    LayoutRootPreview(root = tileLayoutElement(LocalContext.current))
}
