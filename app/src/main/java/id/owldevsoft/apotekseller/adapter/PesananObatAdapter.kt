package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PenjualanResponse
import id.owldevsoft.apotekseller.model.PesananObatResponse
import kotlinx.android.synthetic.main.item_pesanan_obat.view.*

class PesananObatAdapter(
    val item: ArrayList<PesananObatResponse.Data>?,
    val context: Context,
    private val clickAction: OnClickActionPesanan
) : RecyclerView.Adapter<PesananObatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pesanan_obat,
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
        fun bind(item: PesananObatResponse.Data, clickActionPesanan: OnClickActionPesanan) {
            view.tv_nomor_order_pesanan_obat.text = item.noPesanan
            view.tv_tanggal_pesanan.text = item.dateCreated
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
            view.iv_detailpesanan.setOnClickListener {
                clickActionPesanan.onClickDetailPesanan(item)
            }
            view.iv_pemesan.setOnClickListener {
                clickActionPesanan.onClickDetailPemesan(item)
            }
        }
    }

    interface OnClickActionPesanan {
        fun onClickDetailOrder(position: PesananObatResponse.Data)
        fun onClickChangeStatus(position: PesananObatResponse.Data)
        fun onClickDetailPesanan(position: PesananObatResponse.Data)
        fun onClickDetailPemesan(position: PesananObatResponse.Data)
    }

}