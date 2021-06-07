package ru.gorinih.moneymana.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.data.db.dao.CategoriesDAO
import ru.gorinih.moneymana.data.db.dao.ChecksDAO
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.data.model.CheckEntity

@Database(
    entities = [CategoryEntity::class,
        CheckEntity::class], version = 1, exportSchema = false
)
abstract class MDatabase : RoomDatabase() {
    abstract val categoriesDao: CategoriesDAO
    abstract val checksDao: ChecksDAO

    companion object {
        @Volatile
        private var hInstance: MDatabase? = null

        fun getInstance(context: Context): MDatabase =
            hInstance ?: synchronized(this) {
                hInstance ?: buildManaDatabase(context).also { hInstance = it }
            }

        private fun buildManaDatabase(context: Context): MDatabase {
            return Room.databaseBuilder(
                context,
                MDatabase::class.java,
                "mmanadatabase"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            insertDefaultData(hInstance)
                        }
                    }
                })
                .build()

        }

        private suspend fun insertDefaultData(creatingDatabase: MDatabase?) {
            creatingDatabase?.categoriesDao?.insertCategoriesList(categoriesMain)
        }

        private val categoriesMain = listOf(
            CategoryEntity(1, R.drawable.beaker_check, "Прочее", true, 1000),
            CategoryEntity(2, R.drawable.food, "Продукты", true, 1000),
            CategoryEntity(3, R.drawable.drama_masks, "Развлечение", true, 1000),
            CategoryEntity(4, R.drawable.tshirt_crew_outline, "Одежда", true, 1000),
            CategoryEntity(5, R.drawable.gas_station, "Бензин", true, 1000),
            CategoryEntity(6, R.drawable.ambulance, "Здоровье", true, 1000),
            CategoryEntity(7, R.drawable.hiking, "Путешествия", true, 1000),
            CategoryEntity(8, R.drawable.weight_lifter, "Спорт", true, 1000)
        )

    }
}