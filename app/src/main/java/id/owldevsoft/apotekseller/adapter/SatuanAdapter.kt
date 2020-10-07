package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.SatuanObatResponse
import kotlinx.android.synthetic.main.item_satuan.view.*

class SatuanAdapter(
    val item: ArrayList<SatuanObatResponse.Data>?,
    val context: Context,
    val clickUpdate: OnClickAction
) :
    RecyclerView.Adapter<SatuanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_satuan,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickUpdate)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: SatuanObatResponse.Data?, clickUpdate: OnClickAction) {
            view.tv_nama_satuan.text = item?.namaSatuan
            view.iv_edit_satuan.setOnClickListener{
                clickUpdate.onCLickUpdate(item)
            }
            view.iv_delete_satuan.setOnClickListener{
                clickUpdate.onClickDelete(item)
            }
        }
    }

    interface OnClickAction{
        fun onCLickUpdate(position: SatuanObatResponse.Data?)
        fun onClickDelete(position: SatuanObatResponse.Data?)
    }

}