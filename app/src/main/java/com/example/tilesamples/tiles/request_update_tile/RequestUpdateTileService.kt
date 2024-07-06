package com.example.tilesamples.tiles.request_update_tile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.EventBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
import com.google.android.horologist.tiles.SuspendingTileService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

private const val RESOURCES_VERSION = "0"

@OptIn(ExperimentalHorologistApi::class)
class RequestUpdateTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override fun onTileAddEvent(requestParams: EventBuilders.TileAddEvent) {
        // In a production app, you should not use GlobalScope. Instead, use WorkManager and so on.
        GlobalScope.launch {
            repeat(10) {
                // you can request a refresh from any place in your application code
                // you should not update your tile more frequently than once a minute
                // If you specify an interval of less than 1 minute,
                // updates may occur within that timeframe, but the actual update interval may be longer than the specified value.
                getUpdater(this@RequestUpdateTileService).requestUpdate(RequestUpdateTileService::class.java)
                delay(60 * 1000)
            }
            cancel()
        }

        super.onTileAddEvent(requestParams)
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        Log.d("RefreshTileService", "tileRequest")
        val singleTileTimeline = TimelineBuilders.Timeline.Builder().addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder().setLayout(
                LayoutElementBuilders.Layout.Builder().setRoot(tileLayout(this)).build()
            ).build()
        ).build()

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }
}

private fun tileLayout(context: Context): LayoutElementBuilders.LayoutElement {
    val calendar = Calendar.getInstance()

    val hourPart = calendar.get(Calendar.HOUR_OF_DAY)
    val minutePart = calendar.get(Calendar.MINUTE)
    val secondPart = calendar.get(Calendar.SECOND)

    return PrimaryLayout.Builder(buildDeviceParameters(context.resources))
        .setContent(
            Text.Builder(context, "now $hourPart:$minutePart:$secondPart")
                .setColor(argb(Colors.DEFAULT.onSurface))
                .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                .build()
        ).build()
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreview() {
    LayoutRootPreview(root = tileLayout(LocalContext.current))
}
