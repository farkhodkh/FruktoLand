package com.fruktoland.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.data.persistence.items.BasketItem
import com.fruktoland.app.ui.view.ModuleInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderAdapter @Inject constructor(
    private var mainInteractor: ModuleInteractor
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val _totalSum = MutableStateFlow("0.00 р.")
    val totalSum: StateFlow<String> = _totalSum
    val decFormatter = DecimalFormat("#,###.##")

    private val differCallback = object : DiffUtil.ItemCallback<BasketItem>() {
        override fun areItemsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    init {
        differ.addListListener { previousList, currentList ->
            _totalSum.tryEmit(decFormatter.format(getSum()) + " р.")
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.order_recyclerview_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun getSum(): Double = differ.currentList.sumOf { it.price * it.qtty }

    fun removePosition(position: Int) {
        val newList = differ.currentList.toMutableList()
        val item = newList.removeAt(position)

        mainInteractor.removeFromBasket(item)
        this.differ.submitList(newList)
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var txtName: TextView = itemView.findViewById(R.id.txtName)
        private var txtVTotal: TextView = itemView.findViewById(R.id.txtVTotal)
        private var txtVUnit: TextView = itemView.findViewById(R.id.txtVUnit)
        private var txtViewQtty: TextView = itemView.findViewById(R.id.txtViewQtty)
        private var btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        private var btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)

        fun bind(basketItem: BasketItem) {
            txtName.text = basketItem.name
            txtVUnit.text = basketItem.unit
            txtVTotal.text = (basketItem.price * basketItem.qtty).toString()
            txtViewQtty.text = basketItem.qtty.toString()

            btnPlus.setOnClickListener {
                basketItem.qtty = basketItem.qtty + 1.0
                txtVTotal.text = decFormatter.format(basketItem.price * basketItem.qtty)

                txtViewQtty.text = basketItem.qtty.toString()
                _totalSum.tryEmit(decFormatter.format(getSum()) + " р.")

                mainInteractor.addToBasket(basketItem)
            }

            btnMinus.setOnClickListener {
                basketItem.qtty = maxOf(basketItem.qtty - 1.0, 0.0)
                txtVTotal.text = decFormatter.format(basketItem.price * basketItem.qtty)

                txtViewQtty.text = basketItem.qtty.toString()
                _totalSum.tryEmit(decFormatter.format(getSum()) + " р.")

                mainInteractor.addToBasket(basketItem)
            }
        }
    }
}