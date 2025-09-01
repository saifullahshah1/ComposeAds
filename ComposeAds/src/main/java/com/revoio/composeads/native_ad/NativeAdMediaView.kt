package com.revoio.composeads.native_ad

import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAdMediaView(
    nativeAd: NativeAd,
    adView: NativeAdView,
) {
    AndroidView(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(138.dp),
        factory = { context ->
            MediaView(context).apply {
                setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            }
        },
        update = { mediaView ->
            nativeAd.mediaContent?.let { mediaContent ->
                mediaView.mediaContent = mediaContent
            }

            if (adView.mediaView !== mediaView) {
                adView.mediaView = mediaView
            }

            adView.setNativeAd(nativeAd)
        }
    )
}