package pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pedrogonvalle.com.github.alunos_rm98300_rm98157.data.ItemDao
import pedrogonvalle.com.github.alunos_rm98300_rm98157.data.ItemDatabase
import pedrogonvalle.com.github.alunos_rm98300_rm98157.model.ItemModel

class ItemsViewModel(application: Application) : AndroidViewModel(application) {


    private val itemDao: ItemDao


    val itemsLiveData: LiveData<List<ItemModel>>

    init {

        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        ).build()


        itemDao = database.itemDao()


        itemsLiveData = itemDao.getAll()
    }


    fun addItem(local: String, tipo: String, grau: String, data: String, numero:String) {

        viewModelScope.launch(Dispatchers.IO) {

            val newItem = ItemModel(local = local, tipo = tipo, grau = grau, data = data, numero = numero)

            itemDao.insert(newItem)
        }
    }


    fun removeItem(item: ItemModel) {

        viewModelScope.launch(Dispatchers.IO) {

            itemDao.delete(item)
        }
    }
}