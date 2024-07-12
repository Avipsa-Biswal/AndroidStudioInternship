package com.example.Androidintern345.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.Androidintern345.Model.Data.Product
import com.example.Androidintern345.ViewModel.ProductViewModel

@Composable
fun ProductScreen(productVm:ProductViewModel, modifier: Modifier = Modifier){
    val products by productVm.products.observeAsState(emptyList())
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(products){
            ProductItem(product = it)
        }
    }
}


@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(150.dp)) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                IconButton(
                    onClick = { /* Handle Favorite button click */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Rating: ${product.rating.rate} (${product.rating.count})")
                Text(text = product.title)
                Text(text = "${product.price} $", fontSize = 20.sp, color = Color.Red)
            }
        }
    }
}



@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = CardDefaults.cardElevation(9.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = { /* Handle Home button click */ }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon" ,
                    tint = Color.Red,
                    modifier = Modifier.size(35.dp)
                )
            }
            IconButton(onClick = { /* Handle Shopping Cart button click */ }) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Shopping Cart Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
            IconButton(onClick = { /* Handle Home button click */ }) {
                Icon(
                    imageVector = Icons.Outlined.Shop,
                    contentDescription = "Shop Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
            IconButton(onClick = { /* Handle Shopping Cart button click */ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "FavoriteBorder Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
            IconButton(onClick = { /* Handle Home button click */ }) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Person Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}
