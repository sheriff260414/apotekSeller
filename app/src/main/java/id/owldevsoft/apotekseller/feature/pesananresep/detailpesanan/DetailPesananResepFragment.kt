package id.owldevsoft.apotekseller.feature.pesananresep.detailpesanan

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.pesananresep.updatestatus.UpdateStatusPesananResepFragment
import id.owldevsoft.apotekseller.model.PesananResepResponse
import kotlinx.android.synthetic.main.fragment_detail_pesanan_resep.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.*
import java.text.NumberFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailPesananResepFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailPesananResepFragment : DialogFragment() {

    private var mView: View? = null
    lateinit var item: PesananResepResponse.Data

    companion object {
        const val DATA_PESANAN_RESEP: String = "DATAPESANANRESEP"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_pesanan_resep, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            item = bundle.getParcelable(DATA_PESANAN_RESEP)!!

            Picasso.get().load("${BuildConfig.BASE_URL}${item.postImage}")
                .placeholder(R.drawable.placeholder).into(iv_resep)
        }

        close_pesanan_resep.setOnClickListener {
            this.dismiss()
        }

    }

}