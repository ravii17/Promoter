package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.models.JobType
import com.example.eventhostmodule.ui.theme.JobTypeMultiDay
import com.example.eventhostmodule.ui.theme.JobTypeMultiDayBg
import com.example.eventhostmodule.ui.theme.JobTypeOneOff
import com.example.eventhostmodule.ui.theme.JobTypeOneOffBg
import com.example.eventhostmodule.ui.theme.JobTypeUrgent
import com.example.eventhostmodule.ui.theme.JobTypeUrgentBg

@Composable
fun JobTypeBadge(
    type: JobType,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, label) = when (type) {
        JobType.ONE_OFF_EVENT -> Triple(
            JobTypeOneOffBg,
            JobTypeOneOff,
            "ONE-OFF EVENT"
        )
        JobType.URGENT_FILL -> Triple(
            JobTypeUrgentBg,
            JobTypeUrgent,
            "URGENT FILL"
        )
        JobType.MULTI_DAY -> Triple(
            JobTypeMultiDayBg,
            JobTypeMultiDay,
            "MULTI-DAY"
        )
    }

    Text(
        text = label,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
