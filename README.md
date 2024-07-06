# TileSamples
This repository provides sample code demonstrating how to update tiles and create tile UIs.

## TileService
Note: Tiles will not update after building and running the app. You need to remove and re-add the tile to see the updates.

### NormalSuspendingTileService
Simply displays "Hello World!".

![NormalSuspendingTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/adf4d65b-0da2-4e3b-97ce-1a905b886038)

### DynamicExpressionTileService
This is a Count Up tile.
It actually displays the seconds part of the current time.
Uses Dynamic Expression.

![DynamicExpressionTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/9ae76dc6-f069-4201-9b8a-87d730551340)

### PeriodicUpdateTileService
A new column is added every 60 seconds.
Creates multiple TimelineEntry objects.

![PeriodicUpdateTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/30a9d13b-ae93-4a62-a068-762b9239386b)

### RefreshTileService
Displays the current time but updates only every 60 seconds.
Uses `setFreshnessIntervalMillis()`.

![RefreshTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/7ce63898-c0e6-462a-ab7c-3614a7deecad)

### RequestUpdateTileService
Displays the current time but updates only every 60 seconds.
Intended for external updates.
Important: In a production environment, do not use GlobalScope. Use WorkManager or other appropriate tools instead.
Updates continue for 10 minutes after adding the tile.

![RefreshTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/7ce63898-c0e6-462a-ab7c-3614a7deecad)

### UseRendererWithImageResourceTileService
The image is fetched remotely.
The Renderer class is used for this purpose.

![UseRendererWithImageResourceTileService](https://github.com/hiroaki404/tileSamples/assets/48251111/004ccf46-93ce-4558-a584-8046e85fbc01)

## official samples
- [android/codelab\-wear\-tiles](https://github.com/android/codelab-wear-tiles)
- [wear\-os\-samples/WearTilesKotlin at main · android/wear\-os\-samples](https://github.com/android/wear-os-samples/tree/main/WearTilesKotlin)
