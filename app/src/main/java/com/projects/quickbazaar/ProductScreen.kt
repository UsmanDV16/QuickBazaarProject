package com.projects.quickbazaar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange
import com.projects.quickbazaar.ui.theme.theme_red
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@Composable
fun ProductImageSlider(
    images: List<String>,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope() // Scoped to the Composable lifecycle

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp).background(Color.Black)
    ) {
        // Horizontal Pager for images
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().padding(start=65.dp)
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(200.dp)
            )
        }

        // Backward Button
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "Previous Image",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clickable {
                    coroutineScope.launch {
                        val previousPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                        pagerState.animateScrollToPage(previousPage)
                    }
                }
                .padding(8.dp)
        )

        // Forward Button
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Next Image",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .clickable {
                    coroutineScope.launch {
                        val nextPage = (pagerState.currentPage + 1).coerceAtMost(images.size - 1)
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
                .padding(8.dp)
        )

        // Indicator Row
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) theme_orange else Color.Gray)
                )
            }
        }
    }
}



@Composable
fun ProductScreen(
    navController: NavHostController,
    productId: String,
    productViewModel: ProductDetailViewModel = viewModel()
) {
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {}
    }
    // Observe data from the ViewModel
    val product = productViewModel._product
    val reviews = productViewModel._reviews
    val exploreProducts = productViewModel._exploreProducts
    val isAddedToCart = productViewModel._isAddedToCart
    var pagerState= rememberPagerState(0, 0F, { 0 })
    if(!product.value.images.isEmpty()) {
        pagerState = rememberPagerState(

            initialPage = 0,
            initialPageOffsetFraction = 0F,
            pageCount = { product.value.images.size }
        )
    }


    LaunchedEffect(productId) {
        if(productViewModel._product.value.name==""||productViewModel._product.value.id!=productId)
            productViewModel.loadProduct(productId)
        productViewModel.is_in_cart(productId)
    }

    Column(
        modifier=Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(top=16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .size(45.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        LazyColumn(
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
        ) {
            // Product Images
            item {
                ProductImageSlider(images = product.value.images, pagerState)
            }

            // Product Details
            item {
                Text(
                    text = product.value.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        painterResource(id = R.drawable.star), contentDescription = "review stars",
                        tint = theme_blue, modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${product.value.rating} (${product.value.totalReviews} Reviews)",
                        fontSize = 14.sp,
                        color = theme_blue
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(
                        text = "Rs. ${product.value.price}",
                        fontSize = 27.sp,
                        color = theme_red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    "Description",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = theme_blue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = product.value.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Reviews Section
            item {
                Text(
                    text = "Reviews",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_blue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(field_grey, shape = RoundedCornerShape(25.dp))
                        .height(100.dp)
                        .padding(8.dp),
                ) {
                    LazyColumn() {
                        items(reviews) { review ->
                            ReviewItem(review = review)
                        }

                    }
                }
            }

            // Explore Products Section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "- Explore -",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme_blue,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            items(exploreProducts.chunked(2)) { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { product ->
                        ProductCard(
                            navController,
                            product = product,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth().height(90.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    productViewModel.addToCart(productId)

                },
                enabled = !isAddedToCart.value,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .padding(vertical = 10.dp),
                border = BorderStroke(3.dp, theme_blue),
                shape = RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(
                    containerColor = theme_orange, disabledContainerColor = field_grey,
                    disabledContentColor = Color.Gray
                )
            ) {
                Text(
                    text = "Add to Cart",
                    color = theme_blue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )

            }
        }


        }
    }



// Fixed ReviewItem Component
@Composable
fun ReviewItem(review:Review)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
modifier = Modifier.padding(5.dp)
        )
        {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Row(horizontalArrangement = Arrangement.Start)
                {
                    Icon(
                        painterResource(id = R.drawable.profile_icon),
                        contentDescription = "profile",
                        tint = theme_blue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        review.username,
                        color = theme_blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Row()
                {
                    RatingStars(rating = review.rating)
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                review.text,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

        }
    }
}


@Composable
fun RatingStars(rating: Int, maxRating: Int = 5) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxRating) {
            Icon(
                painter = painterResource(
                    id = if (i <= rating) R.drawable.star_filled else R.drawable.star
                ),
                contentDescription = null,
                tint = theme_blue, // Adjust color as needed
                modifier = Modifier.size(15.dp)
            )
        }
    }
}
