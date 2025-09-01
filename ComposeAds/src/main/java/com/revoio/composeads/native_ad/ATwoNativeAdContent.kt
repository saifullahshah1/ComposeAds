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
fun ATwoNativeAdContent(
    nativeAd: NativeAd,
    adView: NativeAdView,
    composeView: View,
    ctaColor: Color,
    backgroundColor: Color = Color(0xCCFFFFFF),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 15.dp)
            .padding(top = 3.dp, bottom = 6.dp)
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
                    modifier = Modifier.size( 50.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {

                Text(
                    text = nativeAd.headline ?: "",
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1E1F23),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .padding(start = if (icon != null) 14.dp else 0.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(
                            start = if (icon != null) 16.dp else 0.dp
                        )
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFFFD9900), shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 3.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "AD",
                            lineHeight = 12.sp,
                            fontSize = 8.sp,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = Color(0xFFFFFFFF),
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = nativeAd.body ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),                        fontSize = 10.sp,
                        lineHeight = 14.sp,
                        maxLines = 2,
                        color = Color(0xFF4F4F4F),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        adView.setNativeAd(nativeAd)

        nativeAd.callToAction?.let { cta ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ctaColor)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .combinedClickable { composeView.performClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    text = cta.uppercase(),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}