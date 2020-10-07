/*
 * Developed By Arief TB on 2/22/19 5:11 PM.
 * Github : github.com/arieftb .
 * Web : arieftb.com .
 * Copyright (c) 2019.
 */

package id.owldevsoft.apotekseller.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import id.owldevsoft.apotekseller.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ImageHelper {
    companion object {
        fun getPathImage(context: Context, uri: Uri): String? {
            var cursor: Cursor? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(uri, proj, null, null, null)
                var columnIndex = 0

                if (cursor != null) {
                    columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst()
                }

                return cursor?.getString(columnIndex)
            } finally {
                cursor?.close()
            }
        }

        fun compressImage(uri: Uri): File {
            val bitmapOption = BitmapFactory.Options()
            bitmapOption.inJustDecodeBounds = false
            bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565

            val bitmap: Bitmap = BitmapFactory.decodeFile(uri.path, bitmapOption)

            val width = bitmapOption.outWidth
            val height = bitmapOption.outHeight

            if (width > 2000 || width > 2000) {
                Bitmap.createScaledBitmap(bitmap, width / 4, height / 4, true)
            } else if ((height > 1000 || width > 1000) && (height <= 2000 || width <= 2000)) {
                Bitmap.createScaledBitmap(bitmap, width / 2, height / 2, true)
            }

            var pathImage: String? = null
            var fileOutStream: FileOutputStream? = null

            try {
                pathImage = uri.path
                fileOutStream = FileOutputStream(pathImage)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutStream)
                fileOutStream.flush()
                fileOutStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return File(Uri.parse(pathImage).path)
        }

        fun getSingleImageFromGallery(context: Activity, code: Int) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            context.startActivityForResult(Intent.createChooser(intent, context.getString(R.string.dialog_title_pick_picture)), code)
        }
//
//        fun getImageFromGallery(context code: Int) {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type = "image/*"
//            context.startActivityForResult(Intent.createChooser(intent, context.getString(R.string.dialog_title_pick_picture)), code)
//        }


//
//        fun getMultipleImageFromGallery(context: Activity, code: Int) {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type = "image/*"
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            context.startActivityForResult(Intent.createChooser(intent, context.applicationContext.getString(R.string.dialog_title_pick_picture)), code)
//        }

        fun getFileName(path: String?): String? = path?.substring(path.lastIndexOf("/") +1)

        fun convertToBody(bodyName: String, file: Uri?): MultipartBody.Part? {

            return try {
                val fileCompressed = compressImage(file!!)
                //            val fileCompressed = File(file?.path)
                val fileBody = fileCompressed.asRequestBody("image/*".toMediaTypeOrNull())

                MultipartBody.Part.createFormData(bodyName, fileCompressed.name, fileBody)
            } catch (e: Exception) {
                null
            }
        }

        fun preparingBody(context: Context, partName: String, fileUri: Uri): MultipartBody.Part {
            val file = File(fileUri.path!!)
            val fileBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            return MultipartBody.Part.createFormData(partName, file.name, fileBody)
        }


//
//        fun showImage(view: ImageView, path: String?) {
//            Glide.with(view.context)
//                .load(path)
//                .apply(RequestOptions().placeholder(R.drawable.logo).error(R.drawable.logo).fitCenter())
//                .into(view)
//        }
//
//        fun showImage(view: ImageView, path: Int?) {
//            Glide.with(view.context)
//                .load(path)
//                .apply(RequestOptions().placeholder(R.drawable.logo).error(R.drawable.logo).fitCenter())
//                .into(view)
//        }
//
//        @SuppressLint("RestrictedApi")
//        fun showIconFab(fab: FloatingActionButton, path: Int?) {
//            fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(fab.context, path!!))
//        }
    }
}