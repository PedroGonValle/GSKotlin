package pedrogonvalle.com.github.alunos_rm98300_rm98157.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pedrogonvalle.com.github.alunos_rm98300_rm98157.model.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}