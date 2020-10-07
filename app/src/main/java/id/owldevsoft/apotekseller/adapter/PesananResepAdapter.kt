package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananResepResponse
import kotlinx.android.synthetic.main.item_pesanan_resep.view.*

class PesananResepAdapter(
    val item: ArrayList<PesananResepResponse.Data>?,
    val context: Context,
    private val clickAction: OnClickActionPesanan
) : RecyclerView.Adapter<PesananResepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pesanan_resep,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickAction)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: PesananResepResponse.Data, clickActionPesanan: OnClickActionPesanan) {
            view.tv_nomor_order_pesanan_resep.text = item.noPesanan
            view.tv_tglpesanan_resep.text = item.dateCreated
            view.tv_status.text = item.status
            if (item.status == "Selesai"){
                view.iv_ubah_status.visibility = View.GONE
            }
            view.ll_content.setOnClickListener{
                clickActionPesanan.onClickDetailOrder(item)
            }
            view.iv_ubah_status.setOnClickListener {
                clickActionPesanan.onClickChangeStatus(item)
            }
            view.iv_fotoresep.setOnClickListener {
                clickActionPesanan.onClickDetailPesanan(item)
            }
            view.iv_pemesan.setOnClickListener {
                clickActionPesanan.onClickDetailPemesan(item)
            }
        }
    }

    interface OnClickActionPesanan {
        fun onClickDetailOrder(position: PesananResepResponse.Data)
        fun onClickChangeStatus(position: PesananResepResponse.Data)
        fun onClickDetailPesanan(position: PesananResepResponse.Data)
        fun onClickDetailPemesan(position: PesananResepResponse.Data)
    }

}