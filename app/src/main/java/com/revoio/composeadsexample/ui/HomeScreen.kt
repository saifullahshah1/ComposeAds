package com.revoio.composeadsexample.ui

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.nativead.NativeAd
import com.revoio.composeadsexample.ads.AdLoadListener
import com.revoio.composeadsexample.ads.NativeAdManager
import com.revoio.composeads.banner_ad.BannerAd
import com.revoio.composeads.native_ad.ComposeNativeAd
import com.revoio.composeads.native_ad.NativeAdType
import com.revoio.composeadsexample.Constants

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val activity = LocalActivity.current

    var showNativeAd by remember { mutableStateOf(false) }
    var isNativeLoaded by remember { mutableStateOf(false) }

    fun hideNativeAd() {
        showNativeAd = false
    }

    fun loadInnerNativeAd() {
        NativeAdManager.innerAdLoadListener = object : AdLoadListener {
            override fun onGenericAdFailedToLoad(error: String) {
                super.onGenericAdFailedToLoad(error)
                hideNativeAd()
            }

            override fun onGenericNativeAdLoaded(nativeAd: NativeAd?) {
                super.onGenericNativeAdLoaded(nativeAd)

                NativeAdManager.getInnerNativeAd()?.let {
                    isNativeLoaded = true
                } ?: hideNativeAd()
            }

            override fun onGenericAdImpression() {
                super.onGenericAdImpression()
            }
        }

        if (!NativeAdManager.isInnerNativeAdLoading()) {
            NativeAdManager.loadInnerNativeAd(context, Constants.HOME_NATIVE_AD_UNIT_ID) { logger ->
                Log.d("ComposeAds", logger)
            }
        }
    }

    fun checkForInnerAd() {
        showNativeAd = true
        NativeAdManager.getInnerNativeAd()?.let {
            isNativeLoaded = true
        } ?: loadInnerNativeAd()
    }

    LaunchedEffect(null) {
        checkForInnerAd()
    }


    Column(
        modifier.fillMaxSize()
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {

        Text("Native")
        Spacer(Modifier.height(20.dp))
        Box() {
            if (showNativeAd) {
                ComposeNativeAd(
                    nativeAd = NativeAdManager.getInnerNativeAd(),
                    isNativeLoaded = isNativeLoaded,
                    backgroundColor = Color(0xFFEEEEEE),
                    ctaColor = Color(0xFFDD2828),
                    adType = NativeAdType.A_ONE
                ) { hideNativeAd() }
            }
        }
        Spacer(Modifier.height(20.dp))
        Box() {
            if (showNativeAd) {
                ComposeNativeAd(
                    nativeAd = NativeAdManager.getInnerNativeAd(),
                    isNativeLoaded = isNativeLoaded,
                    backgroundColor = Color(0xFFEEEEEE),
                    ctaColor = Color(0xFFDD2828),
                    adType = NativeAdType.A_TWO
                ) { hideNativeAd() }
            }
        }
        Spacer(Modifier.height(20.dp))
        Box() {
            if (showNativeAd) {
                ComposeNativeAd(
                    nativeAd = NativeAdManager.getInnerNativeAd(),
                    isNativeLoaded = isNativeLoaded,
                    backgroundColor = Color(0xFFEEEEEE),
                    ctaColor = Color(0xFFDD2828),
                    adType = NativeAdType.A_THREE
                ) { hideNativeAd() }
            }
        }
        Spacer(Modifier.height(20.dp))
        Box() {
            if (showNativeAd) {
                ComposeNativeAd(
                    nativeAd = NativeAdManager.getInnerNativeAd(),
                    isNativeLoaded = isNativeLoaded,
                    backgroundColor = Color(0xFFEEEEEE),
                    ctaColor = Color(0xFFDD2828),
                    adType = NativeAdType.C_ONE
                ) { hideNativeAd() }
            }
        }
        Text("Banner")
        Spacer(Modifier.height(20.dp))
         Box(modifier = Modifier.fillMaxWidth()) {
             /*BannerAd(
                 activity = activity,
                 context = context,
                 bannerAdId = Constants.HOME_BANNER_AD_UNIT_ID,
                 isPremium = false,
                 strokeColor = Color(0xFFEEEEEE),
                 backgroundColor = Color(0x33ACACAC),
                 loadingAdText = "Loading Ad",
                 loadingAdTextColor = Color.Gray,
                 loadingAdTextBackgroundColor = Color.LightGray,
                 defaultBannerWidth = 360
             )*/
         }
    }


}