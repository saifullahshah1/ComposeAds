package com.revoio.composeads.banner_ad

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.revoio.composeads.utils.isNetworkAvailable

@Composable
fun BannerAd(
    activity: Activity?,
    context: Context,
    bannerAdId : String,
    isPremium : Boolean = false,
    strokeColor : Color = Color(0xFFEEEEEE),
    backgroundColor : Color = Color(0x33ACACAC),
    loadingAdText : String = "Loading Ad",
    loadingAdTextColor : Color = Color.Gray,
    loadingAdTextBackgroundColor : Color = Color.LightGray,
    defaultBannerWidth : Int = 360
) {

    var adLoaded by remember { mutableStateOf(false) }
    var isPremium by remember { mutableStateOf(isPremium) }

    val adView = remember {
        AdView(context).apply {
            adUnitId = bannerAdId
            val adSize = activity?.getAdSize() ?: AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, defaultBannerWidth)
            setAdSize(adSize)
        }
    }

    LaunchedEffect(adView) {
        adLoaded = false
        loadBannerAd(
            bannerAdView = adView,
            isPremiumUser = isPremium,
            isNetworkAvailable = context.isNetworkAvailable(),
            logger = { logValue ->
                Log.d("ComposeAds", logValue)
            },
            onAdLoaded = {
                adLoaded = true
            },
            onAdFailed = {
                adLoaded = false
            }
        )
    }

    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(strokeColor),
        ) {
            if (!adLoaded) {

                Text(
                    text = loadingAdText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(2.dp)
                        .background(loadingAdTextBackgroundColor)
                        .wrapContentHeight(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = loadingAdTextColor
                )
            } else {
                BannerAdView(adView, Modifier, backgroundColor)
            }
        }
    }
}