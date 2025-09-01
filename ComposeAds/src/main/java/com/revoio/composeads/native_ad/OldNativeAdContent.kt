package com.revoio.composeads.native_ad

import android.graphics.drawable.Drawable
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OldNativeAdContent(
    nativeAd: NativeAd,
    adView: NativeAdView,
    composeView: View,
    ctaColor : Color,
    adType: NativeAdType
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon: Drawable? = nativeAd.icon?.drawable
            icon?.let { drawable ->
                AsyncImage(
                    model = drawable,
                    contentDescription = null,
                    modifier = Modifier.size(52.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = nativeAd.headline ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false).padding(start = if(icon != null)8.dp else 0.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFD9900),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "AD",
                            color = Color(0xFFFFFFFF),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(start = if(icon != null)8.dp else 0.dp),
                    text = nativeAd.body ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (adType == NativeAdType.D_ONE && nativeAd.mediaContent != null) {
            NativeAdMediaView(nativeAd = nativeAd, adView = adView)
        } else {
            adView.setNativeAd(nativeAd)
        }

        nativeAd.callToAction?.let { cta ->
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ctaColor)
                    .combinedClickable { composeView.performClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 16.sp,
                    text = cta.uppercase(),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,

                )
            }
        }
    }
}
