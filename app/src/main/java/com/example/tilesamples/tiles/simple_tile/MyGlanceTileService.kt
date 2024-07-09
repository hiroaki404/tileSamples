package com.example.tilesamples.tiles.simple_tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.wear.tiles.GlanceTileService
import androidx.glance.wear.tiles.action.ActionCallback
import androidx.glance.wear.tiles.action.actionRunCallback

/**
 * As of July 2024, dynamic expressions are not supported in Android Glance.   Additionally, TimelineEntry is currently limited to a single entry.
 */
class MyGlanceTileService : GlanceTileService() {
    @Composable
    @GlanceComposable
    override fun Content() {
        TileContent()
    }
}

@Composable
@GlanceComposable
fun TileContent() {
    Row {
        Button(
            modifier = GlanceModifier.padding(5.dp).background(Color.Cyan),
            text = "text",
            onClick = actionRunCallback<ClickAddAction>()
        )
        Text("hello")

    }
//    SingleEntityTemplate(
//        SingleEntityTemplateData(
//            textBlock = TextBlock(TemplateText("hello")),
//        )
//    )
}

class ClickAddAction: ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId) {
    }
}
