package com.agroconsult.app.ui.screens.consultations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationsScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    var isBookingSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الاستشارات") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isBookingSheet = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "حجز استشارة")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("الاستشارات النشطة") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("الخبراء") }
                )
            }

            // Tab Content
            when (selectedTab) {
                0 -> ActiveConsultationsTab()
                1 -> ExpertsTab()
            }
        }
    }

    if (isBookingSheet) {
        BookConsultationSheet(onDismiss = { isBookingSheet = false })
    }
}

@Composable
fun ActiveConsultationsTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) { index ->
            ConsultationCard(
                expertName = "الخبير $index",
                topic = "الزراعة العضوية",
                status = "نشط",
                price = "100 ر.س"
            )
        }
    }
}

@Composable
fun ExpertsTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(5) { index ->
            ExpertCard(
                name = "الخبير $index",
                specialty = "الزراعة العضوية",
                rating = 4.5,
                price = "${100 + index * 20} ر.س/ساعة"
            )
        }
    }
}

@Composable
fun ConsultationCard(expertName: String, topic: String, status: String, price: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(expertName, fontWeight = FontWeight.Bold)
                    Text(topic, fontSize = 12.sp)
                }
                Text(
                    status,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(price, color = MaterialTheme.colorScheme.primary)
                Button(onClick = { }, modifier = Modifier.height(32.dp)) {
                    Text("الاتصال")
                }
            }
        }
    }
}

@Composable
fun ExpertCard(name: String, specialty: String, rating: Double, price: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(specialty, fontSize = 12.sp)
                Text("⭐ $rating", color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
                Text(price, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(36.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text("حجز", fontSize = 12.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookConsultationSheet(onDismiss: () -> Unit) {
    var consultationType by remember { mutableStateOf("نص") }
    var consultantName by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("") }

    ModalBottomSheet(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                "حجز استشارة",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text("نوع الاستشارة", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("نص", "صوت", "فيديو").forEach { type ->
                    FilterChip(
                        selected = consultationType == type,
                        onClick = { consultationType = type },
                        label = { Text(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            OutlinedTextField(
                value = consultantName,
                onValueChange = { consultantName = it },
                label = { Text("اسم الخبير") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("الموضوع") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = dateTime,
                onValueChange = { dateTime = it },
                label = { Text("التاريخ والوقت") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("حجز الاستشارة")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
