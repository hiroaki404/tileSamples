package com.example.tilesamples.tiles.use_minimum_renderer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.TileLayoutPreview
import com.google.android.horologist.compose.tools.WearPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
import com.google.android.horologist.tiles.render.SingleTileLayoutRenderer

data class SimpleTileState(
    val text: String
)

@OptIn(ExperimentalHorologistApi::class)
class SimpleTileRenderer(context: Context): SingleTileLayoutRenderer<SimpleTileState, Unit>(context) {
    override fun renderTile(
        state: SimpleTileState,
        deviceParameters: DeviceParametersBuilders.DeviceParameters
    ): LayoutElementBuilders.LayoutElement {
        return tileLayout(state)
    }

    private fun tileLayout(state: SimpleTileState): LayoutElementBuilders.LayoutElement {
        return PrimaryLayout.Builder(buildDeviceParameters(context.resources))
            .setContent(
                Text.Builder(context, state.text)
                    .setColor(argb(Colors.DEFAULT.onSurface))
                    .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                    .build()
            ).build()
    }
}

@OptIn(ExperimentalHorologistApi::class)
@WearPreview
@Composable
fun SimpleTileRendererPreview() {
    val state = SimpleTileState("Hello World!")
    val context = LocalContext.current

    TileLayoutPreview(
        state = state,
        resourceState = Unit,
        renderer = SimpleTileRenderer(context)
    )
}
