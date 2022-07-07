package fathi.reza.been_tech.Home

import com.facebook.drawee.view.SimpleDraweeView
import fathi.reza.been_tech.Custom.MyImageView

class ImageLoadingImpl:ImageLoading {
    override fun load(imageView: MyImageView, ImageUrl: String) {
       if (imageView is SimpleDraweeView) {
           imageView.setImageURI(ImageUrl)
       }
       }
    }
