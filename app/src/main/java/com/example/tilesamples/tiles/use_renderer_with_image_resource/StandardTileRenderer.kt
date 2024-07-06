package com.example.tilesamples.tiles.use_renderer_with_image_resource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.LayoutElementBuilders.Image
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.ResourceBuilders.ImageResource
import androidx.wear.protolayout.ResourceBuilders.Resources
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.render.SingleTileLayoutRenderer
import java.nio.ByteBuffer

data class SimpleTileState(
    val text: String
)

@OptIn(ExperimentalHorologistApi::class)
class StandardTileRenderer(context: Context) :
    SingleTileLayoutRenderer<SimpleTileState, Bitmap?>(context) {
    override fun renderTile(
        state: SimpleTileState,
        deviceParameters: DeviceParameters
    ): LayoutElement {
        return tileLayout(state, deviceParameters)
    }

    private fun tileLayout(
        state: SimpleTileState,
        deviceParameters: DeviceParameters
    ): LayoutElement {
        return PrimaryLayout.Builder(deviceParameters)
            .setContent(
                Image.Builder()
                    .setResourceId("image_id")
                    .setWidth(dp(50f))
                    .setHeight(dp(50f))
                    .build()
            ).setPrimaryLabelTextContent(
                Text.Builder(context, state.text)
                    .setColor(argb(Colors.DEFAULT.onSurface))
                    .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                    .build()
            ).build()
    }

    override fun Resources.Builder.produceRequestedResources(
        resourceState: Bitmap?,
        deviceParameters: DeviceParameters,
        resourceIds: List<String>
    ) {
        resourceState?.let {
            addIdToImageMapping("image_id", bitmapToImageResource(resourceState))
        }
    }
}

fun bitmapToImageResource(bitmap: Bitmap): ImageResource {
    val safeBitmap = bitmap.copy(Bitmap.Config.RGB_565, false)

    val byteBuffer = ByteBuffer.allocate(safeBitmap.byteCount)
    safeBitmap.copyPixelsToBuffer(byteBuffer)
    val bytes: ByteArray = byteBuffer.array()

    return ImageResource.Builder().setInlineResource(
        ResourceBuilders.InlineImageResource.Builder()
            .setData(bytes)
            .setWidthPx(bitmap.width)
            .setHeightPx(bitmap.height)
            .setFormat(ResourceBuilders.IMAGE_FORMAT_RGB_565)
            .build()
    )
        .build()
}

@Preview
fun simpleTileRendererPreview(context: Context): TilePreviewData {
    val state = SimpleTileState("Hello World!")
    val renderer = StandardTileRenderer(context)

    return TilePreviewData(
        onTileResourceRequest = resources {
            val bitmap = createBitmap(100, 100, Bitmap.Config.RGB_565)
            bitmap.eraseColor(Color.WHITE)
            addIdToImageMapping("image_id", bitmapToImageResource(bitmap))
        }
    ) { tileRequest ->
        renderer.renderTimeline(state, tileRequest)
    }
}

internal fun resources(fn: Resources.Builder.() -> Unit): (RequestBuilders.ResourcesRequest) -> Resources =
    {
        Resources.Builder().setVersion(it.version).apply(fn).build()
    }
