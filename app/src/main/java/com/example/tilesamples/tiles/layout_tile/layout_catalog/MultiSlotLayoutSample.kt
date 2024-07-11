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
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.MultiSlotLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.example.tilesamples.tiles.layout_tile.MULTI_BUTTON_LAYOUT
import com.example.tilesamples.tiles.layout_tile.createTile

val multiSlotLayout: (Context, DeviceParameters) -> LayoutElement = { context, _ ->
    Box.Builder()
        .setModifiers(
            Modifiers.Builder()
                .setClickable(
                    Clickable.Builder()
                        .setId(MULTI_BUTTON_LAYOUT)
                        .setOnClick(LoadAction.Builder().build())
                        .build()
                )
                .build()
        )
        .setHeight(DimensionBuilders.expand())
        .setWidth(DimensionBuilders.expand())
        .addContent(
            MultiSlotLayout.Builder()
                .apply {
                    (0..10).forEach {
                        addSlotContent(
                            Text.Builder(context, "$it")
                                .setColor(argb(Colors.DEFAULT.onSurface))
                                .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                                .build()
                        )
                    }
                }
                .build()
        ).build()
}

@Preview
fun multiSlotLayoutPreview(context: Context): TilePreviewData {
    return TilePreviewData { tileRequest ->
        createTile(context, tileRequest.deviceConfiguration, multiSlotLayout)
    }
}
