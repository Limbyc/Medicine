package com.valance.medicine.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.valance.medicine.databinding.ProfileFragmentBinding
import com.valance.medicine.ui.ImageHelper
import java.io.IOException

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap = ImageHelper.loadImageFromPath(requireContext())
        bitmap?.let { binding.UserPhoto.setImageBitmap(it) }

        binding.cardView.setOnClickListener {
            pickPhoto()
        }
    }
    private fun pickPhoto() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)
        } else {
            val galeriIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent, 2)
        }
    }
    @Deprecated("Use pickPhotoWithPermissionCheck() instead.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            val pickedPhotoUri: Uri? = data.data
            pickedPhotoUri?.let { uri ->
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    binding.UserPhoto.setImageBitmap(bitmap)

                    val imagePath = ImageHelper.saveImageToInternalStorage(requireContext(), bitmap)
                    ImageHelper.saveImagePathToSharedPreferences(requireContext(), imagePath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}
