package com.revoio.composeads.banner_ad

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError


internal fun loadBannerAd(
    bannerAdView: AdView,
    isPremiumUser : Boolean,
    isNetworkAvailable : Boolean,
    logger : (String) -> Unit,
    onAdLoaded: (() -> Unit)? = null,
    onAdFailed: (() -> Unit)? = null
) {
    if (!isPremiumUser && isNetworkAvailable) {
        logger("Loading banner ad...")

        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)

        bannerAdView.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                logger("onAdClosed:")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                bannerAdView.isVisible = false
                logger("onAdFailedToLoad: ${loadAdError.message}")
                onAdFailed?.invoke()
            }

            override fun onAdImpression() {
                super.onAdImpression()
                logger("onAdImpression:")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                bannerAdView.isVisible = true
                logger("onAdLoaded: ${bannerAdView.adUnitId}")
                onAdLoaded?.invoke()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                logger("onAdOpened:")
            }
        }
    } else {
        bannerAdView.isVisible = false
        onAdFailed?.invoke()
    }
}

internal fun destroyBanner(bannerAdView: AdView) {
    bannerAdView.destroy()
}

internal fun Activity.getAdSize(): AdSize {
    val windowManager = this.windowManager

    val widthPixels: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets =
            windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
        val bounds = windowMetrics.bounds
        bounds.width() - insets.left - insets.right
    } else {
        val outMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(outMetrics)
        outMetrics.widthPixels
    }

    val density = this.resources.displayMetrics.density
    val adWidth = (widthPixels / density).toInt()

    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
}