package com.example.notesapp.ui.MainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.ui.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    onSave: (String, String, String) -> Unit   // thêm category
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val categories by viewModel.categories.collectAsState()
    var selectedCategory by remember { mutableStateOf("Chưa phân loại") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thêm ghi chú") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            onSave(title, content, selectedCategory)  // GỬI CATEGORY VỀ
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Lưu")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // --- Tiêu đề ---
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(8.dp))

            // --- Nội dung ---
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Nội dung") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            // --- Dropdown chọn phân loại ---
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Phân loại") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                selectedCategory = cat
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}