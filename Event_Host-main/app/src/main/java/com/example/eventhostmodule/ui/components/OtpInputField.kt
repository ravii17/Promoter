package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.OtpInputGray
import com.example.eventhostmodule.ui.theme.OtpPrimary

@Composable
fun OtpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    focusRequester: FocusRequester? = null
) {
    LaunchedEffect(isFocused) {
        if (isFocused && focusRequester != null) {
            focusRequester.requestFocus()
        }
    }
    
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = modifier
            .width(64.dp)
            .height(80.dp)
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            autoCorrect = false
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = OtpInputGray,
            unfocusedContainerColor = OtpInputGray,
            disabledContainerColor = OtpInputGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = OtpPrimary
        ),
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = "0",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
