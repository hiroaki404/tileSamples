package com.example.tilesamples.tiles.use_renderer_with_image_resource

import android.graphics.drawable.BitmapDrawable
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import coil.Coil
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

const val imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhoo1K7MPUKW-dzDxsRKYw-9ZUgu6PCs7u-4v9Mj8gMau7N1ewfC16IusZT9U-qBnTR2u_rI_BCLEc9-sVV0M32e-ABz2dPN2tKBMu26ZbAr5dahgTmCvlz5LwpGrU4SSuID6Ns7k6kQytZ/s800/bird_maruitori.png"

@OptIn(ExperimentalHorologistApi::class)
class UseRendererWithImageResourceTileService: SuspendingTileService() {
    private lateinit var renderer: StandardTileRenderer

    override fun onCreate() {
        super.onCreate()
        renderer = StandardTileRenderer(this)
    }

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        val image = coroutineScope {
            async {
                Coil.imageLoader(this@UseRendererWithImageResourceTileService).run {
                    val request = ImageRequest.Builder(this@UseRendererWithImageResourceTileService)
                        .data(imageUrl)
                        .size(64)
                        .allowRgb565(true)
                        .transformations(CircleCropTransformation())
                        .allowHardware(false)
                        .build()
                    val result = execute(request)
                    (result.drawable as? BitmapDrawable)?.bitmap
                }
            }
        }.await()

        return renderer.produceRequestedResources(image, requestParams)
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val state = SimpleTileState("hello world")
        return renderer.renderTimeline(state, requestParams)
    }
}
