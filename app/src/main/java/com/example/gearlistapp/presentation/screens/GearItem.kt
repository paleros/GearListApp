package com.example.gearlistapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.gearlistapp.GearApplication
import com.example.gearlistapp.R
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.database.AppDatabase
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.LocationEntity
import com.example.gearlistapp.data.repository.GearRepositoryImpl
import com.example.gearlistapp.domain.usecases.gear.GearUseCases
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Egy felszereles elem megjelenitese
 * @param gear a felszereles entitas
 * @param viewModel a felszereles viewmodelje
 */
@Composable
fun GearItem(gear: GearEntity, viewModel: GearViewModel) {

    var category by remember { mutableStateOf<CategoryEntity?>(null) }
    var location by remember { mutableStateOf<LocationEntity?>(null) }
    var locationName by remember { mutableStateOf("") }

    /** A Flow osszegyujtese */
    LaunchedEffect(gear.categoryId) {
        category = viewModel.getGearCategoryById(gear.categoryId)
    }
    LaunchedEffect(gear.locationId) {
        location = viewModel.getGearLocationById(gear.locationId)
        locationName = location?.name ?: ""
    }

    val categoryColor = category?.color ?: Color.Gray
    val categoryIcon = category?.iconRes ?: R.drawable.ic_launcher_foreground


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = categoryColor as Color)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = categoryIcon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.run { size(40.dp) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = gear.name, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
                Text(text = locationName,
                    fontSize = 14.sp,
                    color = Color.White)
            }
        }
    }
}

/**
 * A felszereles elemnek a tesztelese
 */
@Preview(showBackground = true)
@Composable
fun PreviewGearItem() {
    val viewModel = GearViewModel(GearUseCases(GearRepositoryImpl(TestGearDao())))
    val gear = GearEntity(
        id = 1,
        name = "Tura hatizsak",
        categoryId = 1,
        locationId = 1,
        description = "Valami random leiras a megjelenes tesztelesehez"
    )

    GearItem(gear = gear, viewModel = viewModel)
}

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