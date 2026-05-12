package com.example.organisation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.annotation.RequiresApi
import android.os.Build

class CreateJobActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CreateJobScreen(onBackClick = { finish() })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(onBackClick: () -> Unit) {
    var jobTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var crewNeeded by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    var startDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var endDateTime by remember { mutableStateOf<LocalDateTime?>(null) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val orangePrimary = Color(0xFFF9A825)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Job", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Basic Info Section
            Text("Job Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            OutlinedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Job Title") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = orangePrimary, focusedLabelColor = orangePrimary)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Job Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = orangePrimary,
                    focusedLabelColor = orangePrimary
                )
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = crewNeeded,
                    onValueChange = { crewNeeded = it },
                    label = { Text("Crew Needed") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = orangePrimary, focusedLabelColor = orangePrimary)
                )
                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Pay / Budget") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    prefix = { Text("₹ ") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = orangePrimary, focusedLabelColor = orangePrimary)
                )
            }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = orangePrimary) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = orangePrimary, focusedLabelColor = orangePrimary)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFEEEEEE))

            // Schedule Section
            Text("Schedule", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            DateTimePickerCard(
                label = "Start Date & Time",
                selectedDateTime = startDateTime,
                onDateTimeSelected = { startDateTime = it },
                orangePrimary = orangePrimary
            )

            DateTimePickerCard(
                label = "End Date & Time",
                selectedDateTime = endDateTime,
                onDateTimeSelected = { endDateTime = it },
                orangePrimary = orangePrimary
            )

            // Validation Error
            if (startDateTime != null && endDateTime != null && !endDateTime!!.isAfter(startDateTime)) {
                Text(
                    text = "End date & time must be after start date & time",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Button(
                onClick = {
                    if (startDateTime != null && endDateTime != null && !endDateTime!!.isAfter(startDateTime)) {
                         Toast.makeText(context, "Please fix date validation", Toast.LENGTH_SHORT).show()
                    } else {
                         context.startActivity(Intent(context, CrewRequirementsActivity::class.java))
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
            ) {
                Text("Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerCard(
    label: String,
    selectedDateTime: LocalDateTime?,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    orangePrimary: Color
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDateTimePicker(context) { onDateTimeSelected(it) }
            },
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF9F9F9),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(label, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedDateTime?.format(formatter) ?: "Set date & time",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selectedDateTime == null) Color.LightGray else Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(orangePrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.EditCalendar, contentDescription = null, tint = orangePrimary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun showDateTimePicker(context: android.content.Context, onDateTimeSelected: (LocalDateTime) -> Unit) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    
                    val selectedDateTime = LocalDateTime.of(
                        year, month + 1, dayOfMonth, hourOfDay, minute
                    )
                    onDateTimeSelected(selectedDateTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ).show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
