package com.fruktoland.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.data.persistence.CatalogItem

class CatalogAdapter(
    var catalogList: List<CatalogItem>
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.catalog_recyclerview_item, parent, false)
        return CatalogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(catalogList[position])
    }

    override fun getItemCount(): Int = catalogList.size

    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView = itemView.findViewById(R.id.imageView)
        private var txtViewName: TextView = itemView.findViewById(R.id.txtViewName)
        private var txtViewPrice: TextView = itemView.findViewById(R.id.txtViewPrice)
        private var textViewUnit: TextView = itemView.findViewById(R.id.textViewUnit)
        private var txtViewDescription: TextView = itemView.findViewById(R.id.txtViewDescription)
        private var txtViewQtty: TextView = itemView.findViewById(R.id.txtViewQtty)
        private var btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        private var btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)

        fun bind(catalogItem: CatalogItem) {
            btnMinus.setOnClickListener { onButtonClick(false) }
            btnPlus.setOnClickListener { onButtonClick(true) }
//            imageView.setImageBitmap(R.drawable.ic_no_phote)
            txtViewName.text = catalogItem.name
            txtViewPrice.text = catalogItem.price.toString()
            textViewUnit.text = "цена за ${catalogItem.unit}"
            txtViewDescription.text = catalogItem.description

//            itemView.setBackgroundColor(if (isSelected) Color.LTGRAY else 0x00000000)
//            itemImageView.setImageResource(stateImageResource)
//
//            itemContainer.setOnClickListener {
//                selectedPosition = adapterPosition
//                onInvokeConfiguration(configuration)
//            }
//            serverUrl.text = configuration.serverUrl
//            updateResultTextView.isVisible = updateResultText.isNotEmpty()
//            updateResultTextView.text = updateResultText
            val b = 0
        }

        fun onButtonClick(add: Boolean) {
            txtViewQtty.text = when (add) {
                true -> (txtViewQtty.text.toString().toInt() + 1).toString()
                else -> (maxOf(txtViewQtty.text.toString().toInt() - 1, 0)).toString()
            }

        }
    }
}