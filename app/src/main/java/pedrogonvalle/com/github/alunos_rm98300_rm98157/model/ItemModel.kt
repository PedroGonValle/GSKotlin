package pedrogonvalle.com.github.alunos_rm98300_rm98157.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ItemModel(


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    val local: String,
    val tipo:String,
    val grau:String,
    val data:String,
    val numero:String
)