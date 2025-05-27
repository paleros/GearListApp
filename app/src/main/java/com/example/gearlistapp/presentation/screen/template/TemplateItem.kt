package com.example.gearlistapp.presentation.screen.template

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gearlistapp.R
import com.example.gearlistapp.data.entities.TemplateEntity
import com.example.gearlistapp.navigation.Screen
import com.example.gearlistapp.presentation.dialog.actualtemplate.ActualTemplateCreateDialog
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel

/**
 * A sablonok megjelenitese
 * @param template a sablon entitas
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param templateViewModel a sablonokhoz tartozo ViewModel
 * @param onClick a kattintas esemeny
 */
@Composable
fun TemplateItem(
    template: TemplateEntity,
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    templateViewModel: TemplateViewModel,
    navController: NavHostController,
    onClick: () -> Unit
) {
    var title = template.title
    var duration = template.duration
    var itemList = template.itemList
    val backgroundColor = Color(template.backgroundColor)
    val darkerBackgroundColor = backgroundColor.copy(
        red = backgroundColor.red * 0.8f,
        green = backgroundColor.green * 0.8f,
        blue = backgroundColor.blue * 0.8f
    )
    val context = LocalContext.current
    var showCreateActualDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.5f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(darkerBackgroundColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$duration " +
                                stringResource(R.string.day),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Row {
                Box(modifier = Modifier.heightIn(min = 48.dp, max = 96.dp)) {
                    val visibleItems = itemList.take((itemList.size / 3).coerceAtLeast(2))

                    Column {
                        visibleItems.forEach { item ->
                            var itemName by remember { mutableStateOf("") }
                            gearViewModel.getById(id = item) { itemName = it?.name.toString() }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            Color.DarkGray,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = itemName,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, backgroundColor),
                                    startY = 50f
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        showCreateActualDialog = true
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Create actual template",
                    )
                }
            }
        }
    }

    if (showCreateActualDialog) {
        ActualTemplateCreateDialog(
            templateTitle = title,
            gearViewModel = gearViewModel,
            templateViewModel = templateViewModel,
            onDismiss = { showCreateActualDialog = false },
            onSave = { date ->
                templateViewModel.add(
                    title = title,
                    description = template.description,
                    duration = template.duration,
                    backgroundColor = backgroundColor.toArgb(),
                    itemList = template.itemList,
                    date = date,
                    concrete = true,
                )
                showCreateActualDialog = false
                navController.navigate(Screen.HomeScreen.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
                Toast.makeText(context, R.string.save_successful , Toast.LENGTH_LONG).show()
            }
        )
    }
}