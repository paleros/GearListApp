package com.example.gearlistapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gearlistapp.R
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import kotlinx.coroutines.flow.Flow

/**
 * A felszereles lista megjelenitese
 * @param viewModel a felszereles viewmodelje
 */
@Composable
fun GearListScreen(viewModel: GearViewModel) {
    val gear: List<GearEntity> = viewModel.gearList.collectAsState().value as List<GearEntity>

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        /** Szures gomb */
        Button(
            onClick = { /*TODO szűrés logikája később */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = R.string.filter.toString())
        }

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(gear) { gearItem ->
                GearItem(gear = gearItem, viewModel = viewModel)
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun PreviewGearListScreen() {
    val mockGearList = listOf(
        GearEntity(
            id = 1, name = "Hatizsak", categoryId = 1, locationId = 1, description = ""),
        GearEntity(
            id = 2, name = "Sator", categoryId = 2, locationId = 2, description = "")
    )

    val mockViewModel = object : GearViewModel(gearDao = TestGearDao(), locationDao = TestLocationDao(), categoryDao = TestCategoryDao()) {
        override val gearList: StateFlow<List<GearEntity>> = MutableStateFlow(mockGearList)

        override fun getCategoryById(id: Int): Flow<CategoryEntity> {
            return when (id) {
                1 -> CategoryEntity(id = 1, name = "Csomagolas", iconRes = R.drawable.ic_launcher_foreground, color = Color.Blue.value.toInt())
                2 -> CategoryEntity(id = 2, name = "Ejszaka", iconRes = R.drawable.ic_launcher_foreground, color = Color.Green.value.toInt())
                else -> CategoryEntity(0, "Ismeretlen", R.drawable.ic_launcher_foreground, Color.Gray.value.toInt())
            }
        }

        override fun getLocationNameById(id: Int): String {
            return when (id) {
                1 -> "Otthon"
                2 -> "Garazs"
                else -> "Ismeretlen"
            }
        }
    }

    GearListScreen(viewModel = mockViewModel)

    class TestGearDao : GearDao {
        override fun getAllGears(): Flow<List<GearEntity>> = flow {
            emit(
                listOf(
                    GearEntity(id = 1,
                        name = "Tura hatizsak",
                        categoryId = 1,
                        locationId = 1,
                        description = "Valami random leiras a megjelenes tesztelesehez"),
                )
            )
        }

        override suspend fun deleteGear(gear: GearEntity) {}

        override suspend fun deleteGearById(id: Int) {}

        override fun getGearById(id: Int): Flow<GearEntity> {
            return flow {
                emit(
                    GearEntity(id = 1,
                        name = "Tura hatizsak",
                        categoryId = 1,
                        locationId = 1,
                        description = "Valami random leiras a megjelenes tesztelesehez")
                )
            }
        }

        override suspend fun insertGear(gear: GearEntity) {}

        override suspend fun updateGear(gear: GearEntity) {}
    }

    class TestCategoryDao : CategoryDao {
        override fun getCategoryById(id: Int): Flow<CategoryEntity> = flow {
            emit(CategoryEntity(id = id,
                name = "Csomagolas",
                color = Color.Green.value.toInt(),
                iconRes = R.drawable.ic_launcher_foreground))
        }

        override fun getAllCategories(): Flow<List<CategoryEntity>> = flow {
            emit(
                listOf(
                    CategoryEntity(id = 1,
                        name = "Csomagolas",
                        color = Color.Green.value.toInt(),
                        iconRes = R.drawable.ic_launcher_foreground)
                )
            )
        }

        override suspend fun deleteCategory(category: CategoryEntity) {}

        override suspend fun deleteCategoryById(id: Int) {}

        override suspend fun insertCategory(category: CategoryEntity) {}

        override suspend fun updateCategory(category: CategoryEntity) {}
    }

    class TestLocationDao : LocationDao {
        override fun getLocationById(id: Int): Flow<LocationEntity> = flow {
            emit(LocationEntity(id = id,
                name = "Szekreny"))
        }

        override suspend fun deleteLocation(location: LocationEntity) {}

        override suspend fun deleteLocationById(id: Int) {}

        override fun getAllLocations(): Flow<List<LocationEntity>> {
            return flow {
                emit(
                    listOf(
                        LocationEntity(id = 1,
                            name = "Szekreny")
                    )
                )
            }
        }

        override suspend fun insertLocation(location: LocationEntity) {}

        override suspend fun updateLocation(location: LocationEntity) {}
    }
}*/