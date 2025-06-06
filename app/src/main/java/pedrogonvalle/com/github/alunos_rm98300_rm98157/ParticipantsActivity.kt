package pedrogonvalle.com.github.alunos_rm98300_rm98157

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ParticipantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)

        // 1. Encontra a Toolbar no layout pelo ID correto.
        val toolbar: Toolbar = findViewById(R.id.toolbar_participants)
        // 2. Define a Toolbar como a ActionBar da atividade.
        setSupportActionBar(toolbar)

        // 3. Adiciona o botão de "voltar" (seta para a esquerda) na Toolbar.
        // Isso permite que o usuário navegue para a tela anterior.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // O título da Toolbar é definido no arquivo de layout XML
        // usando o atributo `app:title="@string/participants_activity_title"`.
    }

    /**
     * Este método é chamado quando um item no menu de opções (incluindo o botão de voltar
     * da Toolbar) é selecionado.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Verifica se o item clicado é o botão de voltar (o ID padrão é android.R.id.home).
        if (item.itemId == android.R.id.home) {
            // Fecha a atividade atual e retorna para a tela anterior na pilha de navegação.
            finish()
            return true // Indica que o evento de clique foi tratado.
        }
        // Se não for o botão de voltar, passa o evento para a implementação da superclasse.
        return super.onOptionsItemSelected(item)
    }
}
