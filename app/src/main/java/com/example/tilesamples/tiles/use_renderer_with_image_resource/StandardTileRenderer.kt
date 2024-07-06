package com.example.tilesamples.tiles.use_renderer_with_image_resource

import android.content.Context
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.render.SingleTileLayoutRenderer

data class SimpleTileState(
    val text: String
)

@OptIn(ExperimentalHorologistApi::class)
class StandardTileRenderer(context: Context): SingleTileLayoutRenderer<SimpleTileState, Unit>(context) {
    override fun renderTile(
        state: SimpleTileState,
        deviceParameters: DeviceParameters
    ): LayoutElementBuilders.LayoutElement {
        return tileLayout(state, deviceParameters)
    }

    private fun tileLayout(state: SimpleTileState, deviceParameters: DeviceParameters): LayoutElementBuilders.LayoutElement {
        return PrimaryLayout.Builder(deviceParameters)
            .setContent(
                Text.Builder(context, state.text)
                    .setColor(argb(Colors.DEFAULT.onSurface))
                    .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                    .build()
            ).build()
    }
}

@Preview
fun simpleTileRendererPreview(context: Context): TilePreviewData {
    val state = SimpleTileState("Hello World!")
    val renderer = StandardTileRenderer(context)

    return TilePreviewData { tileRequest ->
        renderer.renderTimeline(state, tileRequest)
    }
}
