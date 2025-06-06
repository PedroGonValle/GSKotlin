//Felipe Della Paschoa de Medeiros RM98157
//Pedro Gonçalves Valle - RM98300

package pedrogonvalle.com.github.alunos_rm98300_rm98157

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pedrogonvalle.com.github.alunos_rm98300_rm98157.data.ItemDatabase
import pedrogonvalle.com.github.alunos_rm98300_rm98157.model.ItemModel
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsAdapter
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsViewModel
import pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel.ItemsViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemsViewModel
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var sharedPreferences: SharedPreferences

    // UI Elements
    private lateinit var editLocal: EditText
    private lateinit var editTipo: EditText
    private lateinit var editGrau: EditText
    private lateinit var editData: EditText
    private lateinit var editNumero: EditText
    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val PREFS_NAME = "ThemePrefs"
        const val KEY_THEME = "SelectedTheme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // A chamada para installSplashScreen() DEVE ser a primeira coisa a acontecer.
        // Isso garante que o tema correto (postSplashScreenTheme) seja aplicado
        // antes que a activity comece a desenhar sua UI, resolvendo o erro de tema.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Aplica o tema (claro/escuro) salvo pelo usuário
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        applySelectedTheme()

        setContentView(R.layout.activity_main)

        // --- Início da configuração da UI ---
        setupToolbar()
        setupViewModel()
        bindUIElements()
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eventos Climáticos Extremos"
    }

    private fun setupViewModel() {
        try {
            // Corrigido para usar a sua implementação original da ViewModelFactory,
            // que espera o contexto da aplicação.
            val viewModelFactory = ItemsViewModelFactory(application)
            viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao inicializar ViewModel: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindUIElements() {
        recyclerView = findViewById(R.id.recyclerView)
        button = findViewById(R.id.button)
        editLocal = findViewById(R.id.editLocal)
        editTipo = findViewById(R.id.editTipo)
        editGrau = findViewById(R.id.editGrau)
        editData = findViewById(R.id.editData)
        editNumero = findViewById(R.id.editNumero)
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemsAdapter { item ->
            if (::viewModel.isInitialized) {
                // Supondo que ItemsViewModel tem um método para remover item.
                // Se o nome for diferente (ex: delete), ajuste aqui.
                viewModel.removeItem(item)
            }
        }
        recyclerView.adapter = itemsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupListeners() {
        button.setOnClickListener {
            addEvent()
        }
    }

    private fun observeViewModel() {
        if (::viewModel.isInitialized) {
            viewModel.itemsLiveData.observe(this) { items ->
                itemsAdapter.updateItems(items ?: emptyList())
            }
        }
    }

    private fun addEvent() {
        val local = editLocal.text.toString().trim()
        val tipo = editTipo.text.toString().trim()
        val grau = editGrau.text.toString().trim()
        val data = editData.text.toString().trim()
        val numero = editNumero.text.toString().trim()

        if (local.isBlank() || tipo.isBlank() || grau.isBlank() || data.isBlank() || numero.isBlank()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }
        val numeroInt = numero.toIntOrNull()
        if (numeroInt == null || numeroInt <= 0) {
            editNumero.error = "O número de afetados deve ser maior que 0"
            return
        } else {
            editNumero.error = null
        }

        if (::viewModel.isInitialized) {
            // Supondo que seu ViewModel tem um método addItem com estes parâmetros
            viewModel.addItem(local, tipo, grau, data, numero)
            // Limpa os campos de entrada após adicionar o item
            clearInputFields()
        } else {
            Toast.makeText(this, "ViewModel não inicializado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInputFields() {
        editLocal.text.clear()
        editTipo.text.clear()
        editGrau.text.clear()
        editData.text.clear()
        editNumero.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_participants -> {
                startActivity(Intent(this, ParticipantsActivity::class.java))
                true
            }
            R.id.action_theme_light -> {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.action_theme_dark -> {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.action_theme_system -> {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applySelectedTheme() {
        val selectedThemeMode = sharedPreferences.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(selectedThemeMode)
    }

    private fun setThemeMode(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        with(sharedPreferences.edit()) {
            putInt(KEY_THEME, themeMode)
            apply()
        }
        // Recria a atividade para aplicar a mudança de tema
        val intent = intent
        finish()
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
