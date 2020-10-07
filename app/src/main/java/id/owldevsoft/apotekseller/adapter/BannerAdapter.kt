package id.owldevsoft.apotekseller.adapter

import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.model.BannerResponse
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class BannerAdapter(val data: List<BannerResponse.Data>) : SliderAdapter() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        imageSlideViewHolder?.bindImageSlide("${BuildConfig.BASE_URL_BANNER}${data[position].postImage}")
    }
}