package com.revoio.composeads.native_ad

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun ComposeNativeAd(
    nativeAd: NativeAd?,
    isNativeLoaded: Boolean = false,
    adType: NativeAdType = NativeAdType.NONE,
    backgroundColor: Color = Color(0xCCFFFFFF),
    ctaColor: Color = Color(0xFF0265DC),
    hideNativeAdContainer: () -> Unit
) {
    val shape = when (adType) {
        NativeAdType.A_ONE -> RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
        NativeAdType.A_THREE -> RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
        NativeAdType.C_ONE, NativeAdType.D_ONE -> RoundedCornerShape(12.dp)
        else -> RoundedCornerShape(0.dp)
    }

    val borderModifier = if (adType == NativeAdType.C_ONE || adType == NativeAdType.D_ONE) {
        Modifier.border(1.dp, Color(0xFF666666), shape)
    } else Modifier

    val loadingHeight = when (adType) {
        NativeAdType.A_ONE, NativeAdType.A_THREE -> 110.dp
        NativeAdType.A_TWO -> 120.dp
        NativeAdType.D_ONE -> 260.dp
        NativeAdType.C_ONE -> 125.dp
        else -> 0.dp
    }

    val content: (@Composable (NativeAd, NativeAdView, View) -> Unit)? = when (adType) {
        NativeAdType.A_ONE, NativeAdType.A_THREE -> { ad, adView, contentView ->
            AOneThreeNativeAdContent(ad, adView, contentView, ctaColor, backgroundColor)
        }
        NativeAdType.A_TWO -> { ad, adView, contentView ->
            ATwoNativeAdContent(ad, adView, contentView, ctaColor, backgroundColor)
        }
        NativeAdType.C_ONE, NativeAdType.D_ONE -> { ad, adView, contentView ->
            OldNativeAdContent(ad, adView, contentView, ctaColor, adType)
        }
        else -> null
    }

    if (content != null) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape)
                .then(borderModifier)
                .padding(5.dp)
        ) {
            if (isNativeLoaded) {
                NativeAdView(nativeAd) { ad, adView, contentView ->
                    ad?.let { content(it, adView, contentView) } ?: hideNativeAdContainer()
                }
            } else {
                LoadingAdContainer(loadingHeight)
            }
        }
    }
}

@Composable
fun LoadingAdContainer(containerHeight : Dp) {
    Text(
        text = "Loading Ad",
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .wrapContentHeight(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        color = Color.Gray
    )
}

/*
*
* @Composable
fun ComposeNativeAd(
    nativeAd: NativeAd?,
    isNativeLoaded: Boolean = false,
    adType: NativeAdType = NativeAdType.NONE,
    backgroundColor : Color = Color(0xCCFFFFFF),
    ctaColor: Color = Color(0xFF0265DC),
    hideNativeAdContainer: () -> Unit
) {

    when (adType) {

        NativeAdType.A_ONE , NativeAdType.A_THREE-> {

            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(
                        topStart = if(adType == NativeAdType.A_ONE) 20.dp else 0.dp,
                        topEnd = if(adType == NativeAdType.A_ONE) 20.dp else 0.dp,
                        bottomEnd = 20.dp,
                        bottomStart = 20.dp))
                    .padding(5.dp),
            ) {
                if (isNativeLoaded) {
                    NativeAdView(nativeAd) { ad, adView, contentView ->
                        ad?.let {
                            AOneThreeNativeAdContent(ad, adView, contentView, ctaColor, backgroundColor)
                        } ?: hideNativeAdContainer()
                    }
                } else {
                    LoadingAdContainer(110.dp)
                }
            }
        }

        NativeAdType.A_TWO -> {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(5.dp),
            ) {
                if (isNativeLoaded) {
                    NativeAdView(nativeAd) { ad, adView, contentView ->
                        ad?.let {
                            ATwoNativeAdContent(ad, adView, contentView, ctaColor, backgroundColor)
                        } ?: hideNativeAdContainer()
                    }
                } else {
                    LoadingAdContainer(120.dp)
                }
            }
        }

        NativeAdType.B_ONE -> {}


        NativeAdType.C_ONE, NativeAdType.D_ONE -> {
            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFF666666), RoundedCornerShape(12.dp))
                    .padding(5.dp),
            ) {
                if (isNativeLoaded) {
                    NativeAdView(nativeAd) { ad, adView, contentView ->
                        ad?.let {
                            OldNativeAdContent(ad, adView, contentView, ctaColor, adType)
                        } ?: hideNativeAdContainer()
                    }
                } else {
                    LoadingAdContainer(if(adType == NativeAdType.D_ONE) 260.dp else 125.dp)
                }
            }
        }

        else -> {}

    }

}
* */