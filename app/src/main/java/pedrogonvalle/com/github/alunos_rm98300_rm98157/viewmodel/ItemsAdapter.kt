package pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import pedrogonvalle.com.github.alunos_rm98300_rm98157.R
import pedrogonvalle.com.github.alunos_rm98300_rm98157.model.ItemModel

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Views do novo layout
        private val textViewLocal = view.findViewById<TextView>(R.id.textViewLocal)
        private val chipTipo = view.findViewById<Chip>(R.id.chipTipo)
        private val chipGrau = view.findViewById<Chip>(R.id.chipGrau)
        private val textViewData = view.findViewById<TextView>(R.id.textViewData)
        private val textViewNumero = view.findViewById<TextView>(R.id.textViewNumero)
        private val deleteButton = view.findViewById<MaterialButton>(R.id.deleteButton)
        private val severityIndicator = view.findViewById<View>(R.id.severityIndicator)

        fun bind(item: ItemModel) {
            // Define o local
            textViewLocal.text = item.local

            // Define o tipo no chip
            chipTipo.text = item.tipo

            // Define o grau no chip
            chipGrau.text = item.grau

            // Define a data
            textViewData.text = item.data

            // Define o número com formatação
            textViewNumero.text = "${item.numero} pessoas"

            // Define a cor do indicador de severidade baseado no grau
            setSeverityIndicatorColor(item.grau)

            // Configura o botão de delete
            deleteButton.setOnClickListener {
                onItemRemoved(item)
            }

            // Adiciona animação de clique no card
            itemView.setOnClickListener {
                // Aqui você pode adicionar ação de clique no card se necessário
                // Por exemplo: expandir detalhes, abrir tela de edição, etc.
            }
        }

        private fun setSeverityIndicatorColor(grau: String) {
            val context = itemView.context
            val colorRes = when (grau.lowercase()) {
                "baixo", "leve" -> android.R.color.holo_green_dark
                "medio", "médio", "moderado" -> android.R.color.holo_orange_dark
                "alto", "grave", "critico", "crítico" -> android.R.color.holo_red_dark
                else -> android.R.color.darker_gray
            }

            severityIndicator.setBackgroundColor(
                context.getColor(colorRes)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    // Método para adicionar um novo item
    fun addItem(item: ItemModel) {
        val newItems = items.toMutableList()
        newItems.add(0, item) // Adiciona no início da lista
        updateItems(newItems)
    }

    // Método para remover um item
    fun removeItem(item: ItemModel) {
        val newItems = items.toMutableList()
        newItems.remove(item)
        updateItems(newItems)
    }

    // Método para obter todos os itens
    fun getAllItems(): List<ItemModel> = items.toList()
}