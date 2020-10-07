package id.owldevsoft.apotekseller.adapter

import android.widget.ImageView
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.R
import ss.com.bannerslider.ImageLoadingService

class BannerLoading : ImageLoadingService {
    override fun loadImage(url: String?, imageView: ImageView?) {
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        Picasso.get().load(resource).placeholder(R.drawable.placeholder).into(imageView)
    }

    override fun loadImage(url: String?, placeHolder: Int, errorDrawable: Int, imageView: ImageView?) {
        Picasso.get().load(url).placeholder(R.drawable.placeholder).error(errorDrawable).into(imageView)
    }
}