package com.revoio.composeadsexample.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

object  NativeAdManager {

    /** Inner Native */

    private var innerNativeAd: NativeAd? = null
    private var isInnerNativeAdLoading = false
    var innerAdLoadListener: AdLoadListener? = null

    fun loadInnerNativeAd(context: Context, adUnitId: String, isPremium: Boolean = false, logger : (String) -> Unit) {
        if (innerNativeAd != null || isInnerNativeAdLoading) {
            logger("PreLoadNative: Inner Native ad is already loaded or loading")
            return  // Already loaded or loading
        }

        if (isPremium) {
            innerAdLoadListener?.onGenericAdFailedToLoad("PREMIUM")
            return
        }

        logger("PreLoadNative: Inner Native Loading Native Ad with ID: $adUnitId")

        isInnerNativeAdLoading = true
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad ->
                innerNativeAd = ad
                isInnerNativeAdLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)

                    logger("Inner Native onAdFailedToLoad: $p0")
                    isInnerNativeAdLoading = false
                    innerNativeAd = null
                    innerAdLoadListener?.onGenericAdFailedToLoad(p0.message)
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()

                    logger("Inner native onAdLoaded:")
                    innerAdLoadListener?.onGenericNativeAdLoaded(innerNativeAd)
                }

                override fun onAdImpression() {
                    super.onAdImpression()

                    logger("Inner native onAdImpression:")
                    innerNativeAd = null
                    innerAdLoadListener?.onGenericAdImpression()
                }

                override fun onAdClicked() {
                    super.onAdClicked()

                    logger("Inner native onAdClicked:")
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT).build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun getInnerNativeAd(): NativeAd? {
        return innerNativeAd
    }

    fun isInnerNativeAdLoading(): Boolean {
        return isInnerNativeAdLoading
    }

}

interface AdLoadListener {
    fun onGenericNativeAdLoaded(nativeAd: NativeAd?) {}
    fun onGenericAdFailedToLoad(error: String) {}
    fun onGenericAdImpression(){}
}