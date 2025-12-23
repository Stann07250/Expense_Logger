package com.example.expense_logger

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class Receipt(
    val amount: Double,
    val timeAdded: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val receipts = remember { mutableStateListOf<Receipt>() }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let {
            val amount = it.getDoubleExtra("amount", 0.0)
            val time = it.getStringExtra("timestamp") ?: ""
            receipts.add(Receipt(amount, time))
        }
    }

    val total = receipts.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "UNCLASSIFIED")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                launcher.launch(
                    Intent(context, AddReceiptActivity::class.java)
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Expense Receipt")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(receipts) {
                Text("£${it.amount} - ${it.timeAdded}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Total to Claim: £%.2f".format(total))
    }
}
