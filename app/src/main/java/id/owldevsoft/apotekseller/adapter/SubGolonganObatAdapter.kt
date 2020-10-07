package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.SubGolonganObatResponse
import kotlinx.android.synthetic.main.item_sub_golongan_obat.view.*

class SubGolonganObatAdapter(
    val item: ArrayList<SubGolonganObatResponse.Data>?,
    val context: Context,
    val clickUpdate: OnClickAction
) :
    RecyclerView.Adapter<SubGolonganObatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sub_golongan_obat,
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
        fun bind(item: SubGolonganObatResponse.Data?, clickUpdate: OnClickAction) {
            view.tv_nama_sub_golongan_obat.text = item?.namaSubGolongan
            view.iv_edit_sub_golongan_obat.setOnClickListener{
                clickUpdate.onCLickUpdate(item)
            }
            view.iv_delete_sub_golongan_obat.setOnClickListener{
                clickUpdate.onClickDelete(item)
            }
        }
    }

    interface OnClickAction {
        fun onCLickUpdate(position: SubGolonganObatResponse.Data?)
        fun onClickDelete(position: SubGolonganObatResponse.Data?)
    }

}