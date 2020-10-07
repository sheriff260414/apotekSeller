package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.JenisObatResponse
import kotlinx.android.synthetic.main.item_jenis_golongan_obat.view.*

class JenisGolonganObatAdapter(
    private val item: ArrayList<JenisObatResponse.Data>?,
    val context: Context,
    private val clickActionJenisObat: OnClickActionJenisGolonganObat
) : RecyclerView.Adapter<JenisGolonganObatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_jenis_golongan_obat,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickActionJenisObat)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: JenisObatResponse.Data?, clickUpdateProdusen: OnClickActionJenisGolonganObat) {
            view.tv_nama_jenis_golongan_obat.text = item?.namaJenis
            view.iv_edit_jenis_golongan_obat.setOnClickListener{
                clickUpdateProdusen.onCLickUpdate(item)
            }
            view.iv_delete_jenis_golongan_obat.setOnClickListener{
                clickUpdateProdusen.onClickDelete(item)
            }
        }
    }

    interface OnClickActionJenisGolonganObat {
        fun onCLickUpdate(position: JenisObatResponse.Data?)
        fun onClickDelete(position: JenisObatResponse.Data?)
    }

}