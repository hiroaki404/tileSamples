package com.example.tilesamples.tiles.layout_tile.layout_catalog

import android.content.Context
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Box
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.Modifiers
import androidx.wear.protolayout.material.CircularProgressIndicator
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.ProgressIndicatorColors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.EdgeContentLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tiles.tooling.preview.TilePreviewHelper
import com.example.tilesamples.tiles.layout_tile.SIMPLE_LAYOUT

val edgeContentLayout: (Context, DeviceParameters) -> LayoutElement = { context, deviceParameters ->
    Box.Builder()
        .setModifiers(
            Modifiers.Builder()
                .setClickable(
                    Clickable.Builder()
                        .setId(SIMPLE_LAYOUT)
                        .setOnClick(ActionBuilders.LoadAction.Builder().build())
                        .build()
                )
                .build()
        )
        .setHeight(DimensionBuilders.expand())
        .setWidth(DimensionBuilders.expand())
        .addContent(
            EdgeContentLayout.Builder(deviceParameters)
                .setEdgeContent(
                    CircularProgressIndicator.Builder()
                        .setProgress(0.5f)
                        .setEndAngle(270f)
                        .setCircularProgressIndicatorColors(
                            ProgressIndicatorColors.progressIndicatorColors(
                                Colors.DEFAULT
                            )
                        )
                        .setStrokeWidth(12f)
                        .build()
                )
                .setContent(
                    Text.Builder(context, "Content")
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                        .build()
                )
                .setPrimaryLabelTextContent(
                    Text.Builder(context, "Primary Label Text")
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                        .build()

                )
                .setSecondaryLabelTextContent(
                    Text.Builder(context, "Secondary Label Text")
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                        .build()


                )
                .build()
        ).build()
}

@Preview
fun edgeContentPreview(context: Context) = TilePreviewData { tileRequest ->
    TilePreviewHelper.singleTimelineEntryTileBuilder(
        edgeContentLayout(context, tileRequest.deviceConfiguration)
    ).build()
}
