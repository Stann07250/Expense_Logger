package com.example.expense_logger

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

class AddReceiptActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var amountText by remember { mutableStateOf("") }
            var photoTaken by remember { mutableStateOf(false) }

            val cameraLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.TakePicturePreview()
                ) { bitmap: Bitmap? ->
                    if (bitmap != null) {
                        photoTaken = true
                    }
                }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(text = "Add Receipt")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { cameraLauncher.launch(null) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (photoTaken) "Photo Taken" else "Take Photo")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount (£)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val time = SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale.UK
                        ).format(Date())

                        val result = Intent()
                        result.putExtra("amount", amountText.toDoubleOrNull() ?: 0.0)
                        result.putExtra("timestamp", time)
                        setResult(Activity.RESULT_OK, result)
                        finish()
                    },
                    enabled = photoTaken,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Receipt")
                }
            }
        }
    }
}
