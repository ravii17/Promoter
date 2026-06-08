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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.HomeTextPrimary
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun CustomTextField(
    label: String,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    Column(modifier = Modifier.padding(bottom = 16.dp)) {

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = HomeTextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape = RoundedCornerShape(30.dp),

            colors = OutlinedTextFieldDefaults.colors(   // ✅ HERE
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = PrimaryOrange,

                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5),

                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = PrimaryOrange,

                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            ),

            trailingIcon = trailingIcon
        )
    }
}