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
import ru.gorinih.moneymana.data.db.dao.BudgetDAO
import ru.gorinih.moneymana.data.db.dao.CategoriesDAO
import ru.gorinih.moneymana.data.db.dao.ChecksDAO
import ru.gorinih.moneymana.data.model.BudgetEntity
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.presentation.model.CheckScan

@Database(
    entities = [CategoryEntity::class,
        CheckEntity::class,
        BudgetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ManaDatabase : RoomDatabase() {
    abstract val checksDao: ChecksDAO
    abstract val categoriesDao: CategoriesDAO
//    abstract val budgetDAO: BudgetDAO

    companion object {
        @Volatile
        private var hInstance: ManaDatabase? = null

        fun getInstance(context: Context): ManaDatabase =
            hInstance ?: synchronized(this) {
                hInstance ?: create(context = context).also { hInstance = it }
            }

        private fun create(context: Context): ManaDatabase {
            return Room.databaseBuilder(
                context,
                ManaDatabase::class.java,
                "moneymanadb"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            insertDefaultData(hInstance?.categoriesDao, hInstance?.checksDao)
                        }
                    }

                    private suspend fun insertDefaultData(
                        daoCat: CategoriesDAO?,
                        daoCh: ChecksDAO?
                    ) {
                        daoCat?.insertCategoriesList(categoriesExample)
                        daoCh?.insertChecksList(checkExample)
                    }
                })
                .build()

        }

        private val categoriesExample = listOf(
            CategoryEntity(1, R.drawable.beaker_check, "Прочее", true),
            CategoryEntity(2, R.drawable.food, "Продукты", true),
            CategoryEntity(3, R.drawable.drama_masks, "Развлечение", true),
            CategoryEntity(4, R.drawable.tshirt_crew_outline, "Одежда", true),
            CategoryEntity(5, R.drawable.gas_station, "Бензин", true),
            CategoryEntity(6, R.drawable.ambulance, "Здоровье", true),
            CategoryEntity(7, R.drawable.hiking, "Путешествия", true),
            CategoryEntity(8, R.drawable.weight_lifter, "Спорт", true)
        )
        private val checkExample = listOf(
            CheckEntity(1, 2, 19001, 550.00, 123, 456, 789),
            CheckEntity(2, 5, 19001, 550.00, 123, 456, 789),
            CheckEntity(3, 5, 19003, 1000.00, 123, 456, 789),
        )


    }
}