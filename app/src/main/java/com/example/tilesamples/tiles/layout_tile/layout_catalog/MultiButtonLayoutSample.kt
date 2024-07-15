package com.example.tilesamples.tiles.layout_tile.layout_catalog

import android.content.Context
import androidx.wear.protolayout.ActionBuilders.LoadAction
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Box
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.Modifiers
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.layouts.MultiButtonLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.example.tilesamples.tiles.layout_tile.MERGED_LAYOUT
import com.example.tilesamples.tiles.layout_tile.createTile

val multiButtonLayout: (Context, DeviceParameters) -> LayoutElement =
    { context, _ ->
        Box.Builder()
            .setModifiers(
                Modifiers.Builder()
                    .setClickable(
                        Clickable.Builder()
                            .setId(MERGED_LAYOUT)
                            .setOnClick(LoadAction.Builder().build())
                            .build()
                    )
                    .build()
            )
            .setHeight(DimensionBuilders.expand())
            .setWidth(DimensionBuilders.expand())
            .addContent(MultiButtonLayout.Builder()
                .apply {
                    (0..2).forEach {
                        addButtonContent(
                            Button.Builder(
                                context,
                                Clickable.Builder().build()
                            )
                                .setTextContent("$it")
                                .build()
                        )
                    }
                }
                .build()
            ).build()
    }

@Preview
fun multiButtonLayoutPreview(context: Context): TilePreviewData {
    return TilePreviewData { tileRequest ->
        createTile(context, tileRequest.deviceConfiguration, multiButtonLayout)
    }
}
