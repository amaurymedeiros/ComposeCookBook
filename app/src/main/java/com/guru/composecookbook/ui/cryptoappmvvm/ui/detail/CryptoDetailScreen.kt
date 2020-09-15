package com.guru.composecookbook.ui.cryptoappmvvm.ui.detail

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.data.DemoDataProvider
import com.guru.composecookbook.theme.green700
import com.guru.composecookbook.theme.typography
import com.guru.composecookbook.ui.cryptoappmvvm.Models.Crypto
import com.guru.composecookbook.ui.cryptoappmvvm.utils.roundToTwoDecimals
import com.guru.composecookbook.ui.lists.VerticalListItemSmall
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun CryptoDetailScreen(crypto: Crypto, onBack: () -> Unit) {
    val crypto = remember { crypto }
    Scaffold(
        bottomBar = { CryptoBottomBar(crypto) },
        floatingActionButton = { CryptoFloatingActionButton() },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        val scrollState = rememberScrollState(0f)
        Stack {
            CryptoTopSection(crypto, scrollState, onBack)
            ScrollableColumn(modifier = Modifier.padding(16.dp), scrollState = scrollState) {
                Spacer(modifier = Modifier.height(150.dp))
                //TODO: Charts
                StatisticsSection(crypto)
                NewsSection(crypto)
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun CryptoTopSection(crypto: Crypto, scrollState: ScrollState, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp).drawOpacity(animate((1 - scrollState.value/200).coerceIn(0f, 1f)))) {
        Icon(
            asset = Icons.Default.ArrowBack,
            modifier = Modifier.padding(vertical = 8.dp).clickable(onClick = onBack)
        )
        Row(modifier = Modifier.padding(top = 20.dp)) {
            Text(
                text = crypto.name,
                style = typography.h6,
                modifier = Modifier.padding(end = 8.dp)
            )
            CoilImage(data = crypto.image, modifier = Modifier.preferredSize(28.dp))
        }

        Text(
            text = "${crypto.price.roundToTwoDecimals()} USD",
            style = typography.h6.copy(fontWeight = FontWeight.ExtraBold)
        )
        Text(
            text = "${crypto.dailyChange.roundToTwoDecimals()} " +
                    " (${crypto.dailyChangePercentage.roundToTwoDecimals()}%) Today",
            color = if (crypto.dailyChange > 0) green700 else Color.Red
        )
    }
}

@Composable
fun CryptoBottomBar(crypto: Crypto) {
    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        IconButton(onClick = {}) {
            Icon(asset = Icons.Default.ReadMore)
        }
        IconButton(onClick = {}) {
            Icon(asset = Icons.Default.MoreVert)
        }
    }
}

@Composable
fun CryptoFloatingActionButton() {
    ExtendedFloatingActionButton(
        icon = { Icon(asset = Icons.Default.Add) },
        text = { Text(text = "Trade") },
        onClick = {},
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun NewsSection(crypto: Crypto) {
    val valueModifier = Modifier.padding(bottom = 16.dp, top = 4.dp)
    Text(
        text = "Recent News",
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        style = typography.h5
    )
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            val demoItem = DemoDataProvider.item
            VerticalListItemSmall(item = demoItem)
            VerticalListItemSmall(item = demoItem)
            VerticalListItemSmall(item = demoItem)
            Text(
                text = "See More",
                modifier = Modifier.gravity(Alignment.End).clickable(onClick = {}).padding(16.dp)
            )
        }
      
    }
}

@Composable
fun StatisticsSection(crypto: Crypto) {
    val valueModifier = Modifier.padding(bottom = 16.dp, top = 4.dp)
    Text(
        text = "Statistics",
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        style = typography.h5
    )
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
           Column(modifier = Modifier.weight(1f)) {
               Text(text = "24 High", style = typography.subtitle2)
               Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
               Text(text = "24 Low", style = typography.subtitle2)
               Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
               Text(text = "24 High", style = typography.subtitle2)
               Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
           }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "24 High", style = typography.subtitle2)
                Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
                Text(text = "24 High", style = typography.subtitle2)
                Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
                Text(text = "24 High", style = typography.subtitle2)
                Text(text = crypto.high.roundToTwoDecimals(), modifier = valueModifier)
            }
        }
    }
}