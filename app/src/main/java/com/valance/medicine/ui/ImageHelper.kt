package com.valance.medicine.ui

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.valance.medicine.R
import java.io.File
import java.io.FileOutputStream

object ImageHelper {

    private val DEFAULT_IMAGE_RESOURCE = R.drawable.default_image
    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): String? {
        val contextWrapper = ContextWrapper(context)
        val directory = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        val file = File(directory, "profile_image.jpg")

        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            outputStream?.close()
        }
        return file.absolutePath
    }

    fun loadImageFromPath(context: Context): Bitmap {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val imagePath = sharedPreferences.getString("image_path", null)
        return if (imagePath != null) {
            BitmapFactory.decodeFile(imagePath)
        } else {
            BitmapFactory.decodeResource(context.resources, DEFAULT_IMAGE_RESOURCE)
        }
    }

    fun saveImagePathToSharedPreferences(context: Context, path: String?) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("image_path", path)
        editor.apply()
    }
}
