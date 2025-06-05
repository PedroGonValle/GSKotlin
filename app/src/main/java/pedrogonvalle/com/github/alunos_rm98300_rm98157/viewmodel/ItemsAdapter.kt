package pedrogonvalle.com.github.alunos_rm98300_rm98157.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pedrogonvalle.com.github.alunos_rm98300_rm98157.R
import pedrogonvalle.com.github.alunos_rm98300_rm98157.model.ItemModel

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {


    private var items = listOf<ItemModel>()


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val textViewLocal = view.findViewById<TextView>(R.id.textViewLocal)
        val textViewTipo = view.findViewById<TextView>(R.id.textViewTipo)
        val textViewGrau = view.findViewById<TextView>(R.id.textViewGrau)
        val textViewData = view.findViewById<TextView>(R.id.textViewData)
        val textViewNumero = view.findViewById<TextView>(R.id.textViewNumero)
        val button = view.findViewById<ImageButton>(R.id.imageButton)


        fun bind(item: ItemModel) {

            textViewLocal.text = item.local
            textViewTipo.text = item.tipo
            textViewGrau.text = item.grau
            textViewData.text = item.data
            textViewNumero.text = item.numero

            button.setOnClickListener {
                onItemRemoved(item)
            }
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
}