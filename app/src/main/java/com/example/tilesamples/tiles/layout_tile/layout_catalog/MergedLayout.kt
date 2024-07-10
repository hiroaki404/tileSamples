package com.example.tilesamples.tiles.layout_tile.layout_catalog

import android.content.Context
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.example.tilesamples.tiles.layout_tile.createTile

val mergedLayout: (Context, DeviceParameters) -> LayoutElement = { context, deviceParameters ->
    PrimaryLayout.Builder(deviceParameters)
        .setContent(
            multiButtonLayout(context, deviceParameters)
        )
        .setPrimaryChipContent(
            multiSlotLayout(context, deviceParameters)

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
}

@Preview
fun mergedLayoutPreview(context: Context): TilePreviewData {
    return TilePreviewData { tileRequest ->
        createTile(context, tileRequest.deviceConfiguration, mergedLayout)
    }
}