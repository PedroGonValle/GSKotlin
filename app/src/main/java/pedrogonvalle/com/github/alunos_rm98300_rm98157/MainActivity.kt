package pedrogonvalle.com.github.alunos_rm98300_rm98157

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsAdapter
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsViewModel
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: ItemsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Lista de Compras"


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }

        recyclerView.adapter = itemsAdapter


        val button = findViewById<Button>(R.id.button)
        val editLocal = findViewById<EditText>(R.id.editLocal)
        val editTipo = findViewById<EditText>(R.id.editTipo)
        val editGrau = findViewById<EditText>(R.id.editGrau)
        val editData = findViewById<EditText>(R.id.editData)
        val editNumero = findViewById<EditText>(R.id.editNumero)


        button.setOnClickListener {

            if (editLocal.text.isEmpty() ) {
                editLocal.error = "Preencha o nome do local"
                return@setOnClickListener
            }

            if (editTipo.text.isEmpty() ) {
                editTipo.error = "Preencha o tipo do evento"
                return@setOnClickListener
            }

            if (editGrau.text.isEmpty() ) {
                editGrau.error = "Preencha o grau do impacto"
                return@setOnClickListener
            }

            if (editData.text.isEmpty() ) {
                editData.error = "Preencha a data do evento"
                return@setOnClickListener
            }

            if (editNumero.text.isEmpty() ) {
                editNumero.error = "Preencha o numero de pessoas afetadas"
                return@setOnClickListener
            }

            if (editNumero.text.equals("0")) {
                editNumero.error = "O número de pessoas afetadas não pode ser 0"
                return@setOnClickListener
            }


            viewModel.addItem(editLocal.text.toString(), editTipo.text.toString(), editGrau.text.toString(), editData.text.toString(), editNumero.text.toString())
            editLocal.text.clear()
            editTipo.text.clear()
            editGrau.text.clear()
            editData.text.clear()
            editNumero.text.clear()
        }


        val viewModelFactory = ItemsViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)


        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}