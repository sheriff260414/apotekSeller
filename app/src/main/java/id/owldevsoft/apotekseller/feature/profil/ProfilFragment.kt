package id.owldevsoft.apotekseller.feature.profil

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.login.LoginActivity
import id.owldevsoft.apotekseller.feature.pesananresep.updatestatus.UpdateStatusPesananResepFragment
import id.owldevsoft.apotekseller.model.*
import id.owldevsoft.apotekseller.utils.*
import kotlinx.android.synthetic.main.fragment_profil.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfilFragment : Fragment(), View.OnClickListener, ProfilContract.View,
    View.OnFocusChangeListener,
    OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    override lateinit var presenter: ProfilContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private var loading: AlertDialog? = null
    private lateinit var daerahBody: DaerahBody
    private lateinit var updateProfileBody: UpdateProfileBody
    private lateinit var changePasswordBody: ChangePasswordBody
    lateinit var mCallback: OnSuccessChangePassword

    private var adapterProvince: ArrayAdapter<String>? = null
    private var listProvinsi: ArrayList<String>? = null
    private var dataProvinsi: ArrayList<DaerahResponse.Data>? = null

    private var adapterKabupaten: ArrayAdapter<String>? = null
    private var listKabupaten: ArrayList<String>? = null
    private var dataKabupaten: ArrayList<DaerahResponse.Data>? = null

    private var provinsi: String? = null
    private var idProvince: String? = null
    private var posprov: Int? = null
    private var kabupaten: String? = null
    private var idKabupaten: String? = null
    private var poskab: Int? = null
    private var idApotik: String? = null
    private var namaApotik: String? = null
    private var photo: String? = null
    private var flgPhoto: String = "0"

    private var mFusedLocation: FusedLocationProviderClient? = null
    private var mMap: GoogleMap? = null
    private lateinit var mapFrag: SupportMapFragment
    private var mCurrentPosition: LatLng? = null

    val LOCATION_REQUEST_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfilPresenter(requireContext(), this)
        preferenceHelper = activity?.let { PreferenceHelper(it) }
        loading = DialogHelper.showDialogLoading(requireContext())

        presenter.getApotek(preferenceHelper?.getUser()!!.data.apotek[0].idApotek)

        btnLogout.setOnClickListener(this)
        btnPassProfil.setOnClickListener(this)
        edit_jam_buka.onFocusChangeListener = this
        edit_jam_buka.setOnClickListener(this)
        edit_jam_tutup.onFocusChangeListener = this
        edit_jam_tutup.setOnClickListener(this)
        rl_current_location.setOnClickListener(this)
        btnProfProfil.setOnClickListener(this)
        logoapotek.setOnClickListener(this)

        checkPermission()

        tl_profile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) {
                    sv_pass.visibility = View.GONE
                    sv_profile.visibility = View.VISIBLE
                }
                if (p0?.position == 1) {
                    sv_pass.visibility = View.VISIBLE
                    sv_profile.visibility = View.GONE
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnLogout.id -> logout()
            edit_jam_buka.id -> initTimePicker(edit_jam_buka)
            edit_jam_tutup.id -> initTimePicker(edit_jam_tutup)
            rl_current_location.id -> moveCurrentLocation()
            btnProfProfil.id -> {
                if (handleInput()) {
                    val no_telp = edit_no_telp_profil.text.toString()
                    val npwp = edit_npwp_profil.text.toString()
                    val no_sia = edit_no_sia_profil.text.toString()
                    val nama_apoteker = edit_apoteker_profil.text.toString()
                    val no_sipa = edit_no_sipa_profil.text.toString()
                    val jam_buka = edit_jam_buka.text.toString()
                    val jam_tutup = edit_jam_tutup.text.toString()
                    val longitude = edit_longitude.text.toString()
                    val latitude = edit_latitude.text.toString()
                    val alamat = edit_alamat.text.toString()

                    updateProfileBody = UpdateProfileBody(
                        idApotik!!,
                        namaApotik!!,
                        no_telp,
                        npwp,
                        no_sia,
                        nama_apoteker,
                        no_sipa,
                        jam_buka,
                        jam_tutup,
                        longitude,
                        latitude,
                        idKabupaten!!,
                        photo,
                        alamat,
                        flgPhoto
                    )
                    presenter.updateProfile(updateProfileBody)
                }

            }
            logoapotek.id -> {
                getImageFromGallery()
            }
            btnPassProfil.id -> {
                if (handleChangePass()) {
                    changePasswordBody = ChangePasswordBody(
                        edit_username.text.toString(),
                        edit_pass_lama.text.toString(),
                        edit_password_baru.text.toString()
                    )
                    presenter.changePassword(changePasswordBody)
                }
            }
        }
    }

    private fun handleInput(): Boolean {
        if (edit_no_telp_profil.text!!.isEmpty()) {
            edit_no_telp_profil.error = getString(R.string.fill_data)
            edit_no_telp_profil.requestFocus()
            return false
        }
        if (edit_npwp_profil.text!!.isEmpty()) {
            edit_npwp_profil.error = getString(R.string.fill_data)
            edit_npwp_profil.requestFocus()
            return false
        }
        if (edit_no_sia_profil.text!!.isEmpty()) {
            edit_no_sia_profil.error = getString(R.string.fill_data)
            edit_no_sia_profil.requestFocus()
            return false
        }
        if (edit_apoteker_profil.text!!.isEmpty()) {
            edit_apoteker_profil.error = getString(R.string.fill_data)
            edit_apoteker_profil.requestFocus()
            return false
        }
        if (edit_no_sipa_profil.text!!.isEmpty()) {
            edit_no_sipa_profil.error = getString(R.string.fill_data)
            edit_no_sipa_profil.requestFocus()
            return false
        }
        if (edit_jam_buka.text!!.isEmpty()) {
            edit_jam_buka.error = getString(R.string.fill_data)
            edit_jam_buka.requestFocus()
            return false
        }
        if (edit_jam_tutup.text!!.isEmpty()) {
            edit_jam_tutup.error = getString(R.string.fill_data)
            edit_jam_tutup.requestFocus()
            return false
        }
        if (edit_longitude.text!!.isEmpty()) {
            edit_longitude.error = getString(R.string.fill_data)
            edit_longitude.requestFocus()
            return false
        }
        if (edit_latitude.text!!.isEmpty()) {
            edit_latitude.error = getString(R.string.fill_data)
            edit_latitude.requestFocus()
            return false
        }
        if (edit_alamat.text!!.isEmpty()) {
            edit_alamat.error = getString(R.string.fill_data)
            edit_alamat.requestFocus()
            return false
        }
        if (sp_kabupaten.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Pilih Kabupaten", Toast.LENGTH_LONG).show()
            return false
        }
//        if (photo.isNullOrEmpty()){
//            Toast.makeText(requireContext(), "Photo Apotek tidak boleh kososng", Toast.LENGTH_SHORT).show()
//            return false
//        }
        return true
    }

    private fun handleChangePass(): Boolean {
        if (edit_username.text!!.isEmpty()) {
            edit_username.error = getString(R.string.fill_data)
            edit_username.requestFocus()
            return false
        }
        if (edit_pass_lama.text!!.isEmpty()) {
            edit_pass_lama.error = getString(R.string.fill_data)
            edit_pass_lama.requestFocus()
            return false
        }
        if (edit_password_baru.text!!.isEmpty()) {
            edit_password_baru.error = getString(R.string.fill_data)
            edit_password_baru.requestFocus()
            return false
        }
        return true
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        when (p0?.id) {
            edit_jam_buka.id -> {
                if (p1) {
                    initTimePicker(edit_jam_buka)
                }
            }
            edit_jam_tutup.id -> {
                if (p1) {
                    initTimePicker(edit_jam_tutup)
                }
            }
        }
    }

    private fun logout() {
        DialogHelper.showConfirmDialog(
            requireContext(), getString(R.string.message_logout),
            getString(R.string.ans_mes_logout),
            getString(R.string.ans_mes_no_logout),
            false,
            object : DialogHelper.Answer {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    preferenceHelper?.remove(PreferenceHelper.USERDATA)
                    preferenceHelper?.dataUser = null
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    activity?.finish()
                    dialog.dismiss()
                }

                override fun negativeButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }

            }
        )
    }

    override fun onError(code: Int, message: String) {
        activity?.let {
            DialogHelper.showWarnDialog(
                it,
                message,
                getString(R.string.ans_mes_ok),
                false,
                object : DialogHelper.Positive {
                    override fun positiveButton(dialog: DialogInterface, id: Int) {
                        dialog.dismiss()
                    }
                })
        }
    }

    override fun onApotekSuccess(apotekResponse: ApotekResponse) {
        idApotik = apotekResponse.data.idApotek
        namaApotik = apotekResponse.data.namaApotek
        tv_nama_apotek.setText(apotekResponse.data.namaApotek)
        edit_npwp_profil.setText(apotekResponse.data.npwp)
        edit_no_telp_profil.setText(apotekResponse.data.noTelp)
        edit_no_sia_profil.setText(apotekResponse.data.noSia)
        edit_no_sipa_profil.setText(apotekResponse.data.noSipa)
        edit_apoteker_profil.setText(apotekResponse.data.namaApoteker)
        edit_jam_buka.setText(apotekResponse.data.jamBuka)
        edit_jam_tutup.setText(apotekResponse.data.jamTutup)
        edit_alamat.setText(apotekResponse.data.alamat)
        provinsi = apotekResponse.data.idProvinsi
        kabupaten = apotekResponse.data.idKabupaten
        photo = apotekResponse.data.postImage
        edit_longitude.setText(apotekResponse.data.longitude)
        edit_latitude.setText(apotekResponse.data.latitude)
        Picasso.get().load("${BuildConfig.BASE_URL}${apotekResponse.data.postImage}")
            .placeholder(R.drawable.placeholder).into(logoapotek)

        daerahBody = DaerahBody("")
        presenter.getprovinsi(daerahBody)

        if (kabupaten == null){
            sp_kabupaten.visibility = View.GONE
        }

    }

    override fun onProvinsiSuccess(daerahResponse: DaerahResponse) {
        initSpProvinsi(daerahResponse)
    }

    override fun onKabupatenSuccess(daerahResponse: DaerahResponse) {
        sp_kabupaten.visibility = View.VISIBLE
        initSpKabupaten(daerahResponse)
    }

    override fun onUpdateProfileSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onChangePasswordSuccess(it: ArrayList<ChangePasswordResponse.Data>) {

        Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show()

        val dataUserChange = LoginResponse.Data.User(
            it[0].apiKey,
            it[0].createdBy,
            it[0].createdDate,
            it[0].editedBy,
            it[0].editedDate,
            it[0].idMembers,
            it[0].idRole,
            it[0].idUser,
            it[0].password,
            it[0].username
        )

        val dataUser: ArrayList<LoginResponse.Data.User> = ArrayList()
        dataUser.add(dataUserChange)

        val login = LoginResponse.Data(
            preferenceHelper?.getUser()!!.data.apotek,
            preferenceHelper?.getUser()!!.data.member,
            dataUser
        )

        val loginResponse = LoginResponse(
            login,
            "success change password",
            "success"
        )

        preferenceHelper?.dataUser = Gson().toJson(loginResponse)
        mCallback.onSuccessChangePassword()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnSuccessChangePassword
        } catch (e: ClassCastException) {
            Log.d(
                "Create Dialog",
                "Activity doesn't implement the OnSuccessChangePassword interface"
            )
        }
    }

    interface OnSuccessChangePassword {
        fun onSuccessChangePassword()
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onProcessChangePass(boolean: Boolean) {
        if (boolean) {
            loadingProgPass.visibility = View.VISIBLE
            btnPassProfil.visibility = View.GONE
        } else {
            loadingProgPass.visibility = View.GONE
            btnPassProfil.visibility = View.VISIBLE
        }
    }

    private fun initSpProvinsi(daerahResponse: DaerahResponse) {
        listProvinsi = ArrayList()
        adapterProvince?.clear()
        listProvinsi?.add("Pilih Provinsi")
        dataProvinsi = daerahResponse.data
        daerahResponse.data.forEachIndexed { index, data ->
            if (data.idProv == provinsi) {
                posprov = index + 1
            }
            listProvinsi?.add(data.nama)
        }
        adapterProvince?.notifyDataSetChanged()
        adapterProvince = ArrayAdapter(
            context!!, android.R.layout.simple_spinner_dropdown_item, listProvinsi!!
        )
        sp_provinsi.adapter = adapterProvince

        sp_provinsi.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idProvince = dataProvinsi?.get(p2 - 1)?.idProv
                    daerahBody = DaerahBody(idProvince!!)
                    presenter.getKabupaten(daerahBody)
                } else {
                    idProvince = null
                }
            }
        }

        if (posprov != null) {
            sp_provinsi.setSelection(posprov!!)
        }
    }

    private fun initSpKabupaten(daerahResponse: DaerahResponse) {
        if (provinsi != idProvince) {
            kabupaten = ""
            poskab = 0
        }

        listKabupaten = ArrayList()
        adapterKabupaten?.clear()
        listKabupaten?.add("Pilih Kabupaten")
        dataKabupaten = daerahResponse.data
        daerahResponse.data.forEachIndexed { index, data ->
            if (data.idKab == kabupaten) {
                poskab = index + 1
            }
            listKabupaten?.add(data.nama)
        }
        adapterKabupaten?.notifyDataSetChanged()
        adapterKabupaten = ArrayAdapter(
            context!!, android.R.layout.simple_spinner_dropdown_item, listKabupaten!!
        )
        sp_kabupaten.adapter = adapterKabupaten
        sp_kabupaten.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idKabupaten = dataKabupaten?.get(p2 - 1)?.idKab
                } else {
                    idKabupaten = null
                }
            }
        }

        if (poskab != null) {
            sp_kabupaten.setSelection(poskab!!)
        }

    }

    private fun initTimePicker(textInputEditText: TextInputEditText) {
        var time: String? = null
        val cal = Calendar.getInstance()
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                time = SimpleDateFormat("HH:mm").format(cal.time)
                textInputEditText.setText(time)
            }
        TimePickerDialog(
            requireContext(),
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.setOnCameraIdleListener(this)
        checkGpsEnable()
    }

    override fun onCameraIdle() {
        val lat = mMap?.cameraPosition?.target?.latitude
        val lng = mMap?.cameraPosition?.target?.longitude
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            if (lat != null && lng != null) {
                mCurrentPosition = LatLng(lat, lng)
                val address = geoCoder.getFromLocation(lat, lng, 1)
                if (address.size > 0) {
                    edit_longitude.setText(address[0].longitude.toString())
                    edit_latitude.setText(address[0].latitude.toString())
//                    tv_address.text = address[0].getAddressLine(0)
                }
            }
        } catch (e: IOException) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupMap()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    checkPermission()
                else {
                    Toast.makeText(requireContext(), "Akses lokasi ditolak", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setupMap() {
        mapFrag = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mFusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        mapFrag.getMapAsync(this)
    }

    private fun checkGpsEnable() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val task = settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener { moveCurrentLocation() }
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                val resolvable = it
                try {
                    resolvable.startResolutionForResult(activity, 51)
                } catch (e1: IntentSender.SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun moveCurrentLocation() {
        mFusedLocation?.lastLocation?.addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result != null)
                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it.result?.latitude?.let { it1 ->
                        it.result?.longitude?.let { it2 ->
                            LatLng(it1, it2)
                        }
                    }, 19f))
            }
        }
    }

    private fun getImageFromGallery() {

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        pickIntent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(pickIntent, getString(R.string.dialog_title_pick_picture)),
            Constants.CODE_TARGET_PHOTO
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.CODE_TARGET_PHOTO -> {
                    Picasso.get().load(data?.data).resize(130, 130).into(logoapotek)
//                    sia_image.setImageURI(data?.data)
                    processPicture(data?.data)
                    flgPhoto = "1"
//                    processPicture(data?.data)
                }
            }
        }
    }

    fun processPicture(data: Uri?) {
        val image = ImCompressor().compress(
            ImageHelper.getPathImage(requireContext(), data!!),
            requireContext().getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )?.absolutePath
        )
        photo = image
    }

}