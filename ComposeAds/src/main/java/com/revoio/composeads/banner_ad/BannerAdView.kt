package com.revoio.composeads.banner_ad

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdView

@Composable
internal fun BannerAdView(
    adView: AdView,
    modifier: Modifier = Modifier,
    backgroundColor : Color = Color(0x33ACACAC)
) {

    AndroidView(
        modifier = modifier
            .wrapContentSize()
            .padding(vertical = 2.dp),
        factory = {
            adView.apply {
                val width = ViewGroup.LayoutParams.MATCH_PARENT
                val height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutParams = FrameLayout.LayoutParams(width, height)
                setBackgroundColor(backgroundColor.toArgb())
            }
        }
    )

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose {
            adView.pause()
        }
    }
}