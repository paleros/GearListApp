package com.example.gearlistapp.presentation.screen

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.R
import com.example.gearlistapp.navigation.NavGraph
import com.example.gearlistapp.presentation.dialog.AboutDialog
import com.example.gearlistapp.presentation.dialog.category.CategoryListDialog
import com.example.gearlistapp.presentation.dialog.exportimport.ExportDialog
import com.example.gearlistapp.presentation.dialog.exportimport.ImportDialog
import com.example.gearlistapp.presentation.dialog.location.LocationListDialog
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.BottomNavigationBar
import com.example.gearlistapp.ui.common.MyTopAppBar
import kotlinx.coroutines.launch

/**
 * A navigacios savokat reprezentáló komponens.
 * @param exportLauncher Az ActivityResultLauncher, amely kezeli az exportalast.
 */
@Composable
fun MainScreen(
    exportLauncher: ActivityResultLauncher<Intent>,
) {

    val gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory)
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory)
    val locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory)
    val templateViewModel: TemplateViewModel = viewModel(factory = TemplateViewModel.Factory)

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            /** Bal oldali navigacios menu */
            DrawerContent(
                gearViewModel = gearViewModel,
                categoryViewModel = categoryViewModel,
                locationViewModel = locationViewModel,
                exportLauncher = exportLauncher,
            ) {
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = { MyTopAppBar(navController, scope, drawerState) },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavGraph(navController = navController,
                modifier = Modifier.padding(innerPadding),
                gearViewModel = gearViewModel,
                categoryViewModel = categoryViewModel,
                locationViewModel = locationViewModel,
                templateViewModel = templateViewModel,
            )
        }
    }
}

/**
 * A bal oldali navigacios menu tartalma.
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param exportLauncher Az ActivityResultLauncher, amely kezeli az exportalast.
 * @param onClose a menu bezarasahoz
 */
@Composable
fun DrawerContent(
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    exportLauncher: ActivityResultLauncher<Intent>,
    onClose: () -> Unit
) {
    gearViewModel.loadGears()
    categoryViewModel.loadCategories()
    locationViewModel.loadLocations()

    var showCategoryDialog by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var showImportDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.4f)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.main_icon),
            contentDescription = "Main Icon",
            modifier = Modifier.fillMaxWidth().padding(35.dp),
        )
        Text(
            text = stringResource(id = R.string.menu),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = Color.Gray
        )

        DrawerMenuItem(R.string.gear_categories, onClick = { showCategoryDialog = true; onClose() })
        DrawerMenuItem(R.string.locations, onClick = { showLocationDialog = true; onClose() })
        DrawerMenuItem(R.string.export_list, onClick = { showExportDialog = true; onClose() })
        DrawerMenuItem(R.string.import_list, onClick = { showImportDialog = true; onClose() })
        DrawerMenuItem(R.string.about, onClick = { showAboutDialog = true; onClose() })
    }

    if (showCategoryDialog) {
        CategoryListDialog(
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showCategoryDialog = false }
        )
    }
    if (showLocationDialog) {
        LocationListDialog(
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showLocationDialog = false }
        )
    }
    if (showExportDialog) {
        ExportDialog(
            onDismiss = { showExportDialog = false },
            exportLauncher = exportLauncher
        )
    }
    if (showImportDialog) {
        ImportDialog(
            onDismiss = { showImportDialog = false },
        )
    }
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}

/**
 * A bal oldali navigacios menu egy eleme.
 * @param textRes a szoveg forras
 * @param onClick a gomb megnyomasahoz
 */
@Composable
fun DrawerMenuItem(@StringRes textRes: Int, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    ) {
        Text(
            text = stringResource(id = textRes),
            modifier = Modifier.padding(4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}