package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.HomeTextSecondary
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun CustomField(label: String, placeholder: String) {

    Column(modifier = Modifier.padding(bottom = 14.dp)) {

        Text(label, fontSize = 12.sp, color = HomeTextSecondary)

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF3F3F3),
                focusedContainerColor = Color(0xFFF3F3F3),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = PrimaryOrange
            )
        )
    }
}