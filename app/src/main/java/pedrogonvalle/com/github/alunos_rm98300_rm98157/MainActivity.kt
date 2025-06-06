package pedrogonvalle.com.github.alunos_rm98300_rm98157

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        // Configura a Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eventos Climáticos Extremos"

        // Configura o ViewModel
        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        // Configura o RecyclerView e o Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        // Encontra os campos de texto e o botão
        val button = findViewById<Button>(R.id.button)
        val editLocal = findViewById<EditText>(R.id.editLocal)
        val editTipo = findViewById<EditText>(R.id.editTipo)
        val editGrau = findViewById<EditText>(R.id.editGrau)
        val editData = findViewById<EditText>(R.id.editData)
        val editNumero = findViewById<EditText>(R.id.editNumero)

        // Ação do botão de inserir
        button.setOnClickListener {
            val local = editLocal.text.toString()
            val tipo = editTipo.text.toString()
            val grau = editGrau.text.toString()
            val data = editData.text.toString()
            val numero = editNumero.text.toString()

            // Validação dos campos
            if (local.isBlank() || tipo.isBlank() || grau.isBlank() || data.isBlank() || numero.isBlank()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (numero == "0") {
                editNumero.error = "O número de afetados não pode ser 0"
                return@setOnClickListener
            }

            // Adiciona o item e limpa os campos
            viewModel.addItem(local, tipo, grau, data, numero)
            editLocal.text.clear()
            editTipo.text.clear()
            editGrau.text.clear()
            editData.text.clear()
            editNumero.text.clear()
        }

        // Observa mudanças na lista de itens e atualiza o adapter
        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}