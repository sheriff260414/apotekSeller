package id.owldevsoft.apotekseller.feature.pesananobat.detailpemesan

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananObatResponse
import kotlinx.android.synthetic.main.fragment_pemesan_pesanan_obat.*


class PemesanPesananObatFragment : DialogFragment(), OnMapReadyCallback {

    private var mView: View? = null
    lateinit var item: PesananObatResponse.Data

    private var longitude: Double? = null
    private var latitude: Double? = null
    private var mMap: GoogleMap? = null
    private lateinit var mapFrag: SupportMapFragment

    companion object {
        const val DATA_PESANAN_OBAT: String = "DATAPESANANOBAT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_pemesan_pesanan_obat, container, false)

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
            item = bundle.getParcelable(DATA_PESANAN_OBAT)!!

            edit_namapemesan.setText(item.nama)
            edit_nohppemesan.setText(item.noTelp)

            longitude = item.longitude.toDouble()
            latitude = item.latitude.toDouble()

        }

        setupMap()

        close_pemesan.setOnClickListener {
            this.dismiss()
        }

    }

    private fun setupMap() {
        activity.let {
            mapFrag = activity!!.supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
            mapFrag.getMapAsync(this)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.addMarker(
            MarkerOptions().position((LatLng(latitude!!, longitude!!))).title("Pemesan")
        )
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latitude?.let { it1 ->
            longitude?.let { it2 ->
                LatLng(it1, it2)
            }
        }, 19f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fragment: Fragment? = fragmentManager!!.findFragmentById(R.id.maps)
        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.remove(fragment!!)
        ft.commit()
    }

//    override fun onCameraIdle() {
//        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
//        try {
//            if (latitude != null && longitude != null) {
//                mCurrentPosition = LatLng(latitude!!, longitude!!)
//                val address = geoCoder.getFromLocation(latitude!!, longitude!!, 1)
//            }
//        } catch (e: IOException) {
//            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
//        } catch (e: Exception) {
//            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
//        }
//    }

}