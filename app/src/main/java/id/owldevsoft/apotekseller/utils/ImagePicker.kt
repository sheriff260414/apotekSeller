package id.owldevsoft.apotekseller.utils

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import java.io.File


class ImagePicker {

    private val DEFAULT_MIN_WIDTH_QUALITY = 400 // min pixels

    private val TAG = "ImagePicker"
    private val TEMP_IMAGE_NAME = "tempImage"

    var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY

//    fun getPickImageIntent(context: Context): Intent? {
//        var chooserIntent: Intent? = null
//        var intentList: List<Intent?> = ArrayList()
//        val pickIntent = Intent(
//            Intent.ACTION_PICK,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        )
//        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        takePhotoIntent.putExtra("return-data", true)
//        takePhotoIntent.putExtra(
//            MediaStore.EXTRA_OUTPUT,
//            Uri.fromFile(getTempFile(context))
//        )
//        intentList = addIntentsToList(context, intentList as MutableList<Intent?>, pickIntent)
//        intentList = addIntentsToList(context, intentList, takePhotoIntent)
//        if (intentList.isNotEmpty()) {
//            chooserIntent = Intent.createChooser(
//                intentList.removeAt(intentList.size - 1),
//                context.getString(R.string.pick_image_intent_text)
//            )
//            chooserIntent.putExtra(
//                Intent.EXTRA_INITIAL_INTENTS,
//                intentList.toArray<Parcelable>(arrayOf<Parcelable>())
//            )
//        }
//        return chooserIntent
//    }
//
//    private fun addIntentsToList(
//        context: Context,
//        list: MutableList<Intent?>,
//        intent: Intent
//    ): MutableList<Intent?> {
//        val resInfo: List<ResolveInfo> =
//            context.getPackageManager().queryIntentActivities(intent, 0)
//        for (resolveInfo in resInfo) {
//            val packageName = resolveInfo.activityInfo.packageName
//            val targetedIntent = Intent(intent)
//            targetedIntent.setPackage(packageName)
//            list.add(targetedIntent)
//            Log.d(TAG, "Intent: " + intent.action + " package: " + packageName)
//        }
//        return list
//    }
//
//    private fun getTempFile(context: Context): File? {
//        val imageFile = File(context.externalCacheDir, TEMP_IMAGE_NAME)
//        imageFile.getParentFile().mkdirs()
//        return imageFile
//    }

}