package com.example.tilesamples.tiles.layout_tile.layout_catalog

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.layouts.MultiButtonLayout
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.example.tilesamples.tiles.layout_tile.createTile

val multiButtonLayout: (Context, DeviceParameters) -> LayoutElementBuilders.LayoutElement =
    { context, _ ->
        MultiButtonLayout.Builder()
            .apply {
                (0..2).forEach {
                    addButtonContent(
                        Button.Builder(context, ModifiersBuilders.Clickable.Builder().build())
                            .setTextContent("$it")
                            .build()
                    )
                }
            }
            .build()
    }

@Preview
fun multiButtonLayoutPreview(context: Context): TilePreviewData {
    return TilePreviewData { tileRequest ->
        createTile(context, tileRequest.deviceConfiguration, multiButtonLayout)
    }
}
