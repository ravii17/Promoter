package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.eventhostmodule.ui.theme.*

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",   // ✅ default added (fixes your error)
    placeholder: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,

        // ✅ Label fix (avoid empty label UI bug)
        label = if (label.isNotEmpty()) {
            { Text(label) }
        } else null,

        placeholder = {
            Text(
                text = placeholder,
                color = LoginTextTertiary
            )
        },

        readOnly = readOnly,

        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = LoginIconGray
                )
            }
        },

        trailingIcon = trailingIcon,

        singleLine = true,

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),

        visualTransformation =
            if (isPassword && !isPasswordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(12.dp),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = LoginInputBg,
            disabledContainerColor = LoginInputBg,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedTextColor = LoginTextPrimary,
            unfocusedTextColor = LoginTextPrimary,

            focusedLabelColor = LoginTextPrimary,
            unfocusedLabelColor = LoginTextPrimary,

            focusedPlaceholderColor = LoginTextTertiary,
            unfocusedPlaceholderColor = LoginTextTertiary,

            focusedLeadingIconColor = LoginIconGray,
            unfocusedLeadingIconColor = LoginIconGray
        )
    )
}