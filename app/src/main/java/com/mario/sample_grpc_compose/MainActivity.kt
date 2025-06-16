package com.mario.sample_grpc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mario.grpc.ui.theme.SamplegrpccomposeTheme
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.launch
import pb.CategoryOuterClass
import pb.CategoryServiceGrpcKt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channel = ManagedChannelBuilder
            .forAddress("192.168.3.11", 50051)
            .usePlaintext()
            .build()

        val stub = CategoryServiceGrpcKt.CategoryServiceCoroutineStub(channel)

        enableEdgeToEdge()
        setContent {
            SamplegrpccomposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        InputCategory(
                            stub = stub
                        )
                    }
                ) { innerPadding ->
                    CategoryList(
                        modifier = Modifier.padding(innerPadding),
                        stub = stub
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryList(
    stub: CategoryServiceGrpcKt.CategoryServiceCoroutineStub,
    modifier: Modifier
) {
    val categories = remember { mutableStateListOf<CategoryOuterClass.Category>() }

    LaunchedEffect(Unit) {
        stub.listCategoriesStream(
            CategoryOuterClass.blank.newBuilder().build()
        ).collect { response ->
            categories.add(response)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(categories.size) { index ->
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = categories[index].name
                )
            }
        }
    }
}

@Composable
private fun InputCategory(
    stub: CategoryServiceGrpcKt.CategoryServiceCoroutineStub
) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom = 30.dp)
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Category Name") },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    stub.createCategory(
                        CategoryOuterClass.CreateCategoryRequest.newBuilder().setName(text)
                            .build()
                    )
                    text = ""
                }
            }
        ) {
            Text(text = "Send")
        }
    }
}