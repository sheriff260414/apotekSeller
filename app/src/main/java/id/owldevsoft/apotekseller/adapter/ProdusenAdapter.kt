package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.ProdusenResponse
import kotlinx.android.synthetic.main.item_produsen.view.*

class ProdusenAdapter(
    private val item: ArrayList<ProdusenResponse.Data>?,
    val context: Context,
    private val clickUpdateProdusen: OnClickActionProdusen
) : RecyclerView.Adapter<ProdusenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_produsen,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ProdusenAdapter.ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickUpdateProdusen)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: ProdusenResponse.Data?, clickUpdateProdusen: OnClickActionProdusen) {
            view.tv_nama_produsen.text = item?.namaMerk
            view.iv_edit_produsen.setOnClickListener{
                clickUpdateProdusen.onCLickUpdate(item)
            }
            view.iv_delete_produsen.setOnClickListener{
                clickUpdateProdusen.onClickDelete(item)
            }
        }
    }

    interface OnClickActionProdusen {
        fun onCLickUpdate(position: ProdusenResponse.Data?)
        fun onClickDelete(position: ProdusenResponse.Data?)
    }

}