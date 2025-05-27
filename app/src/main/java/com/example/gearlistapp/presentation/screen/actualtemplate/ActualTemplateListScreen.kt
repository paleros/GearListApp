package com.example.gearlistapp.presentation.screen.actualtemplate

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.ui.model.toUiText
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateListState
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.model.TemplateUi
import com.example.gearlistapp.ui.model.asTemplateEntity
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import com.example.gearlistapp.presentation.dialog.actualtemplate.ActualTemplateDetailDialog
import com.example.gearlistapp.presentation.dialog.template.TemplateFilterDialog
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Az aktualis sablonok listajat megjelenito kepernyo
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param templateViewModel a sablonokhoz tartozo ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun ActualTemplateListScreen(
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    templateViewModel: TemplateViewModel,
) {

    val templateList = templateViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var showIndicator by remember { mutableStateOf(false) }
    var selectedTemplate by remember { mutableStateOf<TemplateUi?>(null) }

    var searchText by remember { mutableStateOf("") }
    var selectedMin by remember { mutableIntStateOf(1) }
    var selectedMax by remember { mutableIntStateOf(30) }
    var sortOrder by remember { mutableStateOf(SortOrder.DateAsc) }
    var showTemplateFilterDialog by remember { mutableStateOf(false) }

    var flipped by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val refreshKeys = remember { mutableStateMapOf<Int, Int>() }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                gearViewModel.loadGears()
                categoryViewModel.loadCategories()
                locationViewModel.loadLocations()
                templateViewModel.loadTemplates()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /** Szures, rendezes es kereses */
    val filteredAndSortedTemplateList = templateList.let {
        when (it) {
            is TemplateListState.Result -> {
                it.templateList
                    .filter { template ->
                        template.title.contains(searchText, ignoreCase = true)
                                &&
                                ((template.duration >= selectedMin && template.duration <= selectedMax)
                                        || (template.duration >= selectedMin && 30 == selectedMax))

                    }
                    .sortedWith { template1, template2 ->
                        when (sortOrder) {
                            SortOrder.DateAsc -> template1.date.compareTo(template2.date)
                            SortOrder.DateDesc -> template2.date.compareTo(template1.date)
                        }
                    }
            }
            else -> emptyList()
        }
    }

    showIndicator = selectedMin != 1 || selectedMax != 30

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(0.dp),
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                /** Kereses */
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { R.string.search },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                /** Filter ikon */
                IconButton(onClick = { showTemplateFilterDialog = true }) {
                    Box {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        if (showIndicator) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 2.dp, y = (-2).dp)
                                    .background(
                                        MaterialTheme.colorScheme.tertiary,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
                /** Renderzes ikon */
                IconButton(onClick = {
                    flipped = !flipped
                    sortOrder = if
                            (sortOrder == SortOrder.DateAsc) SortOrder.DateDesc
                    else
                        SortOrder.DateAsc
                }) {
                    val rotation by animateFloatAsState(
                        targetValue = if (flipped) 180f else 0f,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Icon(
                        Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort",
                        modifier = Modifier
                            .graphicsLayer { rotationZ = rotation })
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
                    .background(
                        color = if (templateList is TemplateListState.Loading || templateList is TemplateListState.Error) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (templateList) {
                    is TemplateListState.Loading -> CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )

                    is TemplateListState.Error -> Text(
                        text = templateList.error.toUiText().asString(context)
                    )

                    is TemplateListState.Result -> {
                        if (filteredAndSortedTemplateList.isEmpty()) {
                            Text(text = stringResource(id = R.string.text_empty_template_list))
                        } else {
                            Column {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    items(
                                        filteredAndSortedTemplateList,
                                        key = { template -> template.id }
                                    ) { template ->
                                        if (template.concrete) {
                                            ActualTemplateItem(
                                                template = template.asTemplateEntity(),
                                                onClick = { selectedTemplate = template },
                                                refreshKey = refreshKeys[template.id] ?: 0,
                                                categoryViewModel = categoryViewModel,
                                                locationViewModel = locationViewModel,
                                                gearViewModel = gearViewModel,
                                                templateViewModel = templateViewModel
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showTemplateFilterDialog) {
        TemplateFilterDialog(
            onDismiss = { showTemplateFilterDialog = false },
            onRangeSelected = { min, max ->
                selectedMin = min
                selectedMax = max
            },
            previousMax = selectedMax,
            previousMin = selectedMin,
        )
    }

    selectedTemplate?.let { template ->
        ActualTemplateDetailDialog(
            templateId = template.id,
            onDismiss = { selectedTemplate = null
                refreshKeys[template.id] = (refreshKeys[template.id] ?: 0) + 1
                        },
            onDelete = { id ->
                var gears : List<Int> = emptyList()
                templateViewModel.getById(id){template ->
                    gears = template?.itemList ?: emptyList()
                }
                for (gear in gears){
                    gearViewModel.delete(gear)
                }
                templateViewModel.delete(id)
                selectedTemplate = null
            },
            gearViewModel = gearViewModel,
            templateViewModel = templateViewModel,
            categoryViewModel = categoryViewModel,
            locationViewModel = locationViewModel,
            onEdit = { id, title, description, duration, selectedMap, piecesMap, backgroundColor, date ->
                coroutineScope.launch {
                    val deferredGearIds = mutableListOf<Deferred<Int>>()

                    for ((gearId, isSelected) in selectedMap) {
                        if (isSelected) {
                            val gear = gearViewModel.getById(gearId)

                            val deferred = async {
                                suspendCancellableCoroutine<Int> { cont ->
                                    gearViewModel.add(
                                        gear?.name ?: "",
                                        gear?.description ?: "",
                                        gear?.categoryId ?: 0,
                                        gear?.locationId ?: 0,
                                        gear?.inPackage == true,
                                        piecesMap[gearId]?.toInt() ?: 1,
                                        gear?.id ?: -1
                                    ) { newId ->
                                        cont.resume(newId, null)
                                    }
                                }
                            }

                            deferredGearIds.add(deferred)
                        }
                    }

                    val gearList = deferredGearIds.awaitAll()

                    val newTemplate = TemplateUi(
                        id = id,
                        title = title,
                        description = description,
                        duration = duration,
                        itemList = gearList,
                        backgroundColor = backgroundColor,
                        date = date,
                        concrete = true
                    )

                    templateViewModel.update(newTemplate)
                    selectedTemplate = null
                }
            }
        )
    }
}

/**
 * A felszerelesek rendezesi iranya
 */
enum class SortOrder {
    DateAsc,
    DateDesc
}
