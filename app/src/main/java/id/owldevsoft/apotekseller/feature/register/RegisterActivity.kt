package id.owldevsoft.apotekseller.feature.register

import android.R.attr
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mvc.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.RegisterBody
import id.owldevsoft.apotekseller.model.Response
import id.owldevsoft.apotekseller.utils.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), RegisterContract.View, View.OnClickListener {

    private lateinit var permission: PermissionCallback.Presenter
    override lateinit var presenter: RegisterContract.Presenter
    private lateinit var registerBody: RegisterBody
    private var photoSia: String? = null
    private var photoSipa: String? = null
    private var photoGedung: String? = null

    init {
        RegisterPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        permission = PermissionHelper(this)
        permission.isRequestPermissionGranted(this)

        btnReg.setOnClickListener(this)
        sia_image.setOnClickListener(this)
        sipa_image.setOnClickListener(this)
        gedung_image.setOnClickListener(this)

        initSpJenis()

        ImagePicker.setMinQuality(600, 600);

    }

    private fun handleInput(): Boolean {
        if (etName.text.isEmpty()) {
            etName.error = getString(R.string.fill_data)
            etName.requestFocus()
            return false
        }
        if (etNoKTP.text.isEmpty()) {
            etNoKTP.error = getString(R.string.fill_data)
            etNoKTP.requestFocus()
            return false
        }
        if (etEmail.text.isEmpty()) {
            etEmail.error = getString(R.string.fill_data)
            etEmail.requestFocus()
            return false
        }
        if (etNoHp.text.isEmpty()) {
            etNoHp.error = getString(R.string.fill_data)
            etNoHp.requestFocus()
            return false
        }
        if (etNamaApotek.text.isEmpty()) {
            etNamaApotek.error = getString(R.string.fill_data)
            etNamaApotek.requestFocus()
            return false
        }
        if (photoSia.isNullOrEmpty()) {
            Toast.makeText(this, "Photo SIA tidak boleh kososng", Toast.LENGTH_SHORT).show()
            return false
        }
        if (photoSipa.isNullOrEmpty()) {
            Toast.makeText(this, "Photo SIPA tidak boleh kososng", Toast.LENGTH_SHORT).show()
            return false
        }
        if (photoGedung.isNullOrEmpty()) {
            Toast.makeText(this, "Photo Gedung tidak boleh kososng", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onRegisterError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            this,
            getString(R.string.err_mes_register, message),
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onRegisterSuccess(response: Response) {
        val intent = Intent(this, NotifRegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loadingProg.visibility = View.VISIBLE
            btnReg.visibility = View.GONE
        } else {
            loadingProg.visibility = View.GONE
            btnReg.visibility = View.VISIBLE
        }
    }

    private fun initSpJenis() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis,
            R.layout.item_spinner
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        sp_jenis.setAdapter(adapter)
        sp_jenis.onItemSelectedListener
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnReg.id -> {
                if (handleInput()) {
                    val nama = etName.text.toString()
                    val noKTP = etNoKTP.text.toString()
                    val email = etEmail.text.toString()
                    val noHp = etNoHp.text.toString()
                    val namaApotek = etNamaApotek.text.toString()
                    val jenis = sp_jenis.selectedItem.toString()
                    registerBody = RegisterBody(
                        nama,
                        noKTP,
                        email,
                        photoSia!!,
                        photoSipa!!,
                        photoGedung!!,
                        noHp,
                        jenis,
                        namaApotek
                    )
                    presenter.doRegister(registerBody)
                }
            }
            sia_image.id -> {
//                getImageFromGallery(Constants.CODE_SIA_PHOTO)
                ImagePicker.pickImage(this, "Choose Picture")
            }
            sipa_image.id -> {
                getImageFromGallery(Constants.CODE_SIPA_PHOTO)
            }
            gedung_image.id -> {
                getImageFromGallery(Constants.CODE_GEDUNG_PHOTO)
            }
        }
    }

    private fun getImageFromGallery(code: Int) {
//        var chooserIntent: Intent? = null
//        var intentList: MutableList<Intent>

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        pickIntent.type = "image/*"
//        takePhotoIntent.putExtra("return-data", true);
//        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)));

//        addIntentsToList(this, intentList, pickIntent)
//        addIntentsToList(this, intentList, takePhotoIntent)
//
//        if (intentList.isNotEmpty()) {
//            chooserIntent = Intent.createChooser(
//                intentList.removeAt(intentList.size - 1),
//                this.getString(R.string.dialog_title_pick_picture))
//            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray());
//        }
        startActivityForResult(
//            takePhotoIntent, Constants.CODE_TARGET_PHOTO
//            pickIntent, Constants.CODE_TARGET_PHOTO
            Intent.createChooser(pickIntent, getString(R.string.dialog_title_pick_picture)),
            code
        )

//        return chooserIntent

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data)
            Picasso.get().load(data?.data).into(sia_image)
//            sia_image.setImageBitmap(bitmap)

//            when (requestCode) {
//                Constants.CODE_SIA_PHOTO -> {
//                    Picasso.get().load(data?.data).resize(1000, 500).into(sia_image)
////                    sia_image.setImageURI(data?.data)
//                    processPicture(data?.data, Constants.CODE_SIA_PHOTO)
////                    processPicture(data?.data)
//                }
//                Constants.CODE_SIPA_PHOTO -> {
//                    Picasso.get().load(data?.data).resize(1000, 500).into(sipa_image)
//                    processPicture(data?.data, Constants.CODE_SIPA_PHOTO)
//                }
//                Constants.CODE_GEDUNG_PHOTO -> {
//                    Picasso.get().load(data?.data).resize(1000, 500).into(gedung_image)
//                    processPicture(data?.data, Constants.CODE_GEDUNG_PHOTO)
//                }
//            }
        }
    }

    fun processPicture(data: Uri?, code: Int) {
        val image = ImCompressor().compress(
            ImageHelper.getPathImage(this, data!!), this.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )?.absolutePath
        )
        when (code) {
            Constants.CODE_SIA_PHOTO -> {
                photoSia = image
            }
            Constants.CODE_SIPA_PHOTO -> {
                photoSipa = image
            }
            Constants.CODE_GEDUNG_PHOTO -> {
                photoGedung = image
            }
        }
    }

//    private fun addIntentsToList(
//        context: Context,
//        list: MutableList<Intent>,
//        intent: Intent
//    ): MutableList<Intent>? {
//        val resInfo: List<ResolveInfo> =
//            context.getPackageManager().queryIntentActivities(intent, 0)
//        for (resolveInfo in resInfo) {
//            val packageName = resolveInfo.activityInfo.packageName
//            val targetedIntent = Intent(intent)
//            targetedIntent.setPackage(packageName)
//            list.add(targetedIntent)
//        }
//        return list
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val grantResult = IntArray(permissions.size)
        grantResult.forEach {
            if (requestCode == Constants.CODE_REQUEST_PERMISSION) {
                if (it == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission", "onRequestPermissionsResult: permission granted")
//                    Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("Permission", "onRequestPermissionsResult: permission denied")
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}