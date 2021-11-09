package com.fruktoland.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.common.Const
import com.fruktoland.app.data.persistence.items.CatalogItem
import com.fruktoland.app.ui.view.ModuleInteractor
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogAdapter @Inject constructor(
    var interactor: ModuleInteractor
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private val picasso = Picasso.get()
    var catalogList: List<CatalogItem> = emptyList()
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
            btnMinus.setOnClickListener { onButtonClick(false, catalogItem) }
            btnPlus.setOnClickListener { onButtonClick(true, catalogItem) }
            if (!catalogItem.imageAddress.isNullOrEmpty()) {
                picasso.load("${Const.BASE_URL}${catalogItem.imageAddress}").into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_no_phote)
            }
            txtViewName.text = catalogItem.name
            txtViewPrice.text = catalogItem.price.toString()
            textViewUnit.text = "цена за ${catalogItem.unit}"
            txtViewDescription.text = catalogItem.description
            txtViewQtty.text = catalogItem.qtty.toString()

            btnMinus.isEnabled = !txtViewName.text.isEmpty()
            btnPlus.isEnabled = !txtViewName.text.isEmpty()
        }

        private fun onButtonClick(add: Boolean, catalogItem: CatalogItem) {
            when (add) {
                true -> {
                    val qtty = txtViewQtty.text.toString().toDouble() + 1.0
                    txtViewQtty.text = (qtty).toString()
                    catalogItem.qtty = qtty
                    interactor.addToBasket(catalogItem)
                }
                else -> {
                    (txtViewQtty.text.toString().toDouble() - 1.0).also { qtty ->
                        val qttyText = when {
                            qtty <= 0 -> {
                                interactor.removeFromBasket(catalogItem)
                                "0.0"
                            }
                            else -> {
                                catalogItem.qtty = qtty
                                interactor.addToBasket(catalogItem)
                                qtty.toString()
                            }
                        }

                        txtViewQtty.text = qttyText
                    }
                }
            }
        }
    }
}