package com.bawa.theWhyDev.PhotoPickerDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bawa.theWhyDev.PhotoPickerDemo.databinding.ActivityMainBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val maxImageSelectionCount = 5
    /**
     * NOTE: theWhyDev Step 2
     * When using PickVisualMedia, the photo picker opens in half-screen mode.
     */
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        Log.i("MainActivity", "Selected image URI : $uri")
        Glide
            .with(this)
            .load(uri)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView);
    }

    /**
     * Registers a photo picker activity launcher in multi-select mode.
     * here we are allowing the user to select at max
     * maxImageSelectionCount = 5
     */
    private val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxImageSelectionCount)) { uris ->
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    /**
     * NOTE: theWhyDev Step 3
     */
    //use only one of the launch methods at a time
    fun onSelectPhotoClicked(view: View) {
        // Can pick both images and video
        //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        /* can pick only images*/
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        /* can pick only videos.*/
        //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))

        /* Launch the photo picker and allow the user to choose only images/videos of a
         specific MIME type, such as GIFs.*/
        //val mimeType = "image/gif"
        //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))*/
    }

    fun onSelectMultiplePhotoClicked(view: View) {
        // this launch is using pickMultipleMedia object so we wil be able to select a limited
        // number of photos as passed in pickMultipleMedia object above
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }


}