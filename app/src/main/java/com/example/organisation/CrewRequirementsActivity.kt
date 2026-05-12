package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CrewRequirementsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CrewRequirementsScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CrewRequirementsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val orangePrimary = Color(0xFFFF8A00)
    val textDark = Color(0xFF1A1A2E)
    val textGray = Color(0xFF7D7D7D)

    var selectedCrewTypes by rememberSaveable { mutableStateOf(setOf<String>()) }
    var totalQuantity by rememberSaveable { mutableIntStateOf(5) }
    var maleCount by rememberSaveable { mutableIntStateOf(2) }
    var femaleCount by rememberSaveable { mutableIntStateOf(3) }

    val crewTypes = listOf("Event Staff", "DJ", "Promoter", "Volunteer", "Security")

    val isDemographicsValid = maleCount + femaleCount == totalQuantity
    val isDataValid = selectedCrewTypes.isNotEmpty() && totalQuantity > 0 && isDemographicsValid

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Organizer", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White,
        bottomBar = {
            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = {
                        val intent = Intent(context, SetPaymentActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangePrimary,
                        disabledContainerColor = orangePrimary.copy(alpha = 0.5f)
                    ),
                    enabled = isDataValid
                ) {
                    Text("Next", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Crew Requirements",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textDark
            )
            Text(
                text = "Define the staffing needs for your event.",
                fontSize = 16.sp,
                color = textGray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            
            // Section 1: Select Crew Type
            SectionHeader("SELECT CREW TYPE")
            Spacer(modifier = Modifier.height(16.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                crewTypes.forEach { type ->
                    val isSelected = selectedCrewTypes.contains(type)
                    CrewTypeChip(
                        label = type,
                        isSelected = isSelected,
                        onSelectedChange = {
                            selectedCrewTypes = if (isSelected) {
                                selectedCrewTypes - type
                            } else {
                                selectedCrewTypes + type
                            }
                        },
                        orangePrimary = orangePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Section 2: Quantity
            SectionHeader("QUANTITY")
            Spacer(modifier = Modifier.height(12.dp))
            QuantityCard(
                title = "Total crew required",
                subtitle = "Total headcount for selected roles",
                count = totalQuantity,
                onCountChange = { 
                    totalQuantity = it.coerceAtLeast(1)
                },
                orangePrimary = orangePrimary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Section 3: Demographics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader("DEMOGRAPHICS")
                Surface(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "OPTIONAL",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFB74D)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    DemographicRow(
                        label = "Male Crew",
                        count = maleCount,
                        onCountChange = { maleCount = it.coerceAtLeast(0) },
                        orangePrimary = orangePrimary
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF5F5F5))
                    DemographicRow(
                        label = "Female Crew",
                        count = femaleCount,
                        onCountChange = { femaleCount = it.coerceAtLeast(0) },
                        orangePrimary = orangePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "You can customize gender requirement based on event needs. Total must match required count.",
                fontSize = 13.sp,
                color = textGray,
                lineHeight = 18.sp
            )

            AnimatedVisibility(visible = !isDemographicsValid && (maleCount > 0 || femaleCount > 0)) {
                Text(
                    text = "Male + Female count (${maleCount + femaleCount}) must equal total quantity ($totalQuantity)",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF9E9E9E),
        letterSpacing = 0.5.sp
    )
}

@Composable
fun CrewTypeChip(
    label: String,
    isSelected: Boolean,
    onSelectedChange: () -> Unit,
    orangePrimary: Color
) {
    val backgroundColor by animateColorAsState(if (isSelected) orangePrimary else Color.White, label = "bgColor")
    val textColor by animateColorAsState(if (isSelected) Color.White else Color(0xFF757575), label = "textColor")
    val borderColor = if (isSelected) orangePrimary else Color(0xFFE0E0E0)

    Surface(
        modifier = Modifier
            .clickable { onSelectedChange() },
        shape = RoundedCornerShape(50.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = textColor, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            if (isSelected) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun QuantityCard(
    title: String,
    subtitle: String,
    count: Int,
    onCountChange: (Int) -> Unit,
    orangePrimary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A1A2E))
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
            
            Counter(count = count, onCountChange = onCountChange, orangePrimary = orangePrimary)
        }
    }
}

@Composable
fun DemographicRow(
    label: String,
    count: Int,
    onCountChange: (Int) -> Unit,
    orangePrimary: Color
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color(0xFF1A1A2E))
        Counter(count = count, onCountChange = onCountChange, orangePrimary = orangePrimary, useLightMode = true)
    }
}

@Composable
fun Counter(
    count: Int,
    onCountChange: (Int) -> Unit,
    orangePrimary: Color,
    useLightMode: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (useLightMode) Color(0xFFF5F5F5) else Color.White)
                .border(1.dp, if (useLightMode) Color.Transparent else Color(0xFFEEEEEE), CircleShape)
                .clickable { onCountChange(count - 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
        }
        
        Text(
            text = count.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (useLightMode) Color(0xFFF5F5F5) else Color.White)
                .border(1.dp, if (useLightMode) Color.Transparent else Color(0xFFEEEEEE), CircleShape)
                .clickable { onCountChange(count + 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
        }
    }
}
