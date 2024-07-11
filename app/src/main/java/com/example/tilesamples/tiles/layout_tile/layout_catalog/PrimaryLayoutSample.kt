package com.example.tilesamples.tiles.layout_tile.layout_catalog

import android.content.Context
import androidx.wear.protolayout.ActionBuilders.LoadAction
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Box
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.Modifiers
import androidx.wear.protolayout.material.Chip
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.CompactChip
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.example.tilesamples.tiles.layout_tile.MULTI_SLOT_LAYOUT
import com.example.tilesamples.tiles.layout_tile.createTile

val simpleLayout: (Context, DeviceParameters) -> LayoutElement = { context, deviceParameters ->
    Box.Builder()
        .setModifiers(
            Modifiers.Builder()
                .setClickable(
                    Clickable.Builder()
                        .setId(MULTI_SLOT_LAYOUT)
                        .setOnClick(LoadAction.Builder().build())
                        .build()
                )
                .build()
        )
        .setHeight(DimensionBuilders.expand())
        .setWidth(DimensionBuilders.expand())
        .addContent(
            PrimaryLayout.Builder(deviceParameters)
                .setContent(
                    Chip.Builder(
                        context,
                        Clickable.Builder().build(),
                        deviceParameters
                    )
                        .setWidth(DimensionBuilders.expand())
                        .setPrimaryLabelContent("Content")
                        .build()
                )
                .setPrimaryChipContent(
                    CompactChip.Builder(
                        context,
                        "pChip",
                        Clickable.Builder().build(),
                        deviceParameters
                    ).build()
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
fun simpleLayoutPreview(context: Context): TilePreviewData {
    return TilePreviewData { tileRequest ->
        createTile(context, tileRequest.deviceConfiguration, simpleLayout)
    }
}
